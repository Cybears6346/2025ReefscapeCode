// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.AlgaeArm;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;

/** Sets the speed of the algae arm, requires AlgaeArm.java subsystem */
public class AlgaeArmSetSpeed extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final AlgaeArm algaeArm;
  private final double speed;

  
  /**
   * Creates a new command named AlgaeArmSetSpeed.
   *
   * @param algaeArm The subsystem used by this command.
   * @param speed The speed that the motor runs at, range -1 to 1
   */
  public AlgaeArmSetSpeed(AlgaeArm algaeArm, double speed) {
    this.algaeArm = algaeArm;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(algaeArm);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.algaeArm.setSpeed(speed);
  }

  @Override
  public void end(boolean interrupted) {
    this.algaeArm.setSpeed(0);
  }

  public boolean isFinished() {
    return false;
  }
}
