// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;

/** Sets the speed of the elevator, requires elevator.java subsystem */
public class ElevatorSetSpeed extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Elevator elevator;
  private final double speed;

  
  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param elevator The subsystem used by this command.
   * @param speed The speed that the motor runs at, range -1 to 1
   */
  public ElevatorSetSpeed(Elevator elevator, double speed) {
    this.elevator = elevator;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.elevator.setSpeed(speed);
  }

  @Override
  public void end(boolean interrupted) {
    this.elevator.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
