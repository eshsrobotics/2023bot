# 2023bot
Source code for ESHS P.O.T.A.T.O.E.S. Team 1759 FRC 2023 robot.

## Motor and port assignments

We anticipate the use of eight motors and one pneumatic element for this year's
robot.

Many of our Spark MAX motor controllers are not working, so as a preventative
measure, we have tested and labeled the working ones using [this document](https://docs.google.com/spreadsheets/d/1GVH6_3GqdCZB0zrcatGYz_2MCnv6uKWJbg4vYoOikac/edit#gid=0).
Please do not add Spark MAX motor controllers to the robot (or any other type!)
that have not been verified to work over CAN or PWM.

| Motor name | Device | Port (type) | Notes |
| --- | --- | --- | --- |
| Front left wheel | Spark MAX | 6 (CAN) | Label: **G** (tested, works) |
| Front right wheel | Spark MAX | 9 (CAN) | Label: **J** (tested, works) |
| Back right wheel | Spark MAX | 5 (CAN) | Label: **A** (tested, works) |
| Back left wheel | Spark MAX | 1 (CAN) | Label: **B** (tested, works) |

## Control scheme
- Field-oriented Mecanum drive — The robot's forward is away from the driver,
  the robot's left is the driver's left, etc.

- If using an Xbox controller 

  - The LEFT joystick controls translation (Y channel is forward/back, X channel is left/right) and the RIGHT joystick's X channel controls
    rotation
  - R1 (Right bumper) will increase (Move away from robot) the arm's X value
  - L1 (Left bumper) will decrease (Move towards the robot) the arm's X value
  - R2 (Right trigger) will increase (Move up) the arm's Y value
  - L2 (Left trigger) will decrease (Move down) the arm's Y value
  - ? controls claw roller pickup (intake)
  - ? controls claw roller release (outtake)

- If using a joystick

  - Moving the joystick controls translation and rotating the joystick controls
    rotation, and the small joystick controls the arm (Left/right controls X
    value and forward/back controls Y value)
  - Holding the trigger will enter **claw mode**, in which:
    * Moving the joystick forward/back will raise (pull toward driver) and lower (push away from
      driver) the arm's Y value.
    * Rotating the joystick left increases the X value of the arm, and rotating the joystick 
      right decreases the X value of the arm.
    * The small joystick _controls the drivetrain_ (Forward/back controls
      driving forward/backward, left/right controls strafing)
  - ? controls claw roller pickup (intake)
  - ? controls claw roller release (outtake)

- If using both an Xbox controller and a joystick

  - The values for translation and rotation from each controller are added
    together, but there is a maximum value of 1 and a minimum value of -1  