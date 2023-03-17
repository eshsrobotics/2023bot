package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ShuffleboardDebug;

public class ClawSubsystem extends SubsystemBase {
    // The motors for the goliath intake, there will be a motor on each "finger"
    // of the claw
    private MotorController goliath1;
    private MotorController goliath2;

    private ShuffleboardDebug debug;
    private InputSubsystem input;

    public ClawSubsystem(InputSubsystem input, ShuffleboardDebug debug) {
        this.debug = debug;
        this.input = input;
    }
}
