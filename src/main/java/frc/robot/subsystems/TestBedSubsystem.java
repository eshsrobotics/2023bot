package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This class is used to test functions of the robot with the testbed
 */
public class TestBedSubsystem extends SubsystemBase {

    private InputSubsystem inputSubsystem;

    public TestBedSubsystem(InputSubsystem inputSubsystem) {
        this.inputSubsystem = inputSubsystem;
    }

    @Override
    public void periodic() {
        super.periodic();
        if (inputSubsystem.getRotation() == 0) {
            
        }
    }
    
}