// FIXME: Reference this for subsystem functions when constructing new ones. Else keep commented.
// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.spark.SparkLowLevel;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkFlex;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkRelativeEncoder;
// import com.revrobotics.spark.SparkBase.PersistMode;
// import com.revrobotics.spark.SparkBase.ResetMode;

// import edu.wpi.first.wpilibj.Timer;
// import com.revrobotics.spark.config.SparkFlexConfig;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class ExampleSubsystem extends SubsystemBase {
//   private final SparkFlex algaeArmMotor;


//   /** Creates a new ExampleSubsystem. */
//   public ExampleSubsystem() {
//     algaeArmMotor = new SparkFlex(9, MotorType.kBrushless);
//     SparkFlexConfig algaeArmLeaderConfig = new SparkFlexConfig();
//     algaeArmLeaderConfig
//           .smartCurrentLimit(50)
//           .idleMode(IdleMode.kBrake)
//           .inverted(true);

//     algaeArmMotor.configure(algaeArmLeaderConfig,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

//   }

//   /**
//    * Example command factory method.
//    *
//    * @return a command
//    */
//   public Command exampleMethodCommand() {
//     // Inline construction of command goes here.
//     // Subsystem::RunOnce implicitly requires `this` subsystem.
//     return runOnce(
//         () -> {
//           /* one-time action goes here */
//         });
//   }

//   /**
//    * An example method querying a boolean state of the subsystem (for example, a digital sensor).
//    *
//    * @return value of some boolean subsystem state, such as a digital sensor.
//    */
//   public boolean exampleCondition() {
//     // Query some boolean state, such as a digital sensor.
//     return false;
//   }

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }

//   @Override
//   public void simulationPeriodic() {
//     // This method will be called once per scheduler run during simulation
//   }
// }
