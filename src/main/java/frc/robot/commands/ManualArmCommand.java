package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.InputSubsystem;

public class ManualArmCommand extends CommandBase {
    private InputSubsystem input;
    private ArmSubsystem arm;

    public ManualArmCommand(InputSubsystem input, ArmSubsystem arm) {
        this.input = input;
        this.arm = arm;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();

        // This sets the new arm x and y values to the current value plus the
        // value from inputSubsystem (which is -1, 0, or 1)
        //
        // NOTE: This may be increasing x and y too slowly, we may need to
        // multiply input.getArmX() and input.getArmY() by a constant to move
        // the arm faster
        // TODO: Make sure that the x and y values do not cause errors
        arm.setX(arm.getX() + (input.getArmX() * Constants.ARM_SPEED_TO_INCHES));
        arm.setY(arm.getY() + (input.getArmY() * Constants.ARM_SPEED_TO_INCHES));
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
    
}