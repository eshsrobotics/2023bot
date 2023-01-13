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

}
