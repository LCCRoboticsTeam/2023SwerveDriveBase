// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import frc.robot.Constants.ArmPosition;
import frc.robot.subsystems.ArmSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmSubsystemCommand extends CommandBase {

  private final ArmSubsystem ArmSubsystem;
  private final BooleanSupplier IntakeAndSpeakerPosition;
  private final BooleanSupplier AmpShooterPosition;
  
  public ArmSubsystemCommand(ArmSubsystem ArmSubsystem, BooleanSupplier IntakeAndSpeakerPosition, BooleanSupplier AmpShooterPosition, boolean printDebugInput) {
    this.ArmSubsystem = ArmSubsystem;
    this.IntakeAndSpeakerPosition = IntakeAndSpeakerPosition;
    this.AmpShooterPosition = AmpShooterPosition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ArmSubsystem.UprightPosition(true); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    if (IntakeAndSpeakerPosition.getAsBoolean()){
      if (ArmSubsystem.armPosition != ArmPosition.INTAKE){
          ArmSubsystem.IntakePosition();
      } else {
        ArmSubsystem.SpeakerShooterPosition();
      }
    } else if (AmpShooterPosition.getAsBoolean()) {
      ArmSubsystem.AmpShooterPosition();
    } else {
    //  ArmSubsystem.UprightPosition(false); 
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //ArmSubsystem.UprightPosition(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
