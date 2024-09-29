package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Variables {
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;

    // do some fancy math I copied off the internet in order to keep power proportional
    double powerLimiter = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
    // do some fancier math to allow the robot to rotate and
    double powerFL = y + x + rx / powerLimiter;
    double powerFR = y - x - rx / powerLimiter;
    double powerBL = y - x + rx / powerLimiter;
    double powerBR = y + x - rx / powerLimiter;
}
