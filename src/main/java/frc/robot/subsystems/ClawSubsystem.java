package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ShuffleboardDebug;

/**
 * This subsystem is responsible for operating the claw at the end of the arm.
 * It is driven by two rollers that rotate in opposite directions, allowing it
 * to either take up or release the inflated and conical game elements.
 */
public class ClawSubsystem extends SubsystemBase {
    // The motors for the goliath intake, there will be a motor on each "finger"
    // of the claw
    private MotorController goliath1;
    private MotorController goliath2;

    private ShuffleboardDebug debug;
    private InputSubsystem input;

    /**
     * Default speed for when there is no input to the claw. This speed can be set
     * either during autonomus or on the shuffleboard.
     */
    private double automaticRollerSpeed = 0;

    /**
     * Sets the speed that the claw rollers should be driven at in the absence
     * of direct human input.  This is the case, for instance, during
     * autonomous.
     *
     * TODO: Write code to read this value from the shuffleboard.
     * 
     * @param rollerSpeed The new speed of the goliath rollers.  Positive values
     *                    do intake, negative values do outtake, and 0 should
     *                    hold steady.
     */
    public void setSpeed(double rollerSpeed) {
        this.automaticRollerSpeed = rollerSpeed;
    }

    /**
     * Initializes this subsystem.
     * 
     * @param input An {@link InputSubsystem} to use for gathering human inputs.
     * @param debug A bag of ShuffleBoard variables used for debugging.
     */
    public ClawSubsystem(InputSubsystem input, ShuffleboardDebug debug) {
        this.debug = debug;
        this.input = input;
        this.goliath1 = new CANSparkMax(Constants.RIGHT_WRIST_ROLLER_CAN_ID, MotorType.kBrushed);
        this.goliath2 = new CANSparkMax(Constants.LEFT_WRIST_ROLLER_CAN_ID, MotorType.kBrushed);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj2.command.Subsystem#periodic()
     */
    @Override
    public void periodic() {
        super.periodic();

        if (Math.abs(input.getGoliathSpeed()) < Constants.DEADZONE) {
            // No significant human input detected.
            goliath1.set(automaticRollerSpeed);
            goliath2.set(-automaticRollerSpeed);
        } else {
            // Human wants claw control.
            goliath1.set(input.getGoliathSpeed());
            goliath2.set(-input.getGoliathSpeed());
        }
    }
}
