package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Driving;
import frc.robot.commands.ArcadeDrive;

public class Limelight extends SubsystemBase {
    final Driving drive = new Driving(); 
    Pose2d limelightPose = new Pose2d();
    public String name = "limelight";



    @Override
    public void periodic() {
        limelightPose=LimelightHelpers.getBotPose2d_wpiBlue(name);
        double timestamp=LimelightHelpers.getBotPoseEstimate_wpiBlue(name).timestampSeconds;
        if (limelightPose!=null && 
        limelightPose.getX()>0 && 
        limelightPose.getY()>0 &&
        limelightPose.getX()<10 &&
        limelightPose.getY()<5){
            drive.updateLimelight(limelightPose, timestamp);
        }

    }


}
