package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;

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
     * Uses motor groups to drive bot.
     */
    private DifferentialDrive differentialDrive;

    /**
     * Groups together the motors on the left side of the chassis.
     */
    private MotorControllerGroup leftMotorGroup;
    private MotorControllerGroup rightMotorGroup;

    /**
     * Keeps track of the bot's position in space.
     */
    private DifferentialDriveOdometry differentialDriveOdometry;

    private ADXRS450_Gyro gyro;

    public VroomSubsystem() {
        backRight = new Spark(4);
        frontRight = new Spark(3);
        rightMotorGroup = new MotorControllerGroup(backRight, frontRight);
        backLeft = new Spark(2);
        frontLeft = new Spark(1);
        leftMotorGroup = new MotorControllerGroup(backLeft, frontLeft);
        differentialDrive = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

        // TODO: Make sure that gyro is stabilized before calling differentialDriveOdometery.
        gyro = new ADXRS450_Gyro();
        // differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d(), 
        //                                                           getEncoderLeftMeters(), 
        //                                                           getEncoderRightMeters());

        differentialDriveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    /**
     * Resets gyros and drive base encoders of the bot. Call this during the
     * beginning of a match por favor.
     */
    public void reset() {
        // TODO: Put in code to reset the encoders and gyros
        differentialDriveOdometry.resetPosition(new Pose2d(), gyro.getRotation2d());
    }

    private double getEncoderLeftMeters() {
        // TODO: Put in code to get the left encoder and convert to meter.
        return 0.0;
    }

    private double getEncoderRightMeters() {
        // TODO: Put in code to get the right encoder and convert to meter.
        return 0.0;
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
