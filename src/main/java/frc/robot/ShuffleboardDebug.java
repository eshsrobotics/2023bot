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
    public GenericEntry shoulderCurrentAngle;
    public GenericEntry elbowCurrentAngle;
    public GenericEntry wristCurrentAngle;
    public GenericEntry shoulderDesiredAngle;
    public GenericEntry elbowDesiredAngle;
    public GenericEntry wristDesiredAngle;
    public GenericEntry x;
    public GenericEntry y;

    /**
     * If true, arm angles are from the shuffleboard instead of controller commands
     */
    public GenericEntry overrideAngles;

    public GenericEntry frontLeft;
    public GenericEntry frontRight;
    public GenericEntry backLeft;
    public GenericEntry backRight;

    public GenericEntry forwardBack;
    public GenericEntry leftRight;
    public GenericEntry rotation;
    private Map<GenericEntry, GenericEntry> entriesChanged;

    public ShuffleboardDebug() {
        var armTab = Shuffleboard.getTab("Arm");
        var layout = armTab.getLayout("bar", BuiltInLayouts.kGrid)
                .withPosition(0, 0)
                .withSize(1, 1);
        shoulderCurrentAngle = armTab.add("Current shoulder angle", 0)
                .withPosition(0, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        elbowCurrentAngle = armTab.add("Current elbow angle", 0)
                .withPosition(2, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        wristCurrentAngle = armTab.add("Current wrist angle", 0)
                .withPosition(4, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        shoulderDesiredAngle = armTab.add("Desired shoulder angle", 0)
                .withPosition(6, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        elbowDesiredAngle = armTab.add("EDesired elbow angle", 0)
                .withPosition(8, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        wristDesiredAngle = armTab.add("Desired wrist angle", 0)
                .withPosition(10, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kGyro)
                .getEntry();
        x = armTab.add("X", 0)
                .withPosition(2, 2)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .getEntry();
        y = armTab.add("Y", 0)
                .withPosition(4, 2)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .getEntry();
        overrideAngles = armTab.add("True = Override", 0)
                .withPosition(6, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .getEntry();

        var inputTab = Shuffleboard.getTab("Input");
        var inputLayout = inputTab.getLayout("Input", BuiltInLayouts.kGrid)
                .withSize(4, 6);
        forwardBack = inputTab.add("frontBack", 0)
                .withPosition(0, 2)

                .getEntry();
        leftRight = inputTab.add("leftRight", 0)
                .withPosition(1, 2)
                .getEntry();
        rotation = inputTab.add("rotation", 0)
                .withPosition(2, 2)
                .getEntry();

        var vroomTab = Shuffleboard.getTab("Vroom");
        var vroomLayout = vroomTab.getLayout("Vroom", BuiltInLayouts.kGrid)
                .withSize(4, 6);
        frontLeft = vroomTab.add("Front Left", 0)
                .withPosition(0, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kDial)
                .getEntry();
        frontRight = vroomTab.add("Front Right", 0)
                .withPosition(2, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kDial)
                .getEntry();
        backLeft = vroomTab.add("Back Left", 0)
                .withPosition(4, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kDial)
                .getEntry();
        backRight = vroomTab.add("Back Right", 0)
                .withPosition(6, 0)
                .withSize(2, 2)
                .withWidget(BuiltInWidgets.kDial)
                .getEntry();
    }
}
