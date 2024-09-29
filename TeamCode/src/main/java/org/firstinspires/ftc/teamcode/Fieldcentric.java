package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUNew;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class Fieldcentric extends LinearOpMode {
    private DcMotorEx fr;
    private DcMotorEx br;
    private DcMotorEx fl;
    private DcMotorEx bl;



    BNO055IMU imu;

    Orientation angles = new Orientation();
    double initYaw;
    double adjustedYaw;

    public void runOpMode() {

        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        bl = hardwareMap.get(DcMotorEx.class, "bl");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC,
                AxesOrder.ZYX, AngleUnit.DEGREES);
        initYaw = angles.firstAngle;
        double zerodYaw = -initYaw + angles.firstAngle;
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double theta = Math.atan2(y, x) * 180 / Math.PI; // angle of gamepad
        double realTheta;
        realTheta = (360 - zerodYaw) + theta;
        double power = Math.hypot(x, y);

        double sin = Math.sin((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realTheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxSincos = Math.max(Math.abs(sin), Math.abs(cos));

        double flPower = (power * cos / maxSincos + turn);
        double frPower = (power * sin / maxSincos - turn);
        double blPower = (power * sin / maxSincos + turn);
        double brPower = (power * cos / maxSincos - turn);
        double brPowertest2 = (power * cos / maxSincos - turn);





        waitForStart();

        while (opModeIsActive()) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            adjustedYaw = angles.firstAngle - initYaw;


            if ((power + Math.abs(turn)) > 1) {
                flPower /= power + turn;
                frPower /= power - turn;
                blPower /= power + turn;
                brPower = power - turn;

                fl.setPower(flPower);
                fr.setPower(frPower);
                bl.setPower(blPower);
                br.setPower(brPower);

            }

        }
    }
}



