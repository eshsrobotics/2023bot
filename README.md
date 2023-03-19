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
| Front right wheel | Spark MAX | 7 (CAN) | Label: **Z** (tested, works) |
| Back right wheel | Spark MAX | 5 (CAN) | Label: **A** (tested, works) |
| Back left wheel | Spark MAX | 1 (CAN) | Label: **B** (tested, works) |
| Wrist motor | Spark MAX | 12 (CAN) | Label: **P** (tested, works) |
| Left roller motor | Spark MAX | 9 (CAN) | Label: **J** (tested, works) |
| Right roller motor | Spark MAX | 2 (CAN) | Label: **N** (tested, works) |

## Control scheme
- The only controller is an Xbox controller 

  - The LEFT joystick controls translation (Y channel is forward/back, X channel is left/right) and the RIGHT joystick's X channel controls
    rotation
  - R1 (Right bumper) will move the wrist up
  - R2 (Right trigger) will move the wrist down
  - L1 (Left bumper) will intake with the roller
  - L2 (Left trigger) will outtake with the roller