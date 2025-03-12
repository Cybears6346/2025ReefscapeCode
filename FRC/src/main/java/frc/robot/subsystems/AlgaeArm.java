// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;;

public class AlgaeArm extends SubsystemBase {
  private final SparkFlex algaeArmMotor;


  /** Creates a new AlgaeArm object configured as necessary. */
  public AlgaeArm() {
    algaeArmMotor = new SparkFlex(OperatorConstants.AlgaeArmFlexID, MotorType.kBrushless);
    SparkFlexConfig algaeArmLeaderConfig = new SparkFlexConfig();
    algaeArmLeaderConfig
          .smartCurrentLimit(50)
          .idleMode(IdleMode.kBrake)
          .inverted(true);

    algaeArmMotor.configure(algaeArmLeaderConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

  }

  /**
   * Set speed method to be called in commands folder
   */
  public void setSpeed(DoubleSupplier speed) {
    algaeArmMotor.set(-speed.getAsDouble()*0.5);
  }
  
   public void setSpeed(double speed) {
    algaeArmMotor.set(-speed*0.5);
  }

}
