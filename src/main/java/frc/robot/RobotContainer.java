// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.SwerveGamepadDriveCommand;
import frc.robot.commands.intakesubsystemCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.intakesubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrainSubsystem driveTrain = new DriveTrainSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController xboxController = new CommandXboxController(
      OperatorConstants.XBOX_CONTROLLER_PORT);

  private final intakesubsystem inTake = new intakesubsystem(DriveConstants.INTAKE_CAN_ID, xboxController.getHID() , true);

  private final SendableChooser<Boolean> fieldRelativeChooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    fieldRelativeChooser.setDefaultOption("Field Relative",  true);
    fieldRelativeChooser.addOption("Robot Relative", false);
    SmartDashboard.putData(fieldRelativeChooser);

    //driveTrain.setDefaultCommand(new SwerveGamepadDriveCommand(driveTrain, xboxController::getLeftY,
    //   xboxController::getLeftX, xboxController::getRightX, fieldRelativeChooser::getSelected));
  
    // Test
    driveTrain.setDefaultCommand(new SwerveGamepadDriveCommand(driveTrain,xboxController::getLeftX,
        xboxController::getLeftY, xboxController::getRightX, fieldRelativeChooser::getSelected));
    
    //inTake.setDefaultCommand(new intakesubsystemCommand(inTake));
    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    xboxController.rightBumper().whileTrue(driveTrain.run(driveTrain::setX));

    //xboxController.a().whileTrue(inTake.intakeIn());    
    //xboxController.b().whileTrue(inTake.intakeOut());
    //xboxController.x().whileTrue(inTake.intakeOff());
    //xboxController.y().whileTrue(inTake.intakeOff());
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return Autos.templateAuto(driveTrain);
  }
}
