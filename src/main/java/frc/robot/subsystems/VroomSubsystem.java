package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This subsystem controls the drive motors for our robot's
 * West Coast drive.  We use two motor groups -- left and
 * right -- to control sets of motors that are connected
 * to a planetary gearbox on each side.
 */
public class VroomSubsystem implements Subsystem {

    private PWMMotorController backRight;
    private PWMMotorController backLeft;
    private PWMMotorController frontRight;
    private PWMMotorController frontLeft;

    /**
     * Groups together the motors on the left side of the chassis.
     */
    private MotorControllerGroup leftMotorGroup;
    private MotorControllerGroup rightMotorGroup;

    public VroomSubsystem() {
        backRight = new Spark(4);
        frontRight = new Spark(3);
        rightMotorGroup = new MotorControllerGroup(backRight, frontRight);
        backLeft = new Spark(2);
        frontLeft = new Spark(1);
        leftMotorGroup = new MotorControllerGroup(backLeft, frontLeft);
        


    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        Subsystem.super.periodic();
    }
    
}
