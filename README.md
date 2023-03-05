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
- If using an Xbox controller, the left joystick controls translation and the
  right joystick controls rotation
- If using a joystick, moving the joystick controls translation and rotating the
  joystick controls rotation
- If using both an Xbox controller and a joystick, the values for translation
  and rotation from each controller are added together, but there is a maximum
  value of 1 and a minimum value of -1
- Field-oriented Mecanum drive — The robot's forward is away from the driver,
  the robot's left is the driver's left, etc.