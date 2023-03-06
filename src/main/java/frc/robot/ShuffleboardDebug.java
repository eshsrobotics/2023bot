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

    public GenericEntry forwardBack;
    public GenericEntry leftRight;
    public GenericEntry rotation; 

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

        var inputTab = Shuffleboard.getTab("Input");
        var inputLayout = inputTab.getLayout("Input", BuiltInLayouts.kGrid)
            .withSize(4, 6);
        forwardBack = inputLayout.add("frontBack", 0)
            .getEntry();
        leftRight = inputLayout.add("leftRight", 0)
            .getEntry();
        rotation = inputLayout.add("rotation", 0)
            .getEntry();
    }
}
