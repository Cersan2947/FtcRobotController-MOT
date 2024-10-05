// Copyright 2024 Patrick R. Michaud

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Concept: Logger", group = "Concept")

public class ConceptLogger extends LinearOpMode {

    Logger log;
    IMU imu;
    VoltageSensor battery;
    
    @Override
    public void runOpMode() {

        battery = hardwareMap.get(VoltageSensor.class, "Control Hub");
        
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters params = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(params);
        
        log = new Logger();
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        log.open();
        int loopCounter = 0;
        log.add("batt").add("loop").add("yaw").add("pitch").add("roll").headerLine();

        while (opModeIsActive()) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            loopCounter++;
            log.add(battery)
               .add(loopCounter)
               .add(orientation.getYaw(AngleUnit.DEGREES))
               .add(orientation.getPitch(AngleUnit.DEGREES))
               .add(orientation.getRoll(AngleUnit.DEGREES))
               .tsLine();

            telemetry.addData("Status", "Running");
            telemetry.addData("loopCounter", loopCounter);
            telemetry.addData("orientation", orientation.toString());
            telemetry.update();
        }
        log.close();
    }
}
