// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.InputSubsystem;
import frc.robot.subsystems.VroomSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private VroomSubsystem driveSubsystem;
  private InputSubsystem inputSubsystem;
  private ShuffleboardDebug shuffleboardDebug;

  private Command teleopCompositeCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    shuffleboardDebug = new ShuffleboardDebug();
    inputSubsystem = new InputSubsystem(shuffleboardDebug);
    driveSubsystem  = new VroomSubsystem(inputSubsystem, shuffleboardDebug);
    
    // Create a command that repeatedly performs any one of the following until
    // that particular action has ended:
    //
    // 1. If the user hits the reset button, returns the arm to its home
    //    position.
    // 2. If the user hits the low goal button, moves the arm to the low goal
    //    (x, y) position.
    // 3. If the user hits the high goal button, moves the arm to the high goal
    //    (x, y) position.
    // 4. If the user has done none of the above, spontaneously operate the arm
    //    in full manual mode.
    //
    // These commands are all InstantCommands, but the first three have a much
    // longer duration than the last one.
    teleopCompositeCommand = new RepeatCommand(new InstantCommand(() -> {
      // This repeat command dispatches other commands and then exits
      final int RESET_STATE = 1;
      final int LOW_STATE = 2;
      final int HIGH_STATE = 3;
      final int MANUAL_OPERATION_FOR_NOW = 4;
      
      Command resetCommand = new WaitCommand(2.0)
        .beforeStarting(() -> System.out.printf("Started reset\n"))
        .andThen(() -> System.out.printf("Ended reset\n"));
      
      Command lowCommand = new WaitCommand(5.0)
        .beforeStarting(() -> System.out.printf("Moving to low goal\n"))
        .andThen(() -> System.out.printf("Done moving to low goal\n"));
      
      final Map<Integer, Command> commandMap = 
        Map.of(RESET_STATE, resetCommand,
               LOW_STATE, lowCommand);

      // Choose what arm state to be in based on human input.
      Supplier<Integer> stateSelector = () -> {
        // TODO: Finish this.
        return MANUAL_OPERATION_FOR_NOW;
      };

      
    }, inputSubsystem));
  
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public void enableAutonomous() {
    driveSubsystem.setAutonomous(true);
  }
  
  public void disableAutonomous() {
    driveSubsystem.setAutonomous(false);
  }
}
