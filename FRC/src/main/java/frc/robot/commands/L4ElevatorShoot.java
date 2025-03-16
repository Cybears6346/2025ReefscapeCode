// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

/** Sets the speed of the elevator, requires elevator.java subsystem */
public class L4ElevatorShoot extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Elevator elevator;
  private final Shooter shooter;
  private boolean isFinished = false;
  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param elevator The subsystem used by this command.
   * 
   **/
    public L4ElevatorShoot(Elevator elevator, Shooter shooter) {
        this.shooter = shooter;
        this.elevator = elevator;
        addRequirements(elevator);
        addRequirements(shooter);
    }
    //Counter for the elevator encoder
    private double previousEncoderValue = 0;
    private int revolutionCounter = 0;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Incremet up to 3.5 revolutions
    double currentEncoderValue = elevator.getElevatorEncoderValue();
  
    if (previousEncoderValue > 0.9 && currentEncoderValue < 0.1) {
      revolutionCounter++;
    }
    previousEncoderValue = currentEncoderValue;
    double totalRevolutions = revolutionCounter + currentEncoderValue;
    if(totalRevolutions >4.2 && totalRevolutions < 4.8){
        shooter.setSpeed(-1);
    }
    if (totalRevolutions < 4.8) { //Adjust this value if needed.
        elevator.setSpeed(-1); //Adjust this for the speed
    } else {
        elevator.setSpeed(0);
        isFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    this.elevator.setSpeed(-0.02); //Helps to counteract gravity pulling the elevator down
    this.shooter.setSpeed(0); 
    previousEncoderValue = 0;
    revolutionCounter = 0;
    isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
