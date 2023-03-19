// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.InputSubsystem;
import frc.robot.subsystems.VroomSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
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

  private Command m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private VroomSubsystem driveSubsystem;
  private InputSubsystem inputSubsystem;
  private ShuffleboardDebug shuffleboardDebug;
  private ClawSubsystem clawSubsystem;
  private WristSubsystem wristSubsystem;

  private Command teleopCompositeCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    shuffleboardDebug = new ShuffleboardDebug();
    inputSubsystem = new InputSubsystem(shuffleboardDebug);
    driveSubsystem  = new VroomSubsystem(inputSubsystem, shuffleboardDebug);
    clawSubsystem = new ClawSubsystem(inputSubsystem, shuffleboardDebug);
    wristSubsystem = new WristSubsystem(inputSubsystem);
    
    teleopCompositeCommand = new RepeatCommand(new InstantCommand(() -> {
      // This repeat command dispatches other commands and then exits
      

    }, inputSubsystem));

    m_autoCommand = new ParallelDeadlineGroup(
      new WaitCommand(Constants.AUTON_BACKWARD_TIME_SECONDS), 
      new InstantCommand(() -> {
        // Start moving backward.
        // Note: this actually makes the robot go forward
        driveSubsystem.setSimpleDrive(-1, 0, 0);
      })
    ).andThen(new InstantCommand(() -> {
      // Stop moving.
      driveSubsystem.setSimpleDrive(0, 0, 0);
    }));
  
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
    driveSubsystem.setAutonomousType(VroomSubsystem.AutonomousType.SIMPLE);
  }
  
  public void disableAutonomous() {
    driveSubsystem.setAutonomousType(VroomSubsystem.AutonomousType.TELEOP);
  }
}
