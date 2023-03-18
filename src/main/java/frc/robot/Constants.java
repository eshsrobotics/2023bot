// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    /**
     * Width of the chassis drivebase (from the center of a wheel on the left to the center of 
     * a wheel on the right.)
     */
    public final static double CHASSIS_WIDTH_INCHES = 24.0; 

    /**
     * Length of the chassis drivebase (from the center of a wheel on the front to 
     * the center of a wheel on the back)
     */
    public final static double CHASSIS_LENGTH_INCHES = 20.0;

    /**
     * Drive CAN port assignments
     */
    public final static int BACK_LEFT_CAN_ID = 1;
    public final static int FRONT_LEFT_CAN_ID = 6;
    public final static int FRONT_RIGHT_CAN_ID = 7;
    public final static int BACK_RIGHT_CAN_ID = 5;

    /**
     * Proportional constant for forward axis PID controller.
     */
    public final static double P_FRONT_BACK = 1.0;

    /**
     * Integral constant for forward axis PID controller. The value is at 0 to avoid integral windup.
     */
    public final static double I_FRONT_BACK = 0;

    /**
     * Derivative constant for forward axis PID controller.
     */
    public final static double D_FRONT_BACK = 0.1;

    /**
     * Proportional constant for horizontal axis PID controller.
     */
    public final static double P_LEFT_RIGHT = 1.0;

     /**
     * Integral constant for horizontal axis PID controller.
     */
    public final static double I_LEFT_RIGHT = 0;

     /**
     * Derivative constant for horizontal axis PID controller.
     */
    public final static double D_LEFT_RIGHT = 0.1;

     /**
     * Proportional constant for the profiled rotation PID controller.
     */ 
    public final static double P_PROFILED_ROTATION = 1.0;

     /**
     * Integral constant for the profiled rotation PID controller.
     */ 
    public final static double I_PROFILED_ROTATION = 0;

    /**
     * Derivative constant for the profiled rotation PID controller.
     */ 
    public final static double D_PROFILED_ROTATION = 0.1;

    /**
     * Puts an acceleration constraint on the profiled rotation PID controller.
     * TODO actually measure the maximum acceleration of the robot
     */
    public final static double MAXIMUM_ACCELERATION_INCHES_PER_SECOND_SQUARED = 10.0;

    /**
     * Puts a velocity constraint on the profiled rotation PID controller.
     * TODO actually measure the maximum speed of the robot 
     */
    public final static double MAXIMUM_VELOCITY_INCHES_PER_SECOND = 48.0;

    /**
     * Maximum velocity our robot can travel in meters.
     * TODO actually measure the maximum speed of the robot 
     */
    public final static double MAXIMUM_VELOCITY_METERS_PER_SECOND = MAXIMUM_VELOCITY_INCHES_PER_SECOND/254;

    /**
     * If an Xbox controller is plugged in, we assume it is in this port
     * 
     * <p>WARNING: If using an Xbox controller and a joystick at the same time, 
     * plug the Xbox controller in first</p>
     */
    public final static int XBOX_PORT = 0;

    /**
     * If a joystick is plugged in, we assume it is in this port
     * 
     * <p>WARNING: If using an Xbox controller and a joystick at the same time, 
     * plug the joystick in second</p>
     */

    public final static int JOYSTICK_PORT = 1;

    /**
     * Represents the trajectory file for autonomous.
     */
    public final static String AUTONOMOUS_JSON_PATH = "/home/lvuser/pathweaver.json";

    /**
     * The distance (in inches) from the shoulder joint of the arm to the elbow
     * joint
     */
    public final static double SHOULDER_TO_ELBOW_INCHES = 19.398;

    /**
     * The distance (in inches) from the elbow joint of the arm to the wrist
     */
    public final static double ELBOW_TO_WRIST_INCHES = 21;

    /**
     * Total arm length
     */
    public final static double ARM_LENGTH = SHOULDER_TO_ELBOW_INCHES + ELBOW_TO_WRIST_INCHES;
    
    /**
     * Used to convert radian values of arm angles to degrees
     */
    public final static double RADIANS_TO_DEGREES = 180 / Math.PI;    

    // TODO: Find actual kP, kI, and kD values for the shoulder, elbow, and
    // wrist
    /**
     * kP, kI, and kD are used for PID for the arm
     */
    public final static double SHOULDER_kP = 10.0;
    public final static double SHOULDER_kI = 10.0;
    public final static double SHOULDER_kD = 10.0;

    public final static double ELBOW_kP = 10.0;
    public final static double ELBOW_kI = 10.0;
    public final static double ELBOW_kD = 10.0;

    public final static double WRIST_kP = 10.0;
    public final static double WRIST_kI = 10.0;
    public final static double WRIST_kD = 10.0;

    /**
     * The conversion factor from CANSparkMax to angles
     */
    public final static double CAN_TO_ANGLE = 5.0;

    // TODO: Find actual CAN IDs for the arm motors
    /**
     * The CAN IDs for the arm and wrist motors.
     */
    public final static int SHOULDER_MOTOR_CAN_ID = 18;
    public final static int ELBOW_MOTOR_CAN_ID = 0;
    public final static int WRIST_MOTOR_CAN_ID = 0;
    public final static int RIGHT_WRIST_ROLLER_CAN_ID = 0;
    public final static int LEFT_WRIST_ROLLER_CAN_ID = 0;

    /**
     * The expected starting angles for the arm motors.
     *
     * <ul>
     *   <li>The starting angle for the **shoulder** is raised from horizontal,
     *       but less than a right angle.</li>
     *   <li>The starting angle for the **elbow** is much further around than
     *       straight out (180 degrees) -- so much further that the arm bends
     *       backward over itself.</li>
     *   <li>The starting angle for the wrist is pointed not down (90 degrees),
     *       not straight (180 degrees), but the opposite of down (270
     *       degrees.)</li>
     * </ul>
     */
    public final static double SHOULDER_START_ANGLE = 55;
    public final static double ELBOW_START_ANGLE = 180 + 125;
    public final static double WRIST_START_ANGLE = -90;

    public final static double SHOULDER_MAX_ANGLE = 180;
    public final static double ELBOW_MAX_ANGLE = 0;
    public final static double WRIST_MAX_ANGLE = 160;

    public final static double SHOULDER_MIN_ANGLE = 26;
    public final static double ELBOW_MIN_ANGLE = -130;
    public final static double WRIST_MIN_ANGLE = -160;
    /**
     * The gear ratios for the shoulder, elbow, and wrist motors
     * 
     * <p>All gear ratios here represent the ratio of the teeth
     * for the input shaft to the teeth for the output shaft.</p>
     */
    public final static double SHOULDER_GEAR_RATIO = 26 / 60;
    public final static double ELBOW_GEAR_RATIO = 32 / 56;
    public final static double WRIST_GEAR_RATIO = 1;

    // TODO: Find actual conversion factors
    /**
     * The conversion factors for the unexpected changes in input to output
     * rotations of the arm motors
     */
    public final static double SHOULDER_CONVERSION = 1;
    public final static double ELBOW_CONVERSION = 1;
    public final static double WRIST_CONVERSION = 1;

    /**
     * The basic deadzone value used for most joysticks and triggers.
     */
    public final static double DEADZONE = 0.1;

    /**
     * The value to multiply the arm values from the inputSubsystem by, because
     * the inputSubsytem will return -1, 0, or 1, which is most likely not the
     * best speed for the arm to move at
     */
    public final static double ARM_SPEED_TO_INCHES = 1;

    // TODO: Find a value that works well for the arm
    /**
     * The tolerance for the arm's PIDControllers.  When we ask the 
     * PIDControllers for the arm's three joints if they've reached their
     * setpoint (in degrees), this is how we get them to tolerate slop in the
     * final angle.
     */
    public final static double PID_TOLERANCE_DEGREES = 1;

    /**
     * Precautionairy measure if to slow down the speed of the claw motor if it
     * is too high.
     */
    public final static double CLAW_MOTOR_SLOW_DOWN_FACTOR = 1.0;

    /**
     * The minimum amount of time to move the rollers on the claw
     */
    public final static double MINIMUM_ROTATION_TIME_SECONDS = 1.0;

    /**
     * The maximum amount of time to move the rollers on the claw
     *
     * Having the rollers on for _too_ long risks damaging cone game elements,
     * since we have no sensors to tell us if we've inhaled a cone up to the
     * base.
     */
    public final static double MAXIMUM_ROTATION_TIME_SECONDS = MINIMUM_ROTATION_TIME_SECONDS + 5;

    public final static double AUTON_BACKWARD_TIME_SECONDS = 2.0;
} 
