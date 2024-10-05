package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;


@TeleOp(name="Basic Drive Test")
public class ATeleop extends LinearOpMode {

    public Minibot bot;
    public DualPad gpad;

    @Override
    public void runOpMode() {
        bot = new Minibot();
        bot.init(hardwareMap);
        bot.setManualExposure(this, 6, 120);
        
        gpad = new DualPad();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

         while (opModeIsActive()) {
            gpad.update(gamepad1, gamepad2);
            double jx = -gpad.left_stick_y - gpad.right_stick_y;
            double jy = -gpad.left_stick_x;
            double jw = -gpad.right_stick_x;
            if (gpad.start) {
                if (gpad.dpad_up) bot.setHeading(0);
            }
            int targetId = -1;
            if (gpad.x) targetId = 4;
            if (gpad.y) targetId = 5;
            if (gpad.b) targetId = 6;
            List<AprilTagDetection> currentDetections =
                    bot.aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetId) {
                    jw = detection.ftcPose.bearing * 0.02;
                    jx = (detection.ftcPose.range - 20) * 0.02;
            }    
                telemetry.addData("tag", bot.format(detection));
            }

            bot.driveFieldXYW(jx, jy, jw);
            
            telemetry.addData("Status", "Running");
            telemetry.addData("heading", bot.getHeading());
            telemetry.addData("IMU heading", bot.getIMUHeading());
            telemetry.update();
        }
    }
}
