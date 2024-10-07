package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@TeleOp(name="Cersan's Code of Craziness")
public class csFieldCentricRed extends LinearOpMode {

    public VisionControl bot;
    public csGamepad gpad;

    @Override
    public void runOpMode() {
        bot = new VisionControl();
        bot.init(hardwareMap);
        bot.setManualExposure(this, 6, 120);

        gpad = new csGamepad();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the driver to press play
        waitForStart();

        VisionControl.Pose targetPose = new VisionControl.Pose(24, 0);
        // runs until the driver presses stop
        while (opModeIsActive()) {
            gpad.update(gamepad1, gamepad2);
            bot.updateTracking();
            telemetry.addData("field pose", bot.field);
            telemetry.addData("targetPose", targetPose);
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
            boolean tagSeen = false;
            for (AprilTagDetection detection : currentDetections) {
                tagSeen = true;
                if (detection.id == targetId) {
                    jw = detection.ftcPose.bearing * 0.02;
                    jx = (detection.ftcPose.range - 20) * 0.02;
                }
                telemetry.addData("tag", bot.format(detection));
            }

            // use current pose as new target
            if(gpad.shift.back){
                targetPose = new VisionControl.Pose(bot.field);
            }

            if(gpad.back){
                bot.driveToPose(targetPose, 0.3);
            }
            else{
                // rot = rotation
                // make rot the direction the robot is facing
                // since this is for red, It'll be facing 270 after auton I think
                // if it doesn't, I'll change it ig
                bot.driveFieldXYW(jx, jy, jw, 270);
            }

            bot.ledg.enable(tagSeen);
            telemetry.addData("Status", "Running");
            telemetry.addData("heading", bot.getHeading());
            telemetry.addData("IMU heading", bot.getIMUHeading());
            telemetry.update();
        }
    }
}