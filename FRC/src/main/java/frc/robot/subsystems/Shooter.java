package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    public final SparkMax shooterMotor1;
    public final SparkMax shooterMotor2;

    public Shooter()
    {
        shooterMotor1 = new SparkMax(OperatorConstants.shooterMotor1ID, MotorType.kBrushless);
        shooterMotor2 = new SparkMax(OperatorConstants.shooterMotor2ID, MotorType.kBrushless);
        SparkMaxConfig globalShooterConfig = new SparkMaxConfig();
        SparkMaxConfig shooterInvertedConfig = new SparkMaxConfig();

        globalShooterConfig
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake);
        shooterInvertedConfig
            .apply(globalShooterConfig)
            .inverted(true)
            .follow(shooterMotor1);

        shooterMotor1.configure(globalShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        shooterMotor2.configure(shooterInvertedConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }
    
    public void setSpeed(DoubleSupplier speed) {
        shooterMotor1.set(speed.getAsDouble());
        shooterMotor2.set(speed.getAsDouble());
    }
    
    public void setSpeed(double speed)
    {
    
        shooterMotor1.set(speed);
        shooterMotor2.set(speed);
    }

    public void periodic() {
        
    }
}