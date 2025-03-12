// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;

/** Sets the speed of the shooter, requires shooter.java subsystem */
public class ShooterSetSpeed extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Shooter shooter;
  private final DoubleSupplier speed;

  
  /**
   * Creates a new command named shooterSetSpeed.
   *
   * @param shooter The subsystem used by this command.
   * @param speed The speed that the motor runs at, range -1 to 1
   */
  public ShooterSetSpeed(Shooter shooter, DoubleSupplier speed) {
    this.shooter = shooter;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  public ShooterSetSpeed(Shooter shooter, double speed) {
    this.shooter = shooter;
    this.speed = () -> speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.shooter.setSpeed(speed);
  }

  @Override
  public void end(boolean interrupted) {
    this.shooter.setSpeed(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
