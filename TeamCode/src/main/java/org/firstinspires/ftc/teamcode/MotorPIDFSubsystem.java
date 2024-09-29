package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;


public class MotorPIDFSubsystem {
    double Kp,Ki,Kd,setPoint,integralSum,lastError,error, derivative;
    public MotorPIDFSubsystem(double Kp,double Ki,double Kd,double setPoint){
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd =Kd;
        this.setPoint = setPoint;
    }

    /**
     * Should be ran in a loop where the motor is set to getPIDSpeed()
     * ex. motor.setPower(getPIDSpeed(motor.getPosition(),timer));
     * the getposition should be the encoder position
     * and the timer should be from an elapsed timer that starts right before the 
     * main command loop.
     */
    public double getPIDSpeed(double encoderPos, ElapsedTime timer){
        error = setPoint-encoderPos;
        derivative = (error-lastError)/timer.seconds();
        integralSum = integralSum+(error* timer.seconds());
        lastError = error;
        timer.reset();
        return (Kp * error) + (Ki * integralSum) + (Kd * derivative);
    }
    public void setSetPoint(double s){
        setPoint = s;
    }
    public void setKp(double Kp){
        this.Kp = Kp;
    }
    public void setKi(double Ki){
        this.Ki = Ki;
    }public void setKd(double Kd){
        this.Kd = Kd;
    }
    

}
