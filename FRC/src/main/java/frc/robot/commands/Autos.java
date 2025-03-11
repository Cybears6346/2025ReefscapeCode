// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Driving;
import frc.robot.subsystems.AlgaeArm;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(Driving subsystem, AlgaeArm algaeArm) {
    return Commands.sequence(new ArcadeDrive(subsystem, 0, 0), new AlgaeArmSetSpeed(algaeArm, 0));
  }// - TODO: REPLACE WITH DRIVE SUBSYSTEM

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
