// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class Driving extends SubsystemBase {
  private final SparkMax leftFrontMotor;
  private final SparkMax leftRearMotor;
  private final SparkMax rightFrontMotor;
  private final SparkMax rightRearMotor;
  private final DifferentialDrive differentialDrive;

  /** Creates a new ExampleSubsystem. */
  public Driving() {
    leftFrontMotor = new SparkMax(OperatorConstants.driveMotor4ID, MotorType.kBrushless);
    leftRearMotor = new SparkMax(OperatorConstants.driveMotor3ID, MotorType.kBrushless);
    rightFrontMotor = new SparkMax(OperatorConstants.driveMotor2ID, MotorType.kBrushless);
    rightRearMotor = new SparkMax(OperatorConstants.driveMotor1ID, MotorType.kBrushless);
    SparkMaxConfig globalLeaderConfig = new SparkMaxConfig();
    globalLeaderConfig
          .smartCurrentLimit(50)
          .idleMode(IdleMode.kBrake);
    
    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    leftFollowerConfig
        .apply(globalLeaderConfig)      
        .inverted(true)
        .follow(leftFrontMotor);
          
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    rightLeaderConfig
        .apply(globalLeaderConfig)
        .inverted(true);
    
    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();
    rightFollowerConfig
        .apply(globalLeaderConfig)
        .inverted(true)
        .follow(rightFrontMotor);

    leftFrontMotor.configure(globalLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftRearMotor.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFrontMotor.configure(rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightRearMotor.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    differentialDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
  }

  /**
   * Arcade drive method to be called in commands folder
   */
    public void arcadeDrive(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }
}
