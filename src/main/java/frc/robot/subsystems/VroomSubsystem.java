package frc.robot.subsystems;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

/**
 * This subsystem controls the drive motors for our robot's West Coast drive. We
 * use two motor groups -- left and right -- to control sets of motors that are
 * connected to a planetary gearbox on each side.
 */
public class VroomSubsystem implements Subsystem {

    private PWMMotorController backRight;
    private PWMMotorController backLeft;
    private PWMMotorController frontRight;
    private PWMMotorController frontLeft;

    /**
     * In order to make a drive with four Mecanum wheels go _vroom_, you need to apply the
     * Mecanum formula to calculate all four wheel speeds.  Fortunately, WPILib has a handy
     * class we can use to do this already.
     * 
     * This class is _only_ used to drive during teleop.  During autonomous, we use a
     * {@link HolonomicDriveController} instead.
     */
    private MecanumDrive drive;


    /**
     * {@link MecanumDriveKinematics} are one of the necessary prerequisite for {@link MecanumDriveOdometry}.
     */
    private MecanumDriveKinematics kinematics;

    /**
     * Keeps track of the bot's position in space.
     */
    private MecanumDriveOdometry mecanumDriveOdometry;

    private ADXRS450_Gyro gyro;
    
    /**
     * The constructor initializes the vroom subsystem.
     */
    public VroomSubsystem() {
        backRight = new Spark(Constants.BACK_RIGHT_PWM_PORT);
        frontRight = new Spark(Constants.FRONT_RIGHT_PWM_PORT);
        backLeft = new Spark(Constants.BACK_LEFT_PWM_PORT);
        frontLeft = new Spark(Constants.FRONT_LEFT_PWM_PORT);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        kinematics = new MecanumDriveKinematics(new Translation2d(-Constants.CHASSIS_WIDTH_INCHES / 2, Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(Constants.CHASSIS_WIDTH_INCHES / 2, Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(-Constants.CHASSIS_WIDTH_INCHES / 2, -Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(Constants.CHASSIS_WIDTH_INCHES / 2, -Constants.CHASSIS_LENGTH_INCHES / 2));

        // TODO: Make sure that gyro is stabilized before calling differentialDriveOdometery.
        gyro = new ADXRS450_Gyro();
        differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 
                                                                  getEncoderLeftMeters(), 
                                                                  getEncoderRightMeters());

        // differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    /**
     * Resets gyros and drive base encoders of the bot. Call this during the
     * beginning of a match por favor.
     */
    public void reset() {
        // TODO: Put in code to reset the encoders and gyros
        differentialDriveOdometry.resetPosition(gyro.getRotation2d(), 0, 0, new Pose2d());
    }

    /**
     * A essential prerequisite for the {@link MecanumDriveOdometry}, this function 
     * returns the total distance the wheels have traveled in meters.
     * 
     * <p>TODO: These values are fake, replace when we get encoders.</p>
     * 
     * @return Four encoder-measured distances.
     */
    private MecanumDriveWheelPositions getEncodersDistanceMeters() {
        MecanumDriveWheelPositions result = new MecanumDriveWheelPositions(0, 0, 0, 0);
        return result;
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        Subsystem.super.periodic();
        differentialDriveOdometry.update(gyro.getRotation2d(), 
                                         getEncoderLeftMeters(), 
                                         getEncoderRightMeters());
    }

}
   