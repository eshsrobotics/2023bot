package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * Contains {@link GenericEntry generic entries} for anything we debug in the 
 * shuffleboard
 */
public class ShuffleboardDebug {
    public GenericEntry joystickRotation;
    public GenericEntry joystickFrontBack;
    public GenericEntry zAxis;
    public GenericEntry yAxis;

    public ShuffleboardDebug() {
        var foo = Shuffleboard.getTab("Testbed");
        var layout = foo.getLayout("bar", BuiltInLayouts.kGrid)
            .withPosition(4, 0)
            .withSize(4, 6);
        joystickRotation = layout.add("joystick rotation", 0)
            .withPosition(0, 0)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", -100, "max", 100))
            .getEntry();
        joystickFrontBack = layout.add("joystick front back", 0)
            .withPosition(3, 0)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", -100, "max", 100))
            .getEntry();
        zAxis = layout.add("Z-axis", 0)
            .withPosition(2, 2)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", -100, "max", 100))
            .getEntry();
        yAxis = layout.add("Y-axis", 0)
            .withPosition(4, 2)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", -1, "max", 1))
            .getEntry();
    }
}
