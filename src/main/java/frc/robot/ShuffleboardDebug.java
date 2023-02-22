package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * Contains {@link GenericEntry generic entries} for anything we debug in the 
 * shuffleboard
 */
public class ShuffleboardDebug {
    public GenericEntry shoulderAngle;
    public GenericEntry elbowAngle;
    public GenericEntry wristAngle;

    public ShuffleboardDebug() {
        var foo = Shuffleboard.getTab("Arm");
        var layout = foo.getLayout("bar", BuiltInLayouts.kGrid)
            .withPosition(4, 0)
            .withSize(4, 6);
        shoulderAngle = layout.add("Shoulder angle", 0)
            .withPosition(0, 0)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kGyro)
            .getEntry();
        elbowAngle = layout.add("Elbow angle", 0)
            .withPosition(2, 0)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kGyro)
            .getEntry();
        wristAngle = layout.add("Wrist angle", 0)
            .withPosition(4, 0)
            .withSize(2, 2)
            .withWidget(BuiltInWidgets.kGyro)
            .getEntry();
    }
}
