package frc.robot.subsystems;

import java.io.IOException;
import java.nio.file.Path;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;

/**
 * This subsystem controls the drive motors for our robot's West Coast drive. We
 * use two motor groups -- left and right -- to control sets of motors that are
 * connected to a planetary gearbox on each side.
 */
public class VroomSubsystem implements Subsystem {

    private CANSparkMax backRight;
    private CANSparkMax backLeft;
    private CANSparkMax frontRight;
    private CANSparkMax frontLeft;

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
     * {@link MecanumDriveKinematics} converts {@link ChassisSpeeds} to a {@link MecanumDriveWheelSpeeds}
     */
    private MecanumDriveKinematics kinematics;

    /**
     * Keeps track of the bot's position in space.
     */
    private MecanumDriveOdometry mecanumDriveOdometry;

    /**
     * Keeps track of the robot's rotation since the last call to {@link #reset()}.
     */
    private ADXRS450_Gyro gyro;
    
    private PIDController frontBackPidController;
    private PIDController leftRightPidController;
    private ProfiledPIDController profiledRotationPidContoller;
    
    /**
     * {@link HolonomicDriveController} converts a trajectory into {@link ChassisSpeeds}
     */
    private HolonomicDriveController autonomousController;

    /**
     * Used to determine if the robot is currently in the autonomous phase of
     * the game
     */
    private boolean isAutonomous;

    /**
     * Used to get the values of the Xbox controller joysticks (left joystick
     * up-down and left-right, and right joystick left-right) or the joystick
     * left-right, up-down, and rotation values
     */
    private InputSubsystem inputSubsystem;

    /**
     * Takes the robots pose at different moments along the autonomus path. 
     */
    private Trajectory trajectory; 

    /**
     * Represents the time in seconds when our autonomous trajectory started
     */
    private double trajectoryTimeSecondsStart;

    /** 
     * Represents the gyro angle at the start of autonomous
     */
    private Rotation2d orientationAtStart;
    
    /**
     * The constructor initializes the vroom subsystem.
     */
    public VroomSubsystem(InputSubsystem inputSubsystem) {
        backRight = new CANSparkMax(Constants.BACK_RIGHT_CAN_ID, MotorType.kBrushed);
        frontRight = new CANSparkMax(Constants.FRONT_RIGHT_CAN_ID, MotorType.kBrushed);
        backLeft = new CANSparkMax(Constants.BACK_LEFT_CAN_ID, MotorType.kBrushed);
        frontLeft = new CANSparkMax(Constants.FRONT_LEFT_CAN_ID, MotorType.kBrushed);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        kinematics = new MecanumDriveKinematics(new Translation2d(-Constants.CHASSIS_WIDTH_INCHES / 2, Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(Constants.CHASSIS_WIDTH_INCHES / 2, Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(-Constants.CHASSIS_WIDTH_INCHES / 2, -Constants.CHASSIS_LENGTH_INCHES / 2),
                                                new Translation2d(Constants.CHASSIS_WIDTH_INCHES / 2, -Constants.CHASSIS_LENGTH_INCHES / 2));

        // TODO: Make sure that gyro is stabilized before calling differentialDriveOdometery.
        gyro = new ADXRS450_Gyro();
        mecanumDriveOdometry = new MecanumDriveOdometry(kinematics, gyro.getRotation2d(), getEncodersDistanceMeters());
        frontBackPidController = new PIDController(Constants.P_FRONT_BACK, Constants.I_FRONT_BACK, Constants.D_FRONT_BACK);
        leftRightPidController = new PIDController(Constants.P_LEFT_RIGHT, Constants.I_LEFT_RIGHT, Constants.D_LEFT_RIGHT);
        profiledRotationPidContoller = new ProfiledPIDController(Constants.P_PROFILED_ROTATION,
                                                                 Constants.I_PROFILED_ROTATION,
                                                                 Constants.D_PROFILED_ROTATION, 
                                                                 new Constraints(Constants.MAXIMUM_VELOCITY_INCHES_PER_SECOND,
                                                                                 Constants.MAXIMUM_ACCELERATION_INCHES_PER_SECOND_SQUARED));
        autonomousController = new HolonomicDriveController(leftRightPidController, 
                                                            frontBackPidController, 
                                                            profiledRotationPidContoller);
        isAutonomous = false;
        
        this.inputSubsystem = inputSubsystem;
        try {
            trajectory = TrajectoryUtil.fromPathweaverJson(Path.of(Constants.AUTONOMOUS_JSON_PATH));
        } catch (IOException e) {
            trajectory = null;
            System.out.format("Caught IOException while opening '%s': %s\n", 
                Constants.AUTONOMOUS_JSON_PATH,
                e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void setAutonomous(boolean a) {
        isAutonomous = a;
        trajectoryTimeSecondsStart = Timer.getFPGATimestamp();
    } 
    /**
     * Resets the starting position of the robot to be (0, 0). Call this during the
     * beginning of a match right before autonomous, por favor.
     */
    public void reset() {
        // After reset, we assume the the current position on the felid is 0, 0.
        // TODO: Put in code to reset the encoders.
        gyro.reset();
        final Pose2d assumedPoseOnReset = new Pose2d(0, 0, gyro.getRotation2d());
        mecanumDriveOdometry.resetPosition(gyro.getRotation2d(), getEncodersDistanceMeters(), assumedPoseOnReset);
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
        mecanumDriveOdometry.update(gyro.getRotation2d(), getEncodersDistanceMeters());
        
        if (isAutonomous) {
            // Autonomous runs HERE
            // Checking that we have a trajectory loaded
            double elapsedAutonTimeSeconds = Timer.getFPGATimestamp() - trajectoryTimeSecondsStart;
            if (trajectory != null &&  elapsedAutonTimeSeconds <= trajectory.getTotalTimeSeconds()) {
                // This Code assumes we never want to rotate the robot relative to the field during autonomous
                Trajectory.State state = trajectory.sample(elapsedAutonTimeSeconds);
                ChassisSpeeds cs = autonomousController.calculate(mecanumDriveOdometry.getPoseMeters(), 
                                                                  state, 
                                                                  orientationAtStart);
                MecanumDriveWheelSpeeds mdws = kinematics.toWheelSpeeds(cs);
                double flPower = MathUtil.clamp(mdws.frontLeftMetersPerSecond / Constants.MAXIMUM_VELOCITY_METERS_PER_SECOND, -1.0, 1.0);
                double frPower = MathUtil.clamp(mdws.frontRightMetersPerSecond / Constants.MAXIMUM_VELOCITY_METERS_PER_SECOND, -1.0, 1.0);
                double blPower = MathUtil.clamp(mdws.rearLeftMetersPerSecond / Constants.MAXIMUM_VELOCITY_METERS_PER_SECOND, -1.0, 1.0);
                double brPower = MathUtil.clamp(mdws.rearRightMetersPerSecond / Constants.MAXIMUM_VELOCITY_METERS_PER_SECOND, -1.0, 1.0);
                frontLeft.set(flPower);
                frontRight.set(frPower);
                backLeft.set(blPower);
                backRight.set(brPower);
            }
        } else {
            // Teleop runs here
            // Field-oriented mecanum
            drive.driveCartesian(inputSubsystem.getFrontBack(),
                                 inputSubsystem.getLeftRight(),
                                 inputSubsystem.getRotation(),
                                 gyro.getRotation2d());
        }
    }

}
   