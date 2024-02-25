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
        int BoreEncoderVal;
        int ls_left_fwd, ls_left_rev, ls_right_fwd, ls_right_rev;
        System.out.println(throughBoreEncoder.getRaw());
        SmartDashboard.putNumber("arm encoder direction",throughBoreEncoder.getRaw());
        System.out.println("UprightPosition");
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);
        
        if (initializeEncoder) {
            // Run until limit switch stops motors, then reset encoder
            //while ((talonMotorLeft.isFwdLimitSwitchClosed()!=1) || (talonMotorRight.isRevLimitSwitchClosed()!=1)) {
            while (talonMotorLeft.isRevLimitSwitchClosed()!=1) {
                ls_left_fwd=talonMotorLeft.isFwdLimitSwitchClosed();
                ls_left_rev=talonMotorLeft.isRevLimitSwitchClosed();
                ls_right_fwd=talonMotorRight.isFwdLimitSwitchClosed();
                ls_right_rev=talonMotorRight.isRevLimitSwitchClosed();
                System.out.println("Waiting to hit upright position. "+ls_left_fwd+" - "+ls_left_rev+" - "+ls_right_fwd+" - "+ls_right_rev);
              }
   
            talonMotorLeft.set(0.0);
            talonMotorRight.set(0.0);

            throughBoreEncoder.reset(); 
            throughBoreEncoder.setReverseDirection(true);

            //talonMotorLeft.set(0.0);
            //talonMotorRight.set(0.0);
        }
        else {
            BoreEncoderVal=throughBoreEncoder.getRaw();
            System.out.println("BoreEncoderVal = "+BoreEncoderVal);
            //new WaitCommand(5);
            //BoreEncoderVal=throughBoreEncoder.getRaw();
            //System.out.println("BoreEncoderVal = "+BoreEncoderVal);
    
            while (BoreEncoderVal>ArmConstants.ARM_UPRIGHT_BORE_ENCODER_POSITION) {
            //while (speed!=0) {
                BoreEncoderVal=throughBoreEncoder.getRaw();
            //    System.out.println("BoreEncoderVal = "+BoreEncoderVal);
            }

          // Run until INTAKE_POSITION_UPRIGHT_VALUE (0)
          talonMotorLeft.set(0);
          talonMotorRight.set(0);
        }

    }

    /* Sets arm to intake position to take in game piece */
    public void IntakePosition() {
        int BoreEncoderVal;
        System.out.println(throughBoreEncoder.getDirection());
        System.out.println("IntakePosition");
        talonMotorLeft.setInverted(true);
        talonMotorRight.setInverted(false);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        System.out.println("BoreEncoderVal = "+BoreEncoderVal);
        //new WaitCommand(5);
        //BoreEncoderVal=throughBoreEncoder.getRaw();
        //System.out.println("BoreEncoderVal = "+BoreEncoderVal);

        while (BoreEncoderVal<ArmConstants.ARM_INTAKE_BORE_ENCODER_POSITION) {
        //while (speed!=0) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
        //    System.out.println("BoreEncoderVal = "+BoreEncoderVal);
        }
        // Run until INTAKE_POSITION_ENCODER_VALUE
        talonMotorLeft.set(0);
        talonMotorRight.set(0);
    }

    /* Sets arm to shooter position to spit out game piece */
    public void ShooterPosition() {
        int BoreEncoderVal;
        System.out.println(throughBoreEncoder.getDirection());
        System.out.println("ShooterPosition");
        talonMotorLeft.setInverted(false);
        talonMotorRight.setInverted(true);

        talonMotorLeft.set(speed);
        talonMotorRight.set(speed);

        BoreEncoderVal=throughBoreEncoder.getRaw();
        System.out.println("BoreEncoderVal = "+BoreEncoderVal);
        //new WaitCommand(5);
        //BoreEncoderVal=throughBoreEncoder.getRaw();
        //System.out.println("BoreEncoderVal = "+BoreEncoderVal);

        while (BoreEncoderVal>ArmConstants.ARM_SHOOTER_BORE_ENCODER_POSITION) {
        //while (speed!=0) {
            BoreEncoderVal=throughBoreEncoder.getRaw();
        //    System.out.println("BoreEncoderVal = "+BoreEncoderVal);
        }
        // Run until SHOOTER_POSITION_ENCODER_VALUE
        talonMotorLeft.set(0);
        talonMotorRight.set(0);
    }

    /* Sets arm to handing-on-chain position which is for end of game */
    public void HangingPosition() {
        talonMotorLeft.set(0.0);
        talonMotorRight.set(0.0);

        

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

