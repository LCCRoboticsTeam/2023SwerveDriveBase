package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
/*
// Use this for Spark motor controllers
import edu.wpi.first.wpilibj.Spark;
*/
// Use this for Talon motor controllersA
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.Robot;

public class intakesubsystem extends SubsystemBase {

    //private final SpeedController extender = RobotMap.armHand;
    //private final Spark sparkMotor;
    
    private final WPI_TalonSRX talonMotor;
    private final XboxController xboxController;
    
    private double speed;
    private boolean printDebug;
  
    public intakesubsystem (int motorIDInput, XboxController xboxController, boolean printDebugInput) {
    
        speed = 0.7;
        this.xboxController=xboxController;
        printDebug = printDebugInput;

        talonMotor = new WPI_TalonSRX(motorIDInput);
        talonMotor.setInverted(false);        

        /*
        if(xboxController.leftBumper().getAsBoolean()){
            System.out.println("yes");
            talonMotor.set(speed);
        }else{
            talonMotor.set(0);
            System.out.println("no");
        }
*/

        if (printDebug) {
            System.out.println("intakesubsystem: MotorID constructor ");
        }
    }

    /* Sets intake to take in balls */
    public CommandBase intakeIn() {
        talonMotor.setInverted(false);
        talonMotor.set(speed);
        return runOnce(
            () -> {
                /* one-time action goes here */
          });
    }

    /* Sets intake to spit out balls */
    public CommandBase intakeOut() {
        talonMotor.setInverted(true);
        talonMotor.set(speed);
        return runOnce(
            () -> {
                /* one-time action goes here */
          });
    }

    /* Turn intake off */
    public CommandBase intakeOff() {
        talonMotor.set(0.0);
        return runOnce(
            () -> {
                /* one-time action goes here */
          });
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    	// speed = Robot.m_oi.getSpeed();
    	speed = .1;
        if (xboxController.getAButton()){
          talonMotor.setInverted(false);
          talonMotor.set(speed);
        } else if (xboxController.getBButton()) {
          talonMotor.setInverted(true);
          talonMotor.set(speed);
        } else {
          talonMotor.set(0.0); 
        }
    }
    	
        /*
    	if (Robot.m_oi.getBallIn()) {
            if (printDebug) {
                System.out.println("IntakeArm: extend speed = " + -1.0 * speed);
            }
            talonMotor.setInverted(false);  // do not reverse motor
            talonMotor.set(speed);          // activate motor

    	} else if (Robot.m_oi.getBallOut()) {
            if (printDebug) {
                System.out.println("IntakeArm: extend speed = " + -1.0 * speed);
            }

            talonMotor.setInverted(true);  // reverse motor
            talonMotor.set(speed);         // activate motor

    	} else {
            talonMotor.set(0);
    	}
        */
    	


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public boolean isPrintDebug() {
        return printDebug;
    }

    public void setPrintDebug(boolean printDebug) {
        this.printDebug = printDebug;
    }

}

