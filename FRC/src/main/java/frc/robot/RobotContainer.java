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
import java.nio.file.Files;
import java.nio.file.Path;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

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

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Timer;;






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
  Timer timer = new Timer();
  private final SequentialCommandGroup commandGroup = new SequentialCommandGroup();

  private final Command doNothingAuto = new DoNothingAuto(arcadeDrive); 
  private final Command straightL4Auto = new StraightL4Auto(arcadeDrive, shooter, elevator, commandGroup);  
  private final Command straigthL4AutoWithUturn = new StraightL4AutoWithUturn(arcadeDrive, shooter, elevator, commandGroup, timer);
  //private final Command teamColorSideAuto = new TeamColorSideAuto(arcadeDrive, shooter, elevator, commandGroup);  
  private final Command enemyColorSideAuto = new EnemyColorSideAuto(arcadeDrive, shooter, elevator, commandGroup, timer);
  private final Command StraightAutoCommand = new StraightAutoCommand(arcadeDrive);
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

    chooser.setDefaultOption("Do Nothing Auton", doNothingAuto); //use this for reference https://docs.wpilib.org/en/stable/docs/software/dashboards/smartdashboard/choosing-an-autonomous-program-from-smartdashboard.html
    chooser.addOption("Straight/Center Auton", straightL4Auto);
    chooser.addOption("Straight/Center Auton with Uturn", straigthL4AutoWithUturn);
    //chooser.addOption("Team Color Side Auton", teamColorSideAuto);  
    chooser.addOption("Processor Auton", enemyColorSideAuto);  
    chooser.addOption("Leave 1 foot", StraightAutoCommand);
    
    SmartDashboard.putData(chooser);
  }
  
//   public Command loadPathplanner(String filename, boolean resetOdomtry) {
//     Path path = Filesystem.getDeployDirectory().toPath().resolve(filename);
//     System.out.println("Path to file: " + path.toString());

//     try {
//         if (!Files.exists(path)) {
//             DriverStation.reportError("Path file not found: " + filename, false);
//             return new InstantCommand();
//         }

//         // Example PathPlanner loading logic (update as per your project)
//         PathPlannerTrajectory trajectory = PathPlanner.loadPath(filename, new PathConstraints(2.0, 2.0)); // Customize your constraints here
//         return new FollowPathPlannerTrajectoryCommand(trajectory);
//     } catch (IOException e) {
//         DriverStation.reportError("Unable to open path: " + filename, e.getStackTrace());
//         return new InstantCommand();
//     }
// }

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

//Elevator Macro Bindings for Auto/Teleop, 
// DO NOT CHANGE ANY OF THESE MACROS, THESE WILL BE USED DURING COMP
    m_operatorController.y().onTrue(new L4ElevatorShoot(elevator, shooter));
    m_operatorController.x().onTrue(new L3ElevatorShoot(elevator, shooter));
    m_operatorController.b().onTrue(new L2ElevatorShoot(elevator, shooter));
    m_operatorController.a().onTrue(new L4ElevatorDown(elevator));
//Use this as a general test, comment out during comp
    m_driverController.a().onTrue(new CenterAutonUTurnpt1(arcadeDrive));
    m_driverController.x().onTrue(new StraightL4Auto(arcadeDrive, shooter, elevator, commandGroup)); 
    m_driverController.b().onTrue(new CenterAutonUTurnShortPath(arcadeDrive));
    m_driverController.y().onTrue(new StraightL4AutoWithUturn(arcadeDrive, shooter, elevator, commandGroup, timer));

    /*
     * Sys ID routines, to be uploaded to URCL by littleton roboics
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
  public Command getAutonomousCommand(){
    return chooser.getSelected();
  }
}
