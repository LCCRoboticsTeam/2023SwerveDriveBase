package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;

public class ShooterSubsystem extends SubsystemBase {
   
    private final WPI_TalonSRX talonMotorLeft;   
    private final WPI_TalonSRX talonMotorRight;
    private double speed;
    private boolean printDebug;
  
    public ShooterSubsystem (int motorIDInputLeft, int motorIDInputRight, double speed, boolean printDebugInput) {
    
        this.speed = speed;
        printDebug = printDebugInput;

        talonMotorLeft = new WPI_TalonSRX(motorIDInputLeft);
        talonMotorLeft.setInverted(false);        
        talonMotorRight = new WPI_TalonSRX(motorIDInputRight);
        talonMotorRight.setInverted(true);  

        if (printDebug) {
            System.out.println("ShooterSubsystem: MotorID constructor ");
        }
    }

    /* Sets Shooter to take in game piece */
    public void ShooterIn() {
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

    }

    /* Sets Shooter to send game piece */
    public void ShooterOut() {
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

    }

    /* Turn Shooter off */
    public void ShooterOff() {
        talonMotorLeft.set(0.0);
        talonMotorRight.set(0.0);

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public boolean isPrintDebug() {
        return printDebug;
    }

    public void setPrintDebug(boolean printDebug) {
        this.printDebug = printDebug;
    }

}

