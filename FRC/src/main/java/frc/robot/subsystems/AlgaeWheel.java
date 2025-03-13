package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeWheel extends SubsystemBase {
    public final SparkMax algaeWheelMotor;

    public AlgaeWheel()
    {
        algaeWheelMotor = new SparkMax(OperatorConstants.AlgaeWheelMaxID, MotorType.kBrushless);
        SparkMaxConfig globalAlgaeWheelLeaderConfig = new SparkMaxConfig(); 

        globalAlgaeWheelLeaderConfig
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake)
            .inverted(true);

        algaeWheelMotor.configure(globalAlgaeWheelLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /**
   * Set speed method to be called in commands folder
   */
    public void setSpeed(double speed)
    {
        algaeWheelMotor.set(-speed);
    }

    public void periodic() {
        
    }
}
