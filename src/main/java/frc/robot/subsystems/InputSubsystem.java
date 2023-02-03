package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

    public InputSubsystem() {
        try {
            xboxController = new XboxController(Constants.XBOX_PORT); 
        } catch(Exception e) {
            System.out.format("Exception caught while initializing input subsystem: %s\n", e.getMessage());
        }

        try {
            joystickController = new Joystick(Constants.JOYSTICK_PORT); 
        } catch(Exception e) {
            System.out.format("Exception caught while initializing input subsystem: %s\n", e.getMessage());
        }
    }

    @Override
    public void periodic() {
        super.periodic();
        double xboxFrontBack = 0.0;
        double xboxLeftRight = 0.0;
        double xboxRotation = 0.0;
        double joystickFrontBack = 0.0;
        double joystickLeftRight = 0.0;
        double joystickRotation = 0.0;
        
        if (xboxController != null) {
            xboxFrontBack = xboxController.getLeftY();
            xboxLeftRight = xboxController.getLeftX();
            xboxRotation = xboxController.getRightX();
        }

        if (joystickController != null) {
            joystickFrontBack = joystickController.getY();
            joystickLeftRight = joystickController.getX();
            joystickRotation = joystickController.getZ();
        }

        // Intelligently combine simultaneous inputs
        frontBack = MathUtil.clamp(xboxFrontBack + joystickFrontBack, -1, 1);
        leftRight = MathUtil.clamp(xboxLeftRight + joystickLeftRight, -1, 1);
        rotation = MathUtil.clamp(xboxRotation + joystickRotation, -1, 1);

        if (frontBack <= Constants.DEADZONE || frontBack >= -Constants.DEADZONE) {
            frontBack = 0.0;
        }
        if (leftRight <= Constants.DEADZONE || leftRight >= -Constants.DEADZONE) {
            leftRight = 0.0;
        }
        if (rotation <= Constants.DEADZONE || rotation >= -Constants.DEADZONE) {
            rotation = 0.0;
        }
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
}
