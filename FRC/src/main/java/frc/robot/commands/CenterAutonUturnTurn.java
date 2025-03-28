// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Driving;
import edu.wpi.first.wpilibj.Timer;

public class CenterAutonUturnTurn extends Command {
  private final Driving driving;

  private final Timer timer = new Timer();

  private boolean isFinished = false;

  public CenterAutonUturnTurn(Driving driving, Timer timer) {
      this.driving = driving;
      
      addRequirements(driving);
  }

  @Override
  public void initialize() {
    timer.reset();
        timer.start();  
      isFinished = false; 
  }

  @Override
  public void execute() {
    driving.arcadeDrive(0, 0.6); // OG VALUE 0.3
  }

  @Override  public boolean isFinished() {
      return timer.hasElapsed(0.5875);//OG TIME 2.35 SECONDS  Inverse Sqaure law, divided by 4
  }

  @Override
  public void end(boolean interrupted) {
        
      driving.arcadeDrive(0, 0); 
      timer.stop();
      driving.zeroDriveEncoder();
  }
}