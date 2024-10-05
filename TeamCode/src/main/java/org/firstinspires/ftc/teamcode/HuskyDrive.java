package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Basic Drive Test")
public class HuskyDrive extends LinearOpMode {

    public RobotDrive bot;
    public HuskyLens huskylens;

    @Override
    public void runOpMode() {
        bot = new RobotDrive();
        bot.init(hardwareMap);
        
        huskylens = hardwareMap.get(HuskyLens.class, "huskylens");
        huskylens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);

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
            
            int targetId = -1;
            if (gamepad1.x) targetId = 1;
            if (gamepad1.y) targetId = 2;
            
            HuskyLens.Block[] currentBlocks = huskylens.blocks();
            telemetry.addData("blocks.length", currentBlocks.length);

            for(HuskyLens.Block block : currentBlocks) {
                int center = block.left + block.width / 2;
                if (block.id == targetId) {
                    jw = (160 - center) * 0.004;
                }
                if (gamepad1.right_bumper) {
                        jx = (220 - block.top) * 0.002;
                }
                telemetry.addData("block", block.toString());
                
            telemetry.addData("Status", "Running");
            telemetry.update();
            }
            
        }
    }
}
