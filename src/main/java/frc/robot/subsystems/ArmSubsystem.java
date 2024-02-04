package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import javax.net.ssl.TrustManager;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;

public class ArmSubsystem extends SubsystemBase {
   
    private final WPI_TalonSRX talonMotorLeft;    
    private final WPI_TalonSRX talonMotorRight;   
    private final Encoder throughBoreEncoder; 
    private double speed;
    private boolean printDebug;
  
    public ArmSubsystem (int motorIDInputLeft, int motorIDInputRight, double speed, boolean printDebugInput) {

        this.speed = speed;
        printDebug = printDebugInput;

        talonMotorLeft = new WPI_TalonSRX(motorIDInputLeft);
        talonMotorLeft.setInverted(false);        
        talonMotorRight = new WPI_TalonSRX(motorIDInputRight);
        talonMotorRight.setInverted(true);    
        
        // Initializes an encoder on DIO pins 0 and 1
        // 2X encoding and non-inverted
        throughBoreEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
        
        if (printDebug) {
            System.out.println("ArmSubsystem: MotorID constructor ");
        }
    }

    /* Sets arm to upright position which is for start of game */
    public void UprightPosition(boolean initializeEncoder) {
 
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

        if (initializeEncoder) {
            // Run until limit switch stops motors, then reset encoder
            while ((talonMotorLeft.get()!=0.0) && (talonMotorRight.get()!=0.0));

            throughBoreEncoder.reset(); 
        }
        else {
          // Run until INTAKE_POSITION_UPRIGHT_VALUE (0)
        }
    }

    /* Sets arm to intake position to take in game piece */
    public void IntakePosition() {
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

        // Run until INTAKE_POSITION_ENCODER_VALUE
    }

    /* Sets arm to shooter position to spit out game piece */
    public void ShooterPosition() {
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

        // Run until SHOOTER_POSITION_ENCODER_VALUE
    }

    /* Sets arm to handing-on-chain position which is for end of game */
    public void HangingPosition() {
        //talonMotorLeft.set(0.0);
        //talonMotorRight.set(0.0);

        // Run until INTAKE_POSITION_HANGING_VALUE
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

