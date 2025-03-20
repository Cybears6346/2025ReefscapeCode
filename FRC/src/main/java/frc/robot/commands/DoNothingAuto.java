// FIXME: Reference this for command functions when constructing new ones. Else keep commented.
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Driving;

import java.util.Queue;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class DoNothingAuto extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Driving driving;
  private boolean isFinished = false;   

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DoNothingAuto(Driving driving) {
    this.driving = driving; 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driving);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driving.arcadeDrive(0, 0);
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    driving.arcadeDrive(0, 0);
    isFinished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driving.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
