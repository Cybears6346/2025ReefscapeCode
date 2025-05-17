// package frc.robot.commands;

// import frc.robot.subsystems.Driving;
// import frc.robot.subsystems.Limelight;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj2.command.Command;

// public class AlignToAprilTag extends Command{
//     @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//     private final Driving driving;
//     private final Limelight limelight;
//     private final PIDController pidController;
//     private boolean isFinished = false;
    
//     public AlignToAprilTag(Driving driving, Limelight limelight){
//         this.driving = driving;
//         this.limelight = limelight;
//         this.pidController = new PIDController(0.02, 0.001, 0.002);
//         addRequirements(driving);

//     }
//     @Override
//     public void execute() {
//         double tx = limelight.getTx(); // Get horizontal offset
//         double turnSpeed = pidController.calculate(tx, 0); // Turn until tx = 0
//         driving.arcadeDrive(0, turnSpeed);
//     }
//     @Override
//     public void end(boolean interrupted){
        
//     }
//     @Override
//     public boolean isFinished(){
//         return pidController.atSetpoint();
//     }
// }
