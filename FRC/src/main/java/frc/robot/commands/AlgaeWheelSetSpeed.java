package frc.robot.commands;

import frc.robot.subsystems.AlgaeWheel;
import edu.wpi.first.wpilibj2.command.Command;

// Sets the speed of the Algae Wheel, requires AlgaeWheel.java subsystem
public class AlgaeWheelSetSpeed extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"}) // This is in the example, don't rly know what it does
    private final AlgaeWheel algaeWheel;
    private final double speed;
    
    /**
     * Creates a new command named AlgaeWheelSetSpeed.
     * 
     * @param algaeWheel The subsystem used by this command
     * @param speed The speed that the motor runs at, range -1 to 1
     */
    public AlgaeWheelSetSpeed(AlgaeWheel algaeWheel, double speed) {
        this.algaeWheel = algaeWheel;
        this.speed = speed;
        addRequirements(algaeWheel);
    }
    
    
     // @Override
    // public void initialize() 
    // {

    // }

     @Override
     public void execute()
     {
        this.algaeWheel.setSpeed(speed);
     }

    @Override
    public void end(boolean interrupted)
    {
        this.algaeWheel.setSpeed(0);
    }
}
