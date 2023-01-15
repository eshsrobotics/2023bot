<a id="orgbe56a7a"></a>

# Mecanum Drive: Autonomous trajectories and field-oriented Mecanum

-   Let's build these WPILib concepts from the bottom to the top.
    1.  [Pose2d](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/geometry/Pose2d.html): Represents the robot's position and orientation on the field
        -   It consists of a vector and an angle (the displacement from the starting position, and the chassis rotation relative to the starting position.)
    2.  [MecanumDriveWheelSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveWheelSpeeds.html): Represents the velocities, *in meters per second*, of all four Mecanum wheels
        -   **Interesting methods**:
            1.  [desaturate(double maxSpeedMetersPerSecond)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveWheelSpeeds.html#desaturate%28double%29)
                -   Reduced wheel speeds so none are above the specified maximum velocities, while maintaining the ratio of speeds between individual wheels.
    3.  [MecanumDriveKinematics](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveKinematics.html): Converts ChassisSpeeds to and from `MecanumDriveWheelSpeeds`
        -   **Required prerequisites**:
            1.  Four vectors representing the positions of (the centers of) the four Mecanum wheels relative to the center of the chassis. Get these from CAD or use a measuring tape.
        -   **Interesting methods**:
            1.  [toChassisSpeeds(MecanumDriveWheelSpeeds): ChassisSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveKinematics.html#toChassisSpeeds%28edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds%29)
                -   Converts overall chassis speeds to wheel velocities *in meters per second*.
            2.  [toWheelSpeeds(ChassisSpeeds): MecanumDriveWheelSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveKinematics.html#toWheelSpeeds%28edu.wpi.first.math.kinematics.ChassisSpeeds%29)
                -   Inverse kinematics: Converts the given desired chassis velocity into the velocity for individual wheels.
            3.  toWheelSpeeds(ChassisSpeeds, Translation2d):
                -   Inverse kinematics: Converts the given desired chassis velocity into the velocity for individual wheels.

                    Any rotation will be centered about the given point on the robot's chassis instead of its wheelbase center. This permits special turning maneuvers that would be difficult to perform otherwise.
    4.  [Encoder](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Encoder.html): Linear encoder hardware abstraction for measuring motor rotations
        -   **Required prerequisites**:
            1.  DIO ports for A and B pins
            2.  Reversal flag
            3.  Knowledge of the [encoding type](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/CounterBase.EncodingType.html):
                -   **`EncodingType.k1X`:** The encoder count increments only on the rising edge of a pulse.
                -   **`EncodingType.k2X`:** The encoder count increments on both the rising and falling edge of a pulse.
                -   **`EncodingType.k4X`:** The encoder count increments on both the rising and falling edge of a pulse from both the A and B channels.
        -   **Interesting methods**:
            1.  [setDistancePerPulse(double distancePerPulse)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Encoder.html#setDistancePerPulse%28double%29)
                -   Sets the distance traveled during one encoder pulse. You must take your drive's gear reductions into account when passing in this value.
                -   Only useful for encoders that are tied to drive wheels and other things that effect linear motion.
            2.  [getDistance(): double](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Encoder.html#getDistance%28%29)
                -   Gets the current distance that the wheel has traveled based on the distance per pulse.
            3.  [get(): int](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Encoder.html#get%28%29)
                -   Gets the current number of clicks from the encoder.
            4.  [reset()](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Encoder.html#reset%28%29)
                -   Resets the encoder to 0.
    5.  [MecanumDriveWheelPositions](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveWheelPositions.html): Represents the measured encoder distance, *in meters*, for all four Mecanum wheels
        -   Generally seems to be constructed where needed; this would not be a member of a class.
        -   **Required prerequisites**:
            1.  Four encoders, one for each wheel (output shaft encoders.)
    6.  [MecanumDriveOdometry](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveOdometry.html): Keeps track of the robot's **[odometry](https://mcm-frc-docs.readthedocs.io/en/latest/docs/software/kinematics-and-odometry/mecanum-drive-odometry.html)** &#x2013; its current position and orientation &#x2013; using encoder feedback and an in-memory model.
        -   Note that odometry will drift over time as your robot makes contact with other things in the field. It's most accurate during autonomous.
        -   **Required prerequisites**:
            1.  A `MecanumDriveKinematics`
            2.  The current gyro angle as a `Rotation2d`
            3.  The current encoder values as a `MecanumDriveWheelPositions`
            4.  (Optional) an initial pose as a Pose2d
        -   **Interesting methods**:
            1.  [update(Rotation2d, MecanumDriveWheelPositions)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveOdometry.html#update%28edu.wpi.first.math.geometry.Rotation2d,edu.wpi.first.math.kinematics.MecanumDriveWheelPositions%29)
                -   Updates the odometry object based on new gyro and encoder measurements.
            2.  [getPoseMeters(): Pose2d](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveOdometry.html#getPoseMeters%28%29)
                -   Returns the robot's current position and orientation.
            3.  [resetPosition(Rotation2d, MecanumDriveWheelPositions, Pose2d)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/MecanumDriveOdometry.html#resetPosition%28edu.wpi.first.math.geometry.Rotation2d,edu.wpi.first.math.kinematics.MecanumDriveWheelPositions,edu.wpi.first.math.geometry.Pose2d%29)
                -   Resets the robot's position as tracked by this odometry object. Does not reset the gyro, nor is such an action necessary.
                -   **Must** be called if your reset the gyro.
    7.  [Trajectory](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/trajectory/Trajectory.html): Allows us to move through the positions and poses coming from the **[PathWeaver tool](https://mcm-frc-docs.readthedocs.io/en/latest/docs/software/wpilib-tools/pathweaver/index.html)**.
        -   **Interesting methods**:
            1.  [sample(double seconds): Trajectory.State](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/trajectory/Trajectory.html#sample%28double%29)
                -   Returns the trajectory's state at the given time.
            2.  [getTotalTimeSeconds(): double](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/trajectory/Trajectory.html#getTotalTimeSeconds%28%29)
                -   Returns the total duration of the trajectory in seconds.
    8.  [TrajectoryUtil](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/trajectory/TrajectoryUtil.html): Allows us to load `Trajectory` objects from PathWeaver JSON files.
        -   **Required prerequisites**
            1.  A path to load *on the roboRIO itself* (meaning that the JSON file must be copied into the roboRIO's filesystem using FileZilla or the like.)
        -   **Interesting methods**:
            1.  [fromPathweaverJson(Path path): Trajectory](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/trajectory/TrajectoryUtil.html#fromPathweaverJson%28java.nio.file.Path%29)
                -   Creates a Trajectory object from a PathWeaver export file.
    9.  [ChassisSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/ChassisSpeeds.html): Represents the overall velocity of any robot chassis
        -   **Interesting methods**:
            1.  [fromFieldRelativeSpeeds(ChassisSpeeds fieldRelativeSpeeds, Rotation2d gyroAngle): ChassisSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/ChassisSpeeds.html#fromFieldRelativeSpeeds%28edu.wpi.first.math.kinematics.ChassisSpeeds,edu.wpi.first.math.geometry.Rotation2d%29)
                -   Converts field-relative chassis speeds into robot-relative chassis speeds.
                    -   `fieldRelativeSpeeds.vxMetersPerSecond` here is the velocity toward the opposite alliance wall, with negative values moving toward the friendly alliance wall.
                    -   `fieldRelativeSpeeds.vyMetersPerSecond` here is toward (your alliance's) left field boundary, with negative values moving toward the right field boundary.
                    -   `fieldRelativeSpeeds.omegaRadiansPerSecond` here represents counterclockwise motion, with negative values rotating clockwise.
    10. [HolonomicDriveController](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/HolonomicDriveController.html): Yes, our old standard is back, and it works on MecanumDrives! Calculates the `ChassisSpeeds` needed to move the chassis into a trajectory position/orientation.
        -   **Required prerequisites**:
            1.  Two [PIDControllers](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/PIDController.html) for the velocity (one for forward/back, one for left/right); I have documentation for those things **[here](akotaobi.md).**
            2.  One [ProfiledPIDController](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/ProfiledPIDController.html) for rotation. This is just an ordinary `PIDController` with constraints for its maximum (angular) velocity and (angular) acceleration.
            3.  A `Trajectory` to navigate through.
            4.  A `MecanumDriveOdometry` we can use to grab the current pose.
        -   **Interesting methods**:
            1.  [calculate(Pose2d currentPose, Trajectory.State desiredState, Rotation2d desiredHeading): ChassisSpeeds](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/HolonomicDriveController.html#calculate%28edu.wpi.first.math.geometry.Pose2d,edu.wpi.first.math.trajectory.Trajectory.State,edu.wpi.first.math.geometry.Rotation2d%29)
                -   Returns the next `ChassisSpeeds` needed to get to the current trajectory state.
                -   **Q:** Shouldn't the desired heading come from the Trajectory?
            2.  [atReference(): boolean](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/HolonomicDriveController.html#atReference%28%29)
                -   Returns True if we are close to or at the desired pose coming from `calculate()`.
            3.  [setTolerance(Pose2d)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/controller/HolonomicDriveController.html#setTolerance%28edu.wpi.first.math.geometry.Pose2d%29)
                -   Used to tell the controller how much error or deviation you're willing to accept from the final position.
    11. [MecanumDrive](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html): Makes Mecanum wheels go *vroom*, formula-free
        -   This is only used for Teleop!
        -   **Required prerequisites**:
            1.  Four [MotorController](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/motorcontrol/MotorController.html) objects &#x2013; one for each motor. Order is front left, back left, front right, back right.
                -   **Q:** What happens if the motor is connected to a planetary gearbox?
        -   **Interesting methods**:
            1.  [driveCartesian(double forwardBack, double leftRight, double rotation)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html#driveCartesian%28double,double,double%29)
                -   Implements normal Mecanum driving (similar to the VEX `mechDrive()` function.)
                -   For the `Rotation` channel, counterclockwise values are positive.
            2.  [driveCartesian(double forwardBack, double leftRight, double rotation, Rotation2d gyroAngle)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html#driveCartesian%28double,double,double,edu.wpi.first.math.geometry.Rotation2d%29)
                -   Implements field-oriented Mecanum (!)
            3.  [driveCartesianIK(double forwardBack, double leftRight, double rotation)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html#driveCartesianIK%28double,double,double%29)
                -   Returns the *unitless* wheel speeds needed to implement normal Mecanum driving. The four values range from -1.0 to 1.0.
            4.  [driveCartesianIK(double forwardBack, double leftRight, double rotation, Rotation2d gyroAngle)](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html#driveCartesianIK%28double,double,double,edu.wpi.first.math.geometry.Rotation2d%29)
                -   Returns the *unitless* wheel speeds needed to implement field-oriented Mecanum. The four values range from -1.0 to 1.0.
            5.  [stopMotor()](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html#stopMotor%28%29)
                -   Stops all four motors.
