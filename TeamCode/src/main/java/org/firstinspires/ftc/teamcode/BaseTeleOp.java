package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp

public class BaseTeleOp extends LinearOpMode {
    Variables v;
    v = new v();

    // I love motors
    // fl is the front left wheel
    // br is the back right wheel
    // y'all understand the rest fr
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;


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
            fl.setPower(v.powerFL);
            fr.setPower(v.powerFR);
            bl.setPower(v.powerBL);
            br.setPower(v.powerBR);


            telemetry.update();
        }
    }
}
