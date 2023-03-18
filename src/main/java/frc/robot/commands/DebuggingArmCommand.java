package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

class DebuggingArmCommand extends CommandBase {

    public enum JointType {
        WRIST,
        ELBOW,
        SHOULDER    
    }

    private JointType jointType;
    private PIDController pidController;

    /**
     * Makes an instance of the {@link DebuggingArmCommand debugger arm command}.
     * 
     * @param jointType The type of joint that this command will be manipulating.
     *                  All other joints will remain untouched.
     */
    public DebuggingArmCommand(JointType jointType) {
        this.jointType = jointType;
        switch (jointType) {
            case WRIST:
                pidController = new PIDController(Constants.WRIST_kP, Constants.WRIST_kI, Constants.WRIST_kD);
                pidController.setTolerance(Constants.PID_TOLERANCE_DEGREES);
                break;
            case ELBOW:
                pidController = new PIDController(Constants.ELBOW_kP, Constants.ELBOW_kI, Constants.ELBOW_kD);
                pidController.setTolerance(Constants.PID_TOLERANCE_DEGREES);
                break;
            case SHOULDER:
                pidController = new PIDController(Constants.SHOULDER_kP, Constants.SHOULDER_kI, Constants.SHOULDER_kD);
                pidController.setTolerance(Constants.PID_TOLERANCE_DEGREES);
                break;
        }
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
    }

    
}
