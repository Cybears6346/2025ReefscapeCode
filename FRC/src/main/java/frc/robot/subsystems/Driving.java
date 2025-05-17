// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.security.Timestamp;
import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SignalsConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.config.BaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPLTVController;

import com.revrobotics.spark.SparkRelativeEncoder;


public class Driving extends SubsystemBase {

  // // Method to drive the robot given robot-relative ChassisSpeeds
  // public void driveRobotRelative(ChassisSpeeds speeds) {
  //   // Implement the logic to drive the robot using the given speeds
  // }

  // // Method to get the robot's relative speeds
  // public ChassisSpeeds getRobotRelativeSpeeds() {
  //   // Return the current robot-relative speeds
  //   return new ChassisSpeeds();
  // }

  // // Method to get the robot's pose
  // public Pose2d getPose() {
  //   // Return the current pose of the robot
  //   return new Pose2d();
  // }

  // // Method to reset the robot's pose
  // public void resetPose(Pose2d pose) {
  //   // Reset the robot's pose to the given pose
  // }

  private Pigeon2 pigeon = new Pigeon2(0,"rio");

  private DifferentialDrivePoseEstimator m_poseEstimator = new DifferentialDrivePoseEstimator(new DifferentialDriveKinematics(Units.inchesToMeters(26)), new Rotation2d(), 0, 0, new Pose2d());

  private final SparkMax leftFrontMotor = new SparkMax(OperatorConstants.driveMotor4ID, MotorType.kBrushless);
  private final SparkMax leftRearMotor = new SparkMax(OperatorConstants.driveMotor3ID, MotorType.kBrushless);
  private final SparkMax rightFrontMotor = new SparkMax(OperatorConstants.driveMotor2ID, MotorType.kBrushless);
  private final SparkMax rightRearMotor = new SparkMax(OperatorConstants.driveMotor1ID, MotorType.kBrushless);

  RelativeEncoder driveEncoder = leftFrontMotor.getEncoder();
  RelativeEncoder RightEncoder = rightFrontMotor.getEncoder();
  

  private final SysIdRoutine sysIdRoutine = new SysIdRoutine(new SysIdRoutine.Config(), new SysIdRoutine.Mechanism(voltage -> {
    leftFrontMotor.setVoltage(voltage);
    leftRearMotor.setVoltage(voltage);
    rightFrontMotor.setVoltage(voltage);
    rightRearMotor.setVoltage(voltage);

  }, null,this));  
    
  private final DifferentialDrive differentialDrive;
    /** Creates a new Driving subsystem. */
  public Driving() {
    SignalsConfig config1 = new SignalsConfig();
    RobotConfig config = null;
    try{
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }
// AutoBuilder.configure(
//             this::getPose, // Robot pose supplier
//             this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
//             this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
//             (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
//             new PPLTVController(0.02), // PPLTVController is the built in path following controller for differential drive trains
//             config, // The robot configuration
//             () -> {
//               // Boolean supplier that controls when the path will be mirrored for the red alliance
//               // This will flip the path being followed to the red side of the field.
//               // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

//               var alliance = DriverStation.getAlliance();
//               if (alliance.isPresent()) {
//                 return alliance.get() == DriverStation.Alliance.Red;
//               }
//               return false;
//             },
//             this // Reference to this subsystem to set requirements
//     );

    
    driveEncoder.setPosition(0);
    config1
      .analogPositionAlwaysOn(true)
      .analogVelocityAlwaysOn(true)
      .analogVoltageAlwaysOn(true)
      .absoluteEncoderPositionAlwaysOn(true)
      .absoluteEncoderVelocityAlwaysOn(true)
      .primaryEncoderPositionAlwaysOn(true)
      .primaryEncoderVelocityAlwaysOn(true)
      .externalOrAltEncoderPositionAlwaysOn(true)
      .externalOrAltEncoderVelocityAlwaysOn(true);
    

    SparkMaxConfig globalLeaderConfig = new SparkMaxConfig();
    globalLeaderConfig
          .smartCurrentLimit(50)
          .idleMode(IdleMode.kBrake)
          .apply(config1);
    
    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    leftFollowerConfig
        .apply(globalLeaderConfig)      
        .inverted(true)
        .follow(leftFrontMotor)
        .apply(config1);
          
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    rightLeaderConfig
        .apply(globalLeaderConfig)
        .inverted(true)
        .apply(config1);
    
    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();
    rightFollowerConfig
        .apply(globalLeaderConfig)
        .inverted(true)
        .follow(rightFrontMotor)
        .apply(config1);

    leftFrontMotor.configure(globalLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftRearMotor.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFrontMotor.configure(rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightRearMotor.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    
    
    differentialDrive = new DifferentialDrive(leftFrontMotor, rightFrontMotor);
    
    

    }

  /**
   * Arcade drive method to be called in commands folder
   */
    public void arcadeDrive(DoubleSupplier speed, DoubleSupplier rotation) {
        differentialDrive.arcadeDrive(-speed.getAsDouble(), -rotation.getAsDouble());
    }

    public void arcadeDrive(double speed, double rotation) {
      differentialDrive.arcadeDrive(speed, rotation);
  }


    @Override
    public void periodic() {
        differentialDrive.feed();
        // This method will be called once per scheduler run
        m_poseEstimator.update(pigeon.getRotation2d(), driveEncoder.getPosition(), rightFrontMotor.getEncoder().getPosition());
    }  

    public void zeroDriveEncoder(){
      driveEncoder.setPosition(0);
    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
      return sysIdRoutine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
      return sysIdRoutine.dynamic(direction);
    }
    public double getDrivingEncoderValue(){
      return driveEncoder.getPosition();
  }

    public void updateLimelight(Pose2d pose, double timestamp){
      m_poseEstimator.addVisionMeasurement(pose, timestamp);
    }

    public void alignToTag(){
      PIDController rotationPID = new PIDController(0.09, 0, 0);
      double output = rotationPID.calculate(m_poseEstimator.getEstimatedPosition().getRotation().getRadians(), 0);
      differentialDrive.arcadeDrive(0, -output);

    }

}
