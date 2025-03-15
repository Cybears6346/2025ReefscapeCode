package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants.OperatorConstants;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Elevator extends SubsystemBase {
    public final SparkMax elevatorMotor1;
    public final SparkMax elevatorMotor2;
    DigitalInput elevatorInput = new DigitalInput(9);
    DutyCycleEncoder elevatorAbsoluteEncoder = new DutyCycleEncoder(elevatorInput);
    double encoderValue; 

    public Elevator()
    {
        elevatorMotor1 = new SparkMax(OperatorConstants.elevatorMotor1ID, MotorType.kBrushless);
        elevatorMotor2 = new SparkMax(OperatorConstants.elevatorMotor2ID, MotorType.kBrushless);
        SparkMaxConfig globalElevatorConfig = new SparkMaxConfig();
        SparkMaxConfig elevatorInvertedConfig = new SparkMaxConfig();

        globalElevatorConfig
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake); 
        elevatorInvertedConfig
            .smartCurrentLimit(0)
            .idleMode(IdleMode.kBrake)
            .inverted(true);

        elevatorMotor1.configure(globalElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorMotor2.configure(elevatorInvertedConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        
    }
    
    public void setSpeed(DoubleSupplier speed) {
        elevatorMotor1.set(-speed.getAsDouble());
        elevatorMotor2.set(-speed.getAsDouble());
    }
    
    public void setSpeed(double speed)
    {
        //Inverse speed to adjust for joystick input.
        elevatorMotor1.set(-speed);
        elevatorMotor2.set(-speed);
    }

    public double getElevatorEncoderValue(){
        return elevatorAbsoluteEncoder.get();
    }
    public void periodic() {
        // TODO: Add elevator encoder here as in the group chat
        encoderValue = getElevatorEncoderValue();
    }
}