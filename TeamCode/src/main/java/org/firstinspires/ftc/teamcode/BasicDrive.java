package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic Drive Test")
public class BasicDrive extends LinearOpMode {

    public RobotDrive bot;

    @Override
    public void runOpMode() {
        bot = new RobotDrive();
        bot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
        double jx = -gamepad1.left_stick_y - gamepad1.right_stick_y;
            bot.driveXYW(jx, 0, 0);
        double jy = -gamepad1.left_stick_x;
            bot.driveXYW(jx, jy, 0);
        double jw = -gamepad1.right_stick_x;
            bot.driveXYW(jx, jy, jw);
            
        }
    }
}