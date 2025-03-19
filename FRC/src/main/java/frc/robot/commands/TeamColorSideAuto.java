
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Driving;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TeamColorSideAuto extends Command {
  private final Driving driving;
  private final Shooter shooter;
  private final Elevator elevator;
  
  private final double targetEncoderValue = 100; 
  private boolean isFinished = false;

  public TeamColorSideAuto(Driving driving, Shooter shooter, Elevator elevator) {
      this.driving = driving;
      this.shooter = shooter;
      this.elevator = elevator;
      addRequirements(driving, elevator, shooter);
  }

  @Override
  public void initialize() {
      isFinished = false;
  }

  @Override
  public void execute() {
      double encoderValue = driving.getDrivingEncoderValue();
      
      if (encoderValue < targetEncoderValue) {
          driving.arcadeDrive(0.5, 0); 
      } else {
          driving.arcadeDrive(0, 0); 
          new L4ElevatorShoot(elevator, shooter).schedule(); 
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
  }
}