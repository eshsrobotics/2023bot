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
     * 
     * <p>TODO: This value is **FAKE**.  Replace with measured values.</p>
     */
    public final static double CHASSIS_WIDTH_INCHES = 10.0; 

    /**
     * Length of the chassis drivebase (from the center of a wheel on the front to 
     * the center of a wheel on the back)
     * 
     * <p>TODO: This value is **FAKE**.  Replace with measured values.</p>
     */
    public final static double CHASSIS_LENGTH_INCHES = 10.0;

    /**
     * Drive PWM port assignments
     */
    public final static int BACK_LEFT_PWM_PORT = 2;
    public final static int FRONT_LEFT_PWM_PORT = 1;
    public final static int FRONT_RIGHT_PWM_PORT = 4;
    public final static int BACK_RIGHT_PWM_PORT = 3;

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
} 


