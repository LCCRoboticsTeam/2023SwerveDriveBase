// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.SwerveGamepadDriveCommand;
import frc.robot.commands.IntakeSubsystemCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
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
  private final CommandXboxController commandXboxController = new CommandXboxController(
      OperatorConstants.XBOX_CONTROLLER_PORT);
   private final XboxController xboxController = new XboxController(
    OperatorConstants.XBOX_CONTROLLER_PORT);

  private final IntakeSubsystem inTake = new IntakeSubsystem(DriveConstants.INTAKE_CAN_ID, IntakeConstants.INTAKE_MOTOR_SPEED,true);

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
  
    driveTrain.setDefaultCommand(new SwerveGamepadDriveCommand(driveTrain,commandXboxController::getLeftX,
    commandXboxController::getLeftY, commandXboxController::getRightX, fieldRelativeChooser::getSelected));
    
    //inTake.setDefaultCommand(new IntakeSubsystemCommand(inTake, xboxController.getHID(), true));
    inTake.setDefaultCommand(new IntakeSubsystemCommand(inTake, xboxController::getAButton, xboxController::getBButton, true));
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
    commandXboxController.rightBumper().whileTrue(driveTrain.run(driveTrain::setX));

    
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
