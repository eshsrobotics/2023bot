package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.InputSubsystem;

public class ManualArmCommand extends CommandBase {
    private InputSubsystem input;
    private ArmSubsystem arm;

    public ManualArmCommand(InputSubsystem input, ArmSubsystem arm) {
        this.input = input;
        this.arm = arm;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public void execute() {
        super.execute();

        // This sets the new arm x and y values to the current value plus the
        // value from inputSubsystem (which is -1, 0, or 1)
        //
        // NOTE: This may be increasing x and y too slowly, we may need to
        // multiply input.getArmX() and input.getArmY() by a constant to move
        // the arm faster
        // TODO: Make sure that the x and y values do not cause errors
        double xLength = arm.getX() + (input.getArmX() * Constants.ARM_SPEED_TO_INCHES);
        double yLength = arm.getY() + (input.getArmY() * Constants.ARM_SPEED_TO_INCHES);
        double armExtensionLength = Math.sqrt(Math.pow(xLength, 2) + Math.pow(xLength, 2));

        double[] arrangle = arm.computeArmInverseKinematics(xLength, yLength);
        double shoulderAngle = arrangle[0];
        double elbowAngle = arrangle[1];
        double wristAngle = arrangle[2];
        final double sMax = Constants.SHOULDER_MAX_ANGLE;
        final double sMin = Constants.SHOULDER_MIN_ANGLE;
        final double eMax = Constants.ELBOW_MAX_ANGLE;
        final double eMin = Constants.ELBOW_MIN_ANGLE;
        final double wMax = Constants.WRIST_MAX_ANGLE;
        final double wMin = Constants.WRIST_MIN_ANGLE;

        if (armExtensionLength > Constants.ARM_LENGTH) {
            // If the length we are trying to go to is longer than the maximum possible length
            if (input.getArmX() == 0) {
                // If only y is changing, change y normally and decrease x so the arm is still at the maximum length
                xLength = Math.sqrt(Math.pow(Constants.ARM_LENGTH, 2) - Math.pow(yLength, 2));
            }
            else if (input.getArmY() == 0) {
                // If only x is changing, change x normally and decrease y so the arm is still at the maximum length
                yLength = Math.sqrt(Math.pow(Constants.ARM_LENGTH, 2) - Math.pow(xLength, 2));
            } else {
                // If both x and y are being changed, rumble the controller
                input.rumbleXbox();
            }
        } else if ((shoulderAngle > sMax || shoulderAngle < sMin) ||
            (elbowAngle > eMax || elbowAngle < eMin) ||
            (wristAngle > wMax || wristAngle < wMin)) {
            // If any angle exceeds the maximum or minimum angle, don't change x and y and rumble the controller
            xLength = arm.getX();
            yLength = arm.getY();
            input.rumbleXbox();
        }
        arm.setX(xLength);
        arm.setY(yLength);
        
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
    
}