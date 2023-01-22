# 2023bot
Source code for ESHS P.O.T.A.T.O.E.S. Team 1759 FRC 2023 robot.

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