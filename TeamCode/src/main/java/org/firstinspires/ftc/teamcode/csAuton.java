package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class csAuton extends LinearOpMode {
    public VisionControl bot;

    @Override
    public void runOpMode(){

        bot = new VisionControl();
        bot.init(hardwareMap);

        telemetry.addData("Status","Initialized");
        telemetry.update();
        // Wait for driver to press play
        waitForStart();

        // Robot starts in tile F4 with an initial heading of 90 degrees
        bot.setHeading(90);
        bot.setFieldXY(12, -60);

        VisionControl.Pose[] path = new VisionControl.Pose[]{
                new VisionControl.Pose(12, -36),
                new VisionControl.Pose(20,-36),
                new VisionControl.Pose(20, -56)
        };
        followPath(path);
        // loop until the driver presses stop
        while (opModeIsActive()){
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
    public void followPath(VisionControl.Pose[] path){
        int i = 0;
        while (i < path.length){
            if(!opModeIsActive()) return;
            bot.updateTracking();
            double dist = bot.driveToPose(path [i], 0.25);
            telemetry.addData("path target", i);
            telemetry.addData("target pose", path[i]);
            telemetry.addData("field pose", bot.field);
            telemetry.addData("dist", dist);
            telemetry.update();
            if (dist < 1.5)i++;
        }
        bot.driveXYW(0,0,0);
    }

}
