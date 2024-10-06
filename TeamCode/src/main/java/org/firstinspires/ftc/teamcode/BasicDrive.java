package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Base Drive")
public class BasicDrive extends LinearOpMode {

    public RobotDrivePm bot;

    @Override
    public void runOpMode() {
        bot = new RobotDrivePm();
        bot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double jx = -gamepad1.left_stick_y - gamepad1.right_stick_y;
            double jy = -gamepad1.left_stick_x;
            double jw = -gamepad1.right_stick_x;

            bot.driveXYW(jx, jy, jw);

        }
    }
}
