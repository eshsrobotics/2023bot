package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShuffleboardDebug;

/**
 * Use X and Y values for the position of the hand to create motor values for
 * the joints of the arm
 */
public class ArmSubsystem extends SubsystemBase {

    /**
     * The variable for the horizontal distance (in inches) from the shoulder to
     * the wrist
     */
    private double x;

    /**
     * The variable for the vertical distance (in inches) from the shoulder to
     * the wrist
     */
    private double y;

    /**
     * This is the motor for the shoulder, which is the motor that is attached
     * to the the tower (unofficial name) on the robot
     *
     * This variable, elbowMotor, and wristMotor are all nullable (It is fine
     * for any of these three variables to be null)
     */
    private CANSparkMax shoulderMotor;

    /**
     * This is the elbow motor of the robot, the motor that is in between the
     * shoulder and wrist
     */
    private CANSparkMax elbowMotor;

    /**
     * This is the wrist motor of the robot, the motor that is connected to the
     * hand of the robot to keep it facing straight down
     */
    private CANSparkMax wristMotor;

    /**
     * The variable for the angle the shoulder motor should move to based on x
     * and y
     */
    private double shoulderAngleDegrees;

    /**
     * The variable for the angle the elbow motor should move to based on x and
     * y
     */
    private double elbowAngleDegrees;

    /**
     * The variable for the angle the wrist motor should move to based on x and
     * y
     */
    private double wristAngleDegrees;

    /**
     * The PID controller for the motor that moves the shoulder of the arm
     * 
     * Used to move the motor more accurately
     */
    private PIDController shoulderPID;

    /**
     * The PID controller for the motor that moves the elbow of the arm
     * 
     * Used to move the motor more accurately
     */
    private PIDController elbowPID;

    /**
     * The PID controller for the motor that moves the wrist of the arm
     * 
     * Used to move the motor more accurately
     */
    private PIDController wristPID;

    private ShuffleboardDebug debug;

    /**
     * The constructor takes two values, one for the horizontal distance from
     * the shoulder ot the wrist, and one for the vertical distance from the
     * shoulder to the wrist, and sets the x and y variables to those values
     *
     * If the two starting values for x and y are wrong, the entire arm won't
     * work
     */
    public ArmSubsystem(double xInches, double yInches, ShuffleboardDebug debug) {
        x = xInches;
        y = yInches;

        this.debug = debug;

        // TODO: Fill in actual motors
        shoulderMotor = null;
        elbowMotor = null;
        wristMotor = null;

        shoulderPID = new PIDController(Constants.SHOULDER_kP, Constants.SHOULDER_kI, Constants.SHOULDER_kD);
        elbowPID = new PIDController(Constants.ELBOW_kP, Constants.ELBOW_kI, Constants.ELBOW_kD);
        wristPID = new PIDController(Constants.WRIST_kP, Constants.WRIST_kI, Constants.WRIST_kD);
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        super.periodic();
    }

    private void calculateArmAngles() {
        final double L1 = Constants.SHOULDER_TO_ELBOW_INCHES;
        final double L2 = Constants.ELBOW_TO_WRIST_INCHES;
        double h = Math.sqrt((x * x) + (y * y));
        double gamma = Math.acos(((h * h) - (L1 * L1) - (L2 * L2)) / (-2 * L1 * L2));
        double alpha = Math.asin((L2 * Math.sin(gamma)) / h);
        double beta = Math.PI - alpha - gamma;
        double theta = Math.asin(y / h);
        double fi = Math.asin(x / h);
        shoulderAngleDegrees = (alpha * Constants.RADIANS_TO_DEGREES) + (theta * Constants.RADIANS_TO_DEGREES);
        elbowAngleDegrees = gamma * Constants.RADIANS_TO_DEGREES;
        wristAngleDegrees = (beta * Constants.RADIANS_TO_DEGREES) + (fi * Constants.RADIANS_TO_DEGREES);
        debug.shoulderAngle.setDouble(shoulderAngleDegrees);
        debug.elbowAngle.setDouble(elbowAngleDegrees);
        debug.wristAngle.setDouble(wristAngleDegrees);
    }

    /**
     * Sets the variable for the horizontal distance from the shoulder joint to
     * the wrist joint of the arm - This forms the bottom leg of a right
     * triangle
     */
    public void setX(double xInches) {
        x = xInches;
    }

    /**
     * Returns the variable for the horizontal distance (in inches) from the
     * shoulder joint to the wrist joint of the arm
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the variable for the vertical distance from the shoulder joint to
     * the wrist joint of the arm - This forms the vertical leg of a right
     * triangle
     */
    public void setY(double yInches) {
        y = yInches;
    }

    /**
     * Returns the variable for the vertical distance (in inches) from the
     * shoulder joint to the wrist joint of the arm
     */
    public double getY() {
        return y;
    }
}