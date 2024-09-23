package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class Variables {
    double y = gamepad1.left_stick_y;
    double x = gamepad1.left_stick_x;
    double rx = gamepad1.right_stick_x;
    // do some fancy math I copied off the internet in order to keep power proportional

    double powerLimiter = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
    double powerFL;
    double powerFR;
    double powerBL;
    double powerBR;
}
