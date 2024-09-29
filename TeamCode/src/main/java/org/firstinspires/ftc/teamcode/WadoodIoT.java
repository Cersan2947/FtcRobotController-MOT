package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class WadoodIoT extends LinearOpMode {

    private DcMotorEx fr;
    private DcMotorEx br;
    private DcMotorEx fl;
    private DcMotorEx bl;

    public void runOpMode() {
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");
        br = hardwareMap.get(DcMotorEx.class, "br");
        bl = hardwareMap.get(DcMotorEx.class, "bl");

        br.setDirection(DcMotorEx.Direction.REVERSE);
        fl.setDirection(DcMotorEx.Direction.REVERSE);
        bl.setDirection(DcMotorEx.Direction.FORWARD);
        fr.setDirection(DcMotorEx.Direction.REVERSE);
        // Reset encoders
        fl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Set to run without encoders
        fl.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            double lefty;
            double leftx;
            double rightx;

            // Driving joystick
            lefty = gamepad1.left_stick_y;
            rightx = gamepad1.left_stick_x;
            leftx = gamepad1.right_stick_x;

            fl.setPower((1 * -lefty) - (leftx * -0.5) + -rightx);
            br.setPower((0.925 * lefty) + (leftx * -0.425) - rightx);
            fr.setPower((0.925 * lefty) + (leftx * 0.425) - rightx);
            bl.setPower((1 * lefty) - (-leftx * 0.5) + rightx);

            if (gamepad1.right_bumper) {
                fl.setPower(0.5 * fl.getPower());
                bl.setPower(0.5 * bl.getPower());
                fr.setPower(0.5 * fr.getPower());
                br.setPower(0.5 * br.getPower());
            }

            // Telemetry
            telemetry.addData("FR encoder pos:", fr.getCurrentPosition());
            telemetry.addData("BR encoder pos:", br.getCurrentPosition());
            telemetry.addData("FL encoder pos:", fl.getCurrentPosition());
            telemetry.addData("BL encoder pos:", bl.getCurrentPosition());
            telemetry.update();
        }
    }
}
