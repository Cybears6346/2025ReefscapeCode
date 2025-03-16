// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.Command;

import static edu.wpi.first.units.Units.Newton;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;




/** Sets the speed of the elevator, requires elevator.java subsystem */
public class L4ElevatorDown extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Elevator elevator;

  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param elevator The subsystem used by this command.
   * 
   **/
    public L4ElevatorDown(Elevator elevator) {
        this.elevator = elevator;
        addRequirements(elevator);
    }
    //Counter for the elevator encoder
    private double previousEncoderValue = 0;  // Initialize to our recorded revolutions we had earlier
    private int revolutionCounter = 0; // Initialize to our recorded revolutions we had earlier

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    // if (elevator.getElevatorEncoderValue() < 0.9 ){
    //     elevator.setSpeed(-0.5);
    //     }
    //     else {
    //     elevator.setSpeed(0);
    // }

    // Decrement up to 3.5 revolutions
    double currentEncoderValue = elevator.getElevatorEncoderValue();
    if (currentEncoderValue > previousEncoderValue) {
        revolutionCounter--;
    }
    // if (previousEncoderValue < 0.1 && currentEncoderValue > 0.9) {
    //     revolutionCounter--;
    // }
    previousEncoderValue = currentEncoderValue;
    double totalRevolutions = revolutionCounter + currentEncoderValue;
    if (totalRevolutions < 3.5) {
        elevator.setSpeed(0.5);
    } else {
        elevator.setSpeed(0);
    }
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
