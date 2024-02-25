// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import frc.robot.subsystems.ShooterSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterSubsystemCommand extends CommandBase {

  private final ShooterSubsystem ShooterSubsystem;
  //private final XboxController xboxController;
  private final BooleanSupplier ShooterIn;
  private final BooleanSupplier ShooterOut;
  
  /** Creates a new SwerveControllerDrive. */
  public ShooterSubsystemCommand(ShooterSubsystem ShooterSubsystem, BooleanSupplier ShooterIn, BooleanSupplier ShooterOut, boolean printDebugInput) {
    this.ShooterSubsystem = ShooterSubsystem;
    this.ShooterIn = ShooterIn;
    this.ShooterOut = ShooterOut;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.ShooterSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ShooterSubsystem.ShooterOff(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    if (ShooterIn.getAsBoolean()){
      // Disable for now
      //ShooterSubsystem.ShooterIn();
    } else if (ShooterOut.getAsBoolean()) {
      ShooterSubsystem.ShooterOut();
    } else {
      ShooterSubsystem.ShooterOff(); 
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ShooterSubsystem.ShooterOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
