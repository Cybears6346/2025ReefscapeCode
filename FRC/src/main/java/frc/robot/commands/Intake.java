// THIS WILL BE FOR AUTO IT WILL INTAKE AND THEN SHOOT FOR SECOND CORAL SCORE IN AUTO 

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.Timer;

/** Sets the speed of the elevator, requires elevator.java subsystem */
public class Intake extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  private final Timer timer = new Timer();

  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param Intake The subsystem used by this command.
   * 
   **/
    public Intake(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }
    //Counter for the elevator encoder

  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setSpeed(-1);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setSpeed(0); 
    timer.stop();
  }

  @Override
  public boolean isFinished() {
    return timer.hasElapsed(2); 
  }
}
