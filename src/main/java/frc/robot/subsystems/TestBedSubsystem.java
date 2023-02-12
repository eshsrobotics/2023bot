package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ShuffleboardDebug;

/**
 * This class is used to test functions of the robot with the testbed
 */
public class TestBedSubsystem extends SubsystemBase {

    private InputSubsystem inputSubsystem;
    private ShuffleboardDebug debug;
    private CANSparkMax motor1;
    private CANSparkMax motor2;
    private MotorController motor3;

    public TestBedSubsystem(InputSubsystem inputSubsystem, ShuffleboardDebug debug) {
        this.inputSubsystem = inputSubsystem;
        this.debug = debug;
        motor1 = new CANSparkMax(1, MotorType.kBrushed);
        motor2 = new CANSparkMax(9, MotorType.kBrushed);
        motor3 = new PWMSparkMax(1);
    }

    @Override
    public void periodic() {
        super.periodic();
        double rotation = inputSubsystem.getRotation();
        double frontBack = inputSubsystem.getFrontBack();
        debug.joystickRotation.setDouble(rotation * 100);
        debug.joystickFrontBack.setDouble(frontBack * 100);
        if (rotation == 0) {
            motor1.stopMotor();
        } else {
            motor1.set(rotation);
        }
        if (frontBack == 0) {
            motor3.stopMotor();
        } else {
            motor3.set(frontBack);
        }
        if (inputSubsystem.getA()) {
            motor2.set(1);
        } else if (inputSubsystem.getB()) {
            motor2.set(-1);
        } else {
            motor2.stopMotor();
        }
    }
    
}