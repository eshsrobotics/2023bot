package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WristSubsystem extends SubsystemBase {
    private CANSparkMax wristMotor;
    private RelativeEncoder encoder;

    private InputSubsystem input;

    private boolean passedMin;
    private double currentAngle;

    public WristSubsystem(InputSubsystem input) {
        wristMotor = new CANSparkMax(0, MotorType.kBrushless);
        encoder = wristMotor.getEncoder();
        encoder.setPositionConversionFactor(360);
        this.input = input;
        passedMin = false;
        currentAngle = Constants.WRIST_START_ANGLE;
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        super.periodic();
        double currentInputAngleDegrees = encoder.getPosition();
        currentAngle = currentInputAngleDegrees / Constants.WRIST_GEAR_RATIO;
        if (currentAngle > Constants.WRIST_MIN_ANGLE) {
            passedMin = true;
        }
        boolean wristUp = input.getWristUp();
        boolean wristDown = input.getWristDown();
        if ((wristUp && !wristDown && currentAngle < Constants.WRIST_MIN_ANGLE) ||
            (wristDown && !wristUp && currentAngle > Constants.WRIST_MAX_ANGLE)) {
            input.rumbleXbox();
        } else {
            if (wristUp && !wristDown && !passedMin) {
                // Move wrist up
                wristMotor.set(Constants.WRIST_MOTOR_SPEED);
            } else if (wristDown && !wristUp) {
                // Move wrist down
                wristMotor.set(-Constants.WRIST_MOTOR_SPEED);
            } else {
                wristMotor.stopMotor();
            }
        }
    }
    
}
