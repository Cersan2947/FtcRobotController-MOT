package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import static org.firstinspires.ftc.teamcode.RobotValues.*;


public class RobotDrive {

    DcMotorEx fl;
    DcMotorEx fr;
    DcMotorEx bl;
    DcMotorEx br;
    IMU imu;
    static double headingOffset = 0;
    public DcMotorEx initDcMotor(HardwareMap hardwareMap, String name, DcMotor.Direction dir) {
        DcMotorEx m = hardwareMap.get(DcMotorEx.class, name);
        m.setDirection(dir);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        return m;
    }
    public void init(HardwareMap hardwareMap) {
        fl = initDcMotor(hardwareMap, "fl", LEFTDIR);
        fr = initDcMotor(hardwareMap, "fr", RIGHTDIR);
        bl = initDcMotor(hardwareMap, "bl", LEFTDIR);
        br = initDcMotor(hardwareMap, "br", RIGHTDIR);
        initIMU(hardwareMap);
    }
    public double getIMUHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public void setHeading(double h) {
        headingOffset = h - getIMUHeading();
    }

    public double getHeading() {
        return headingOffset + getIMUHeading();
    }


    public void initIMU(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters params = new IMU.Parameters(
            new RevHubOrientationOnRobot(LOGO_DIR, USB_DIR));
        imu.initialize(params);
    }

    public void driveXYW(double rx, double ry, double rw) {
         double denom = Math.max(
                      1,
                      // change 1 to whatever power you need for low power mode
                      (Math.abs(rx)+Math.abs(ry)+Math.abs(rw))
                  );

        double flPower = (rx - ry - rw) / denom;
        double frPower = (rx + ry + rw) / denom;
        double blPower = (rx + ry - rw) / denom;
        double brPower = (rx - ry + rw) / denom;
        

        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
        
    }
    public void driveFieldXYW(double fx, double fy, double fw) {
        // rotate field orientation to robot orientation
        double theta = Math.toRadians(getHeading());
        double rx = fx * Math.cos(-theta) - fy * Math.sin(-theta);
        double ry = fx * Math.sin(-theta) + fy * Math.cos(-theta);

        driveXYW(rx, ry, fw);
        }
    
}
