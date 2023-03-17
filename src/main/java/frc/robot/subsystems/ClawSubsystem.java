package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
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
     * Records a time stamp of when the user starts to hold the 
     * intake button down.
     */
    private double intakeStartTimeSeconds;

    /**
     * True if the intake button is being held down.  False otherwise.
     */
    boolean intaking = false;

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

        CANSparkMax leftRoller =  new CANSparkMax(Constants.RIGHT_WRIST_ROLLER_CAN_ID, MotorType.kBrushed);
        this.goliath1 = leftRoller;
        CANSparkMax rightRoller = new CANSparkMax(Constants.LEFT_WRIST_ROLLER_CAN_ID, MotorType.kBrushed);
        this.goliath2 = rightRoller;

        // Prevents the intake from just dropping whatever it was holding if there 
        // is no input from the controllers. 
        leftRoller.setIdleMode(IdleMode.kBrake);
        rightRoller.setIdleMode(IdleMode.kBrake);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj2.command.Subsystem#periodic()
     */
    @Override
    public void periodic() {
        super.periodic();
        double rollerSpeed = 0;

        // s = seconds held down
        // b = burst time
        // Scenario IZZY: Rollers roll for b + s seconds
        // Scenario MATTHEW: Roll for MAX(s, b) seconds
        // We agreed on Scenario MATTHEW.

        if (input.getGoliathForwardButton()) {
            // User is intaking a roller.
            if (!intaking) {
                // Prevent us from resetting the timer simply because the button
                // is being held down.
                intaking = true;
                intakeStartTimeSeconds = Timer.getFPGATimestamp();

                // Start rollin'!
                rollerSpeed = Constants.CLAW_MOTOR_SLOW_DOWN_FACTOR;
            }
        } else {
            intaking = false;
            if (Timer.getFPGATimestamp() < intakeStartTimeSeconds + Constants.MINIMUM_ROTATION_TIME_SECONDS) {
                // The user started intaking and then released recently (i.e.,
                // within the minimum roll time.)  That means we need to keep rolling!
                rollerSpeed = Constants.CLAW_MOTOR_SLOW_DOWN_FACTOR;
            } else {
                // The user released the intake a long time ago, or they never
                // did the intake in the first place.
                rollerSpeed = 0;
            }
        }
        
        if (input.getGoliathReverseButton()) {
            // User is outtaking a roller.  There's no danger of killing a game
            // element, so let us indulge in the user's whims.
            rollerSpeed = -Constants.CLAW_MOTOR_SLOW_DOWN_FACTOR;
        }
        
        // Commented out for now because this condition, as-is, would kill
        // burst mode (the rollers are supposed to roll on their own for the 
        // minimum time even if the human releases the controller.)
        //
        // We need a better condition for determining when "the human is not
        // providing input."
        //
        // if (!input.getGoliathReverseButton() && 
        //     !input.getGoliathForwardButton()) {
        //     // No human input.  We should use the speed from the ShuffleBoard
        //     // or from autonomous (if any.)
        //     goliath1.set(automaticRollerSpeed);
        //     goliath2.set(-automaticRollerSpeed);
        // }

        if (rollerSpeed == 0) {
            goliath1.stopMotor();
            goliath2.stopMotor();
        } else {
            goliath1.set(rollerSpeed);
            goliath2.set(-rollerSpeed);
        }
    }
}
