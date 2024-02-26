package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import javax.net.ssl.TrustManager;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Robot;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.ArmPosition;

public class ArmSubsystem extends SubsystemBase {
   
    private final WPI_TalonSRX talonMotorLeft;    
    private final WPI_TalonSRX talonMotorRight;   
    private final Encoder throughBoreEncoder; 
    private double speed;
    private boolean printDebug;
    public ArmPosition armPosition = ArmPosition.UNKNOWN;
  
    public ArmSubsystem (int motorIDInputLeft, int motorIDInputRight, double speed, boolean printDebugInput) {

        this.speed = speed;
        printDebug = printDebugInput;

        talonMotorLeft = new WPI_TalonSRX(motorIDInputLeft);
        talonMotorLeft.setInverted(false);        
        talonMotorRight = new WPI_TalonSRX(motorIDInputRight);
        talonMotorRight.setInverted(true);    
        
        // Initializes an encoder on DIO pins 0 and 1
        // 2X encoding and inverted
        throughBoreEncoder = new Encoder(0, 1, true, Encoder.EncodingType.k2X);
        throughBoreEncoder.setDistancePerPulse((double) 360/8192/2);

        if (printDebug) {
            System.out.println("ArmSubsystem: MotorID constructor ");
        }
    }

    /* Sets arm to upright position which is for start of game */
    public void UprightPosition(boolean initializeEncoder) {
        int BoreEncoderVal;
        boolean speedSet = false;
        int ls_left_fwd, ls_left_rev, ls_right_fwd, ls_right_rev;

        //SmartDashboard.putNumber("arm encoder direction",throughBoreEncoder.getRaw());

        if (printDebug) {
             System.out.println("UprightPosition");
        }

        // This is the setting for moving arm upward
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);
        
        if (initializeEncoder) {
            // Run until limit switch stops motors, then reset encoder
            // FIXME: Update one other limit switch is in place
            //while ((talonMotorLeft.isFwdLimitSwitchClosed()!=1) || (talonMotorRight.isRevLimitSwitchClosed()!=1)) {
            while (talonMotorLeft.isRevLimitSwitchClosed()!=1) {
                if (speedSet==false) {
                    talonMotorLeft.set(speed);
                    talonMotorRight.set(speed);
                    speedSet=true;
                }
                if (printDebug) {
                    ls_left_fwd=talonMotorLeft.isFwdLimitSwitchClosed();
                    ls_left_rev=talonMotorLeft.isRevLimitSwitchClosed();
                    ls_right_fwd=talonMotorRight.isFwdLimitSwitchClosed();
                    ls_right_rev=talonMotorRight.isRevLimitSwitchClosed();
                    // System.out.println("Waiting to hit upright position. "+ls_left_fwd+
                    //                    " - "+ls_left_rev+" - "+ls_right_fwd+
                    //                    " - "+ls_right_rev);
                }  
            }
   
            // Stop motors in case one of them still running
            talonMotorLeft.set(0.0);
            talonMotorRight.set(0.0);

            // Initialize (zero) the Bore Encoder
            throughBoreEncoder.reset(); 

            // FIXME: Should the Arm move forward slightly so it does not hit limit switch?
 
        }
        else {
            // Run until ARM_UPRIGHT_BORE_ENCODER_POSITION
            BoreEncoderVal=throughBoreEncoder.getRaw();
            if (printDebug) {
                System.out.println("UprightPosition BoreEncoderVal Start = "+BoreEncoderVal);
                System.out.println("UprightPosition BoreEncoderVal Start = "+throughBoreEncoder.getDistance());
            }

            while (BoreEncoderVal>ArmConstants.ARM_UPRIGHT_BORE_ENCODER_POSITION) {
                if (speedSet==false) {
                    talonMotorLeft.set(speed);
                    talonMotorRight.set(speed);
                    speedSet=true;
                }
                BoreEncoderVal=throughBoreEncoder.getRaw();
            }

            talonMotorLeft.set(0);
            talonMotorRight.set(0);

            if (printDebug) {
                BoreEncoderVal=throughBoreEncoder.getRaw();
                System.out.println("UprightPosition BoreEncoderVal End = "+BoreEncoderVal);
                System.out.println("UprightPosition BoreEncoderVal End = "+throughBoreEncoder.getDistance());
            }

        }

        armPosition = ArmPosition.UPRIGHT;

    }

    /* Sets arm to intake position to take in game piece */
    public void IntakePosition() {
        int BoreEncoderVal;
        boolean speedSet = false;

        if (printDebug) {
            System.out.println("IntakePosition");
        }

        // This is the setting for moving arm downward
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        if (printDebug) {
            System.out.println("IntakePosition BoreEncoderVal Start = "+BoreEncoderVal);
            System.out.println("IntakePosition BoreEncoderVal Start = "+throughBoreEncoder.getDistance());
        }

        // Run until ARM_INTAKE_BORE_ENCODER_POSITION
        while (BoreEncoderVal<ArmConstants.ARM_INTAKE_BORE_ENCODER_POSITION) {
            if (speedSet==false) {
                talonMotorLeft.set(speed);
                talonMotorRight.set(speed);
                speedSet=true;
            }
            BoreEncoderVal=throughBoreEncoder.getRaw();
        }

        talonMotorLeft.set(0);
        talonMotorRight.set(0);

        if (printDebug) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
            System.out.println("IntakePosition BoreEncoderVal End = "+BoreEncoderVal);
            System.out.println("IntakePosition BoreEncoderVal End = "+throughBoreEncoder.getDistance());
        }    

        armPosition = ArmPosition.INTAKE;
        
    }

    /* Sets arm to shooter position to spit out game piece */
    public void SpeakerShooterPosition() {
        int BoreEncoderVal;
        boolean speedSet = false;

        if (printDebug) {
            System.out.println("SpeakerShooterPosition");
        }

        // This is the setting for moving arm upward
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        if (printDebug) {
            System.out.println("SpeakerShooterPosition BoreEncoderVal Start = "+BoreEncoderVal);
            System.out.println("SpeakerShooterPosition BoreEncoderVal Start = "+throughBoreEncoder.getDistance());
        }

        // Run until ARM_SPEAKER_SHOOTER_BORE_ENCODER_POSITION
        while (BoreEncoderVal>ArmConstants.ARM_SPEAKER_SHOOTER_BORE_ENCODER_POSITION) {
            if (speedSet==false) {
                talonMotorLeft.set(speed);
                talonMotorRight.set(speed);
                speedSet=true;
            }
            BoreEncoderVal=throughBoreEncoder.getRaw();
        }

        talonMotorLeft.set(0);
        talonMotorRight.set(0);

        if (printDebug) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
            System.out.println("SpeakerShooterPosition BoreEncoderVal End = "+BoreEncoderVal);
            System.out.println("SpeakerShooterPosition BoreEncoderVal End = "+throughBoreEncoder.getDistance());
        }    

        armPosition = ArmPosition.SPEAKER_SHOOTER;

    }

    /* Sets arm to shooter position to spit out game piece */
    public void AmpShooterPosition() {
        int BoreEncoderVal;
        boolean speedSet = false;

        if (printDebug) {
            System.out.println("AmpShooterPosition");
        }

        // This is the setting for moving arm upward
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        if (printDebug) {
            System.out.println("AmpShooterPosition BoreEncoderVal Start = "+BoreEncoderVal);
        }

        // Run until ARM_AMP_SHOOTER_BORE_ENCODER_POSITION
        while (BoreEncoderVal>ArmConstants.ARM_AMP_SHOOTER_BORE_ENCODER_POSITION) {
            if (speedSet==false) {
                talonMotorLeft.set(speed);
                talonMotorRight.set(speed);
                speedSet=true;
            }
            BoreEncoderVal=throughBoreEncoder.getRaw();
        }

        talonMotorLeft.set(0);
        talonMotorRight.set(0);

        if (printDebug) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
            System.out.println("AmpSpeakerShooterPosition BoreEncoderVal End = "+BoreEncoderVal);
        }    

        armPosition = ArmPosition.AMP_SHOOTER;

    }

    /* Sets arm to handing-on-chain position which is for end of game */
    public void HangPosition() {
        int BoreEncoderVal;
        boolean speedSet = false;

        if (printDebug) {
            System.out.println("HangPosition");
        }

        // This is the setting for moving arm downward
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        if (printDebug) {
            System.out.println("HangPosition BoreEncoderVal Start = "+BoreEncoderVal);
        }

        // Run until ARM_HANG_BORE_ENCODER_POSITION
        while (BoreEncoderVal>ArmConstants.ARM_HANG_BORE_ENCODER_POSITION) {
            if (speedSet==false) {
                talonMotorLeft.set(speed);
                talonMotorRight.set(speed);
                speedSet=true;
            }
            BoreEncoderVal=throughBoreEncoder.getRaw();
        }

        talonMotorLeft.set(0);
        talonMotorRight.set(0);

        if (printDebug) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
            System.out.println("HangPosition BoreEncoderVal End = "+BoreEncoderVal);
        }    

        armPosition = ArmPosition.HANG;

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

