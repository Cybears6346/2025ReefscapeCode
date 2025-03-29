// FIXME: Reference this for command functions when constructing new ones. Else keep commented.
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.Driving;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;

import java.util.Queue;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class Delay extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private boolean isFinished = false;  
  private final Timer timer = new Timer(); 
  

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public Delay(Timer timer) {   
    
    // Use addRequirements() here to declare subsystem dependencies
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();  
    timer.start();
    isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(1);
  }
}
