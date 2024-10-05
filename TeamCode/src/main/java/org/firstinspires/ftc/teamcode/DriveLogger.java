package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Base Drive")
public class DriveLogger extends LinearOpMode {

    public RobotDrive bot;
    public Logger log;

    @Override
    public void runOpMode() {
        bot = new RobotDrive();
        bot.init(hardwareMap);

        log = new Logger();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        log.open();
        log.add("loop").add("apress")
                .add("lf")
                .headerLine();
        int loopCount = 0;
        int logUntil = 0;

        while (opModeIsActive()) {
            loopCount++
            double jx = -gamepad1.left_stick_y - gamepad1.right_stick_y;
            double jy = -gamepad1.left_stick_x;
            double jw = -gamepad1.right_stick_x;

            boolean apress = gamepad1.a;
            if(apress){
                jx = 0.5;
                logUntil = loopCount + 100;
            }
            if (loopCount < logUntil){
                log.add(loopCount).add(apress)
                        .add(bot.fl.getCurrentPosition())
                        .tsLine();
            }
            bot.driveXYW(jx, jy, jw);

        }
    }
}
