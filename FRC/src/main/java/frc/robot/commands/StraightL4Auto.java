// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Driving;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

/** Sets the speed of the elevator, requires elevator.java subsystem */
public class StraightL4Auto extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Driving driving;
  private final Shooter shooter;
  private final Elevator elevator;
  private boolean isFinished = false;
  /**
   * Creates a new command named ElevatorSetSpeed.
   *
   * @param driving The subsystem used by this command.
   * 
   * 
   **/
    public StraightL4Auto(Driving driving, Shooter shooter, Elevator elevator) {
        this.driving = driving;
        this.shooter = shooter;
        this.elevator = elevator;
        addRequirements(driving);
    }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double encoder = driving.getDrivingEncoderValue();
    double encoderCounter = 0;
    if (Math.abs(encoderCounter) < 1000) {
      driving.arcadeDrive(encoder, 0);   
        encoderCounter += encoder;
    } else{
        new L4ElevatorShoot(this.elevator, this.shooter);
      isFinished = true;
    }
}

  @Override
  public void end(boolean interrupted) {
    driving.arcadeDrive(0,0);
    isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
