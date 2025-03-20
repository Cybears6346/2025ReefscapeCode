//THIS METHOD IS MOST LIKELY GOING TO BE SOLEY USED IN AUTONOMOUS, NOT TELEOP

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

/** Sets the speed of the elevator, requires elevator.java subsystem */
public class L4ElevatorDown extends Command{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Elevator elevator;
  private boolean isFinished = false;

  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param elevator The subsystem used by this command.
   **/
  public L4ElevatorDown(Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  // Counter for the elevator encoder
  private double previousEncoderValue = 0.3;
  private int revolutionCounter = 4;

  @Override
  public void initialize() {
    isFinished = false;
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Decrement down to 0 revolutions
    double currentEncoderValue = elevator.getElevatorEncoderValue();

    if (previousEncoderValue < 0.1 && currentEncoderValue > 0.9) {
      revolutionCounter--;
    }
    previousEncoderValue = currentEncoderValue;
    double totalRevolutions = revolutionCounter + currentEncoderValue;
    if (totalRevolutions > 0.42) { // Adjust this value if needed.
      elevator.setSpeed(1); // Adjust this for the speed
    } else {
      elevator.setSpeed(0);
      isFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.elevator.setSpeed(0);
    previousEncoderValue = 0.3;
    revolutionCounter = 4;
    isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}