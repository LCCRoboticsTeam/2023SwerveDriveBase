package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Robot;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
   
    private final WPI_TalonSRX talonMotorLeft;   
    private final WPI_TalonSRX talonMotorRight;
    //private final Encoder throughBoreEncoderLeft;
    //private final Encoder throughBoreEncoderRight;
    private double speed;
    private boolean printDebug;
  
    public ShooterSubsystem (int motorIDInputLeft, int motorIDInputRight, double speed, boolean printDebugInput) {
    
        this.speed = speed;
        printDebug = printDebugInput;

        talonMotorLeft = new WPI_TalonSRX(motorIDInputLeft);
        talonMotorLeft.setInverted(false);        
        talonMotorRight = new WPI_TalonSRX(motorIDInputRight);
        talonMotorRight.setInverted(false);  

        if (printDebug) {
            System.out.println("ShooterSubsystem: MotorID constructor ");
        }
    }

    /* Sets Shooter to take in game piece */
    public void ShooterIn() {
        //talonMotorLeft.setInverted(true);
        //talonMotorRight.setInverted(true);

        talonMotorLeft.set(-speed);
        talonMotorRight.set(-speed);

    }

    /* Sets Shooter to send game piece */
    public void ShooterOut(boolean highspeed) {
        int BoreEncoderValLeft;
        int BoreEncoderValRight;

        //talonMotorLeft.setInverted(false);
        //talonMotorRight.setInverted(false);

        if (highspeed) {
            talonMotorLeft.set(speed*ShooterConstants.SHOOTER_HIGH_SPEED_MULTIPLIER);
            talonMotorRight.set(speed*ShooterConstants.SHOOTER_HIGH_SPEED_MULTIPLIER);
        }
        else {
          talonMotorLeft.set(speed);
          talonMotorRight.set(speed);
        }

        //BoreEncoderValLeft=throughBoreEncoderLeft.getRate();
        //BoreEncoderValRight=throughBoreEncoderRight.getRate()
        //if (printDebug) {
        //    System.out.println("intakeIn BoreEncoderValLeft = "+BoreEncoderValLeft);
        //    System.out.println("intakeIn BoreEncoderValRight = "+BoreEncoderValRight);
        //}

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

