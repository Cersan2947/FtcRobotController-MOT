package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp

public class BaseTeleOp extends LinearOpMode {
    // I love motors
    // fl is the front left wheel
    // br is the back right wheel
    // y'all understand the rest fr
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    // make le variables
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;
    // do some fancy math I copied off the internet in order to keep power proportional
    double powerLimiter = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
    double powerFL;
    double powerFR;
    double powerBL;
    double powerBR;

    @Override
    public void runOpMode() {
        fl = hardwareMap.get(DcMotor.class, "FrontLeft");
        fr = hardwareMap.get(DcMotor.class, "FrontRight");
        bl = hardwareMap.get(DcMotor.class, "BackLeft");
        br = hardwareMap.get(DcMotor.class, "BackRight");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            powerFL = y + x + rx / powerLimiter;
            powerFR = y - x - rx / powerLimiter;
            powerBL = y - x + rx / powerLimiter;
            powerBR = y + x - rx / powerLimiter;
            fl.setPower(y + x + rx / powerLimiter);
            fr.setPower(y - x - rx / powerLimiter);
            bl.setPower(y - x + rx / powerLimiter);
            br.setPower(y + x - rx / powerLimiter);


            telemetry.update();
        }
    }
}
