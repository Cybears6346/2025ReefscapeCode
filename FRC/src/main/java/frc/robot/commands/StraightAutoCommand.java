// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Driving;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StraightAutoCommand extends Command {
  private final Driving driving;
  
  private final double targetEncoderValue = 5.5; // EDIT THIS TO CHANCE THE DISTANCE (0.3 speed, 22; 0.5 speed, 20)
  private boolean isFinished = false;

  public StraightAutoCommand(Driving driving) {
      this.driving = driving;
      addRequirements(driving);
  }

  @Override
  public void initialize() {
    driving.zeroDriveEncoder();
      isFinished = false; 
  }

  @Override
  public void execute() {
      double encoderValue = driving.getDrivingEncoderValue();
      if (encoderValue < targetEncoderValue) {
          driving.arcadeDrive(0.5, 0); 
      } else {
          driving.arcadeDrive(0, 0);
         isFinished = true; 
      }
  }

  @Override
  public boolean isFinished() {
      return isFinished;
  }

  @Override
  public void end(boolean interrupted) {
        
      driving.arcadeDrive(0, 0); 
      driving.zeroDriveEncoder();
  }
}