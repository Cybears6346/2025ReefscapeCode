// package frc.robot.subsystems;

// import edu.wpi.first.networktables.NetworkTable;
// import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.networktables.NetworkTableInstance;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// public class Limelight extends SubsystemBase {
//     private final NetworkTable limelightTable;

//     public Limelight() {
//         limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
//     }

//     // Get horizontal offset (tx) in degrees
//     public double getTx() {
//         return limelightTable.getEntry("tx").getDouble(0.0);
//     }

//     // Get vertical offset (ty) in degrees
//     public double getTy() {
//         return limelightTable.getEntry("ty").getDouble(0.0);
//     }

//     // Get target area (ta) as a percentage of image
//     public double getTa() {
//         return limelightTable.getEntry("ta").getDouble(0.0);
//     }

//     // Get whether the Limelight has a valid target (tv)
//     public boolean hasTarget() {
//         return limelightTable.getEntry("tv").getDouble(0.0) == 1.0;
//     }

//     // Set Limelight LED mode (0 = pipeline, 1 = force off, 2 = force blink, 3 = force on)
//     public void setLedMode(int mode) {
//         limelightTable.getEntry("ledMode").setNumber(mode);
//     }

//     // Set Limelight camera mode (0 = vision processing, 1 = driver camera)
//     public void setCameraMode(int mode) {
//         limelightTable.getEntry("camMode").setNumber(mode);
//     }

//     // Set Limelight pipeline (0-9)
//     public void setPipeline(int pipeline) {
//         limelightTable.getEntry("pipeline").setNumber(pipeline);
//     }

//     @Override
//     public void periodic() {
//         // Display Limelight values on the SmartDashboard for debugging
//         SmartDashboard.putNumber("Limelight TX", getTx());
//         SmartDashboard.putNumber("Limelight TY", getTy());
//         SmartDashboard.putNumber("Limelight TA", getTa());
//         SmartDashboard.putBoolean("Limelight Has Target", hasTarget());
//     }
// }
