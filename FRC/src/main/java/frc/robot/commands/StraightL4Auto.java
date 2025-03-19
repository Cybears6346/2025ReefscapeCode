// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.Driving;
// import frc.robot.subsystems.Elevator;
// import frc.robot.subsystems.Shooter;
// import frc.robot.commands.*;
// import frc.robot.subsystems.*;
// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;

// /** Sets the speed of the elevator, requires elevator.java subsystem */
// public class StraightL4Auto extends Command {
//   @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//   private final Driving driving;
//   private final Shooter shooter;
//   private final Elevator elevator;
//   private boolean isFinished = false;
//   /**
//    * Creates a new command named ElevatorSetSpeed.
//    *
//    * @param driving The subsystem used by this command.
//    * 
//    * 
//    **/
//     public StraightL4Auto(Driving driving, Shooter shooter, Elevator elevator) {
//         this.driving = driving;
//         this.shooter = shooter;
//         this.elevator = elevator;
//         addRequirements(driving);
//     }


// double encoder;
// double encoderCounter;

//   @Override
//   public void initialize() {
//     encoder = 0;
//     encoderCounter = 0;
//   }
//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {

//      encoder = driving.getDrivingEncoderValue();
//     System.out.println(encoderCounter);
//     encoderCounter++;
//     if (encoderCounter < 10) {
//       driving.arcadeDrive(0.5,0);
       
//     } else {
//       new L4ElevatorShoot(elevator, shooter);
//     }
// }

//   @Override
//   public void end(boolean interrupted) {
//     driving.arcadeDrive(0,0);
//     isFinished = false;
//   }

//   @Override
//   public boolean isFinished() {
//     return isFinished;
//   }
// }

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Driving;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class StraightL4Auto extends Command {
  private final Driving driving;
  private final Shooter shooter;
  private final Elevator elevator;
  
  private final double targetEncoderValue = 100; 
  private boolean isFinished = false;

  public StraightL4Auto(Driving driving, Shooter shooter, Elevator elevator) {
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