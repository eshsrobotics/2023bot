package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShuffleboardDebug;

/**
 * Abstracts user input to three different values for, the front-back value, the
 * left-right value, and the rotation value for a mecanum drive
 */
public class InputSubsystem extends SubsystemBase {
    /**
     * The XboxController object, will be used if our controls will be from an
     * Xbox controller
     */
    private XboxController xboxController = null;

    /**
     * The Joystick object, will be used if our controls will be from a joystick
     */
    private Joystick joystickController = null;

    private double frontBack;
    private double leftRight;
    private double rotation;
    private int armX;
    private int armY;
    private boolean highGoal = false;
    private boolean lowGoal = false;
    private boolean floor = false;
    private boolean resetArm = false;
    private boolean goliathForward = false;
    private boolean goliathReverse = false;
    private double goliathSpeed = 0;
    private ShuffleboardDebug debug;

    public InputSubsystem(ShuffleboardDebug debug) {
        try {
            xboxController = new XboxController(Constants.XBOX_PORT);
        } catch (Exception e) {
            System.out.format("Exception caught while initializing input subsystem: %s\n", e.getMessage());
        }

        try {
            joystickController = new Joystick(Constants.JOYSTICK_PORT);
        } catch (Exception e) {
            System.out.format("Exception caught while initializing input subsystem: %s\n", e.getMessage());
        }

        this.debug = debug;
    }

    @Override
    public void periodic() {

        super.periodic();
        double xboxFrontBack = 0.0;
        double xboxLeftRight = 0.0;
        double xboxRotation = 0.0;
        int xboxArmX = 0;
        int xboxArmY = 0;

        double joystickFrontBack = 0.0;
        double joystickLeftRight = 0.0;
        double joystickRotation = 0.0;
        int joystickArmX = 0;
        int joystickArmY = 0;

        
        
        /**
         * jHat is one of eight angle values that is given by the small
         * "joystick" on the top of the Logitech Extreme 3D Pro. It is given in
         * 45 degree increments from 0-315, and returns -1 if no input is given
         */
        int jHat = -1;

        boolean clawMode = false;

        if (xboxController != null) {
            xboxFrontBack = xboxController.getLeftY();
            xboxLeftRight = xboxController.getLeftX();
            xboxRotation = xboxController.getRightX();

            if (xboxController.getRightBumper()) {
                // If right bumper is pressed, we are moving the arm away from
                // the robot
                xboxArmX = 1;
            } else if (xboxController.getLeftBumper()) {
                // If left bumper is pressed, we move the arm towards the robot
                xboxArmX = -1;
            }

            if (xboxController.getBButtonPressed()) {
                // Control claw intake and outake.
                goliathSpeed = xboxController.getRightTriggerAxis() - 
                    xboxController.getLeftTriggerAxis();
            } else {
                // Control the arm y as normal.
                if (xboxController.getRightTriggerAxis() > Constants.DEADZONE) {
                    // if right trigger is pressed, we move the arm up
                    xboxArmY = 1;
                } else if (xboxController.getLeftTriggerAxis() > Constants.DEADZONE) {
                    // if left trigger is pressed, we move the arm down
                    xboxArmY = -1;
                }
            }
            
            highGoal = xboxController.getYButton();
            lowGoal = xboxController.getXButton();
            floor = xboxController.getAButton();
            resetArm = xboxController.getBButton();
            goliathForward = xboxController.getPOV() > -45 && xboxController.getPOV() < 45;
            goliathReverse = xboxController.getPOV() > 135 && xboxController.getPOV() < 225;
        }

        if (joystickController != null) {
            joystickFrontBack = joystickController.getY();
            joystickLeftRight = joystickController.getX();
            joystickRotation = joystickController.getZ();
            clawMode = joystickController.getTrigger();
            jHat = joystickController.getPOV();
            highGoal = highGoal || joystickController.getRawButton(5);
            lowGoal = lowGoal || joystickController.getRawButton(3);
            floor = floor || joystickController.getRawButton(6);
            resetArm = resetArm || joystickController.getRawButton(4);
            goliathForward = goliathForward || joystickController.getRawButton(9);
            goliathReverse = goliathReverse || joystickController.getRawButton(11);
        }

        if (!clawMode) {
            // Intelligently combine simultaneous inputs
            frontBack = MathUtil.clamp(xboxFrontBack + joystickFrontBack, -1, 1);
            leftRight = -MathUtil.clamp(xboxLeftRight + joystickLeftRight, -1, 1);
            rotation = -MathUtil.clamp(xboxRotation + joystickRotation, -1, 1);
            if (jHat != -1) {
                // The hat is being manipulated
                joystickArmY = (jHat > 90 && jHat < 270 ? -1 : jHat < 90 || jHat > 270 ? 1 : 0);
                joystickArmX = (jHat > 0 && jHat < 180 ? 1 : jHat > 180 ? -1 : 0);
            }

            armX = xboxArmX + joystickArmX;
            armY = xboxArmY + joystickArmY;
        } else {
            final double CLAW_MODE_TRANSLATIONAL_SPEED = 0.2;
            if (jHat != -1) {
                frontBack = (jHat > 90 && jHat < 270 ?  -CLAW_MODE_TRANSLATIONAL_SPEED:  jHat < 90 || jHat > 270 ? CLAW_MODE_TRANSLATIONAL_SPEED : 0);
                leftRight = (jHat > 0 && jHat < 180 ? CLAW_MODE_TRANSLATIONAL_SPEED : jHat > 180 ? -CLAW_MODE_TRANSLATIONAL_SPEED : 0);
            }
            if (joystickFrontBack > Constants.DEADZONE) {
                armY = -1;
            } else if (joystickFrontBack < -Constants.DEADZONE) {
                armY = 1;
            }
            if (joystickRotation > Constants.DEADZONE) {
                armX = -1;
            } else if (joystickRotation < -Constants.DEADZONE) {
                armX = 1;
            }
        }

        // Adds a deadzone to the controller of 0.1, or 10%
        if (Math.abs(frontBack) < Constants.DEADZONE) {
            frontBack = 0.0;
        }
        if (Math.abs(leftRight) < Constants.DEADZONE) {
            leftRight = 0.0;
        }
        if (Math.abs(rotation) < Constants.DEADZONE) {
            rotation = 0.0;
        }

        debug.forwardBack.setDouble(frontBack);
        debug.leftRight.setDouble(leftRight);
        debug.rotation.setDouble(rotation);
    }

    /**
     * The user's desired front-back motion
     */
    public double getFrontBack() {
        return frontBack;
    }

    /**
     * The user's desired left-right motion
     */
    public double getLeftRight() {
        return leftRight;
    }

    /**
     * The user's desired rotational motion
     */
    public double getRotation() {
        return rotation;
    }

    public int getArmX() {
        return armX;
    }

    public int getArmY() {
        return armY;
    }

    public boolean getHighGoalButton() {
        return highGoal;
    }

    public boolean getLowGoalButton() {
        return lowGoal;
    }

    public boolean getFloorButton() {
        return floor;
    }

    public boolean getResetArmButton() {
        return resetArm;
    }

    public boolean getGoliathForwardButton() {
        return goliathForward;
    }

    public boolean getGoliathReverseButton() {
        return goliathReverse;
    }
 
    /**
     * The Goliath intake at the end of the claw is controlled by two roller
     * motors that spin in opposite directions.  Anything caught between them
     * can either be taken up (intake) or released (outtake.)
     * 
     * <p>This function returns a single number that determines the roller
     * speed.  If that number is positive, that represents claw intake; 
     * negative values represent claw outtake.</p>
     * @return A number between -1.0 and 1.0 (inclusive.)
     */
    public double getGoliathSpeed() {
        return goliathSpeed;
    } 

    /**
     * Used when the user wants fine grain control over the intake.
     * @return
     */
    public boolean manualIntake() {
         
    }
    
    public void rumbleXbox() {
        if (xboxController != null) {
            xboxController.setRumble(RumbleType.kBothRumble, 1);
        }
    }
}
