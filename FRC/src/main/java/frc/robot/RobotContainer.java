// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//https://www.youtube.com/watch?v=gRbBkdinq0o Tutorial for pathplanner

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

import java.io.IOException;
import java.nio.file.Path;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final AlgaeArm algaeArm = new AlgaeArm();
  private final AlgaeWheel algaeWheel = new AlgaeWheel();
  private final Shooter shooter = new Shooter();
  private final Elevator elevator = new Elevator();
  private final Driving arcadeDrive = new Driving();

  SendableChooser<Command> chooser = new SendableChooser<>();
  

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    chooser.addOption("L4 pt.1.path",loadPathplanner("E:\\Github Repos\\Hartford-Comp-Code-2025-1\\FRC\\src\\main\\deploy\\pathplanner\\paths\\L4 pt.1.path", true)); // change file path to match current pc it's running on
    chooser.addOption("L4 pt.2.path",loadPathplanner("E:\\Github Repos\\Hartford-Comp-Code-2025-1\\FRC\\src\\main\\deploy\\pathplanner\\paths\\L4 pt.2.path", true));// change file path to match current pc it's running on
    chooser.addOption("L4 pt.3.path",loadPathplanner("E:\\Github Repos\\Hartford-Comp-Code-2025-1\\FRC\\src\\main\\deploy\\pathplanner\\paths\\L4 pt.3.path", true));// change file path to match current pc it's running on
   
    Shuffleboard.getTab("Autonomous").add(chooser);

  }
  
  public Command loadPathplanner(String filename, boolean resetOdomtry){
    Path path = Filesystem.getDeployDirectory().toPath().resolve(filename);
    // try {
    //   Path path = Filesystem.getDeployDirectory().toPath().resolve(filename);

    // }catch(IOException exception){
    //   DriverStation.reportError("Unable to open path+" + filename, exception.getStackTrace());
    //   System.out.println("Unable to read from file" + filename);
    //   return new InstantCommand();
    // }
    return new InstantCommand();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    /**
    * This is a lambda. Yellow () means it takes no parameters, function runs when 
    * operator controller >0.05 in either direction. While this is true, algaearm subsystem
    * is run at a speed of -operatorcontroller.getLeftY()
    */
    new Trigger(() -> Math.abs(m_operatorController.getLeftY())>0.05)
        .whileTrue(new AlgaeArmSetSpeed(algaeArm, 
        () -> -m_operatorController.getLeftY()));

    /**-
     * This should hopefully drive the robot 
     */
    double speedPercentage = 0.8;
    double rotationPercentage = 1;
      //   new Trigger(() -> Math.abs(m_driverController.getLeftY()) > 0.05 || Math.abs(m_driverController.getRightX()) > 0.05)
      // .whileTrue(new ArcadeDrive(arcadeDrive, m_driverController.getLeftY()*speedPercentage, m_driverController.getRightX()*rotationPercentage));
      new Trigger(() -> Math.abs(m_driverController.getLeftY()) > 0.05 || Math.abs(m_driverController.getRightX()) > 0.05)
        .whileTrue(new ArcadeDrive(arcadeDrive, 
                       () -> m_driverController.getLeftY() * speedPercentage, 
                       () -> m_driverController.getRightX() * rotationPercentage));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    m_operatorController.leftBumper().whileTrue(new AlgaeWheelSetSpeed(algaeWheel, 1));
    m_operatorController.rightBumper().whileTrue(new AlgaeWheelSetSpeed(algaeWheel, -1));
    

    new Trigger(() -> Math.abs(m_operatorController.getRightY()) > 0.05)
    .whileTrue(new ElevatorSetSpeed(elevator, 
    () -> m_operatorController.getRightY()*speedPercentage));

    new Trigger(() -> m_operatorController.getLeftTriggerAxis() > 0.25 && m_operatorController.getRightTriggerAxis() <= 0.25)
      .whileTrue(new ShooterSetSpeed(shooter, 
      () -> m_operatorController.getLeftTriggerAxis()));

    new Trigger(() -> m_operatorController.getRightTriggerAxis() > 0.25 && m_operatorController.getLeftTriggerAxis() <= 0.25)
       .whileTrue(new ShooterSetSpeed(shooter, 
       () -> -m_operatorController.getRightTriggerAxis()));

//Elevator Macro Bindings for Auto/Teleop
    m_operatorController.y().onTrue(new L4ElevatorUp(elevator));
    m_operatorController.x().onTrue(new L3ElevatorUp(elevator));

//Use this as a elevator DOWN test
    m_operatorController.a().onTrue(new L4ElevatorDown(elevator));

    /*
     * Sys ID routines, to be uploaded to URCL by littleton robotics
     * Driver controller must hold those button combinations to administer the routine
     * Please do this in an open area
     * There are four tests total, read the URCL docs for info on how to access the data. 
     * Keep these tests here as they will differ from robot to robot year to year
     */
    m_driverController
      .a()
      .and(m_driverController.rightBumper())
      .whileTrue(arcadeDrive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));

    m_driverController
      .b()
      .and(m_driverController.rightBumper())
      .whileTrue(arcadeDrive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));

    m_driverController
      .x()
      .and(m_driverController.rightBumper())
      .whileTrue(arcadeDrive.sysIdDynamic(SysIdRoutine.Direction.kForward));

    m_driverController
      .y()
      .and(m_driverController.rightBumper())
      .whileTrue(arcadeDrive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * TODO: ADD THIS FOR AUTONOMOUS
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
   // return Autos.exampleAuto(arcadeDrive,algaeArm);
    return chooser.getSelected();
  }
}
