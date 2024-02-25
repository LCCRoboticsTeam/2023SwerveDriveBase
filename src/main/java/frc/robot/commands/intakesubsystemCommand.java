// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import frc.robot.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSubsystemCommand extends CommandBase {

  private final IntakeSubsystem IntakeSubsystem;
  //private final XboxController xboxController;
  private final BooleanSupplier InTakeIn;
  private final BooleanSupplier InTakeOut;
  
  public IntakeSubsystemCommand(IntakeSubsystem IntakeSubsystem, BooleanSupplier InTakeIn, BooleanSupplier InTakeOut, boolean printDebugInput) {
    this.IntakeSubsystem = IntakeSubsystem;
    //this.xboxController = xboxController;
    this.InTakeIn = InTakeIn;
    this.InTakeOut = InTakeOut;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.IntakeSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    IntakeSubsystem.intakeOff(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    if (InTakeIn.getAsBoolean()){
      IntakeSubsystem.intakeIn();
    } else if (InTakeOut.getAsBoolean()) {
      // FIXME: Disable for now
      //IntakeSubsystem.intakeOut();
    } else {
      IntakeSubsystem.intakeOff(); 
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    IntakeSubsystem.intakeOff();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
