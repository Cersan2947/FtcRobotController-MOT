package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;


public class VisionControl extends RobotDrive {
    
    public AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    public LED ledr;
    public LED ledg;

    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);
        initAprilTag(hardwareMap);
        ledr = hardwareMap.get(LED.class, "ledr");
        ledg = hardwareMap.get(LED.class, "ledg");
        ledr.enable(false);
        ledg.enable(true);
    }
    
    public void initAprilTag(HardwareMap hardwareMap) {
        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
    }
    public String format(AprilTagDetection detection) {
        double range = detection.ftcPose.range;
        double bearing = detection.ftcPose.bearing;
        double yaw = detection.ftcPose.yaw;
        double tagx = detection.metadata.fieldPosition.get(0);
        double tagy = detection.metadata.fieldPosition.get(1);
        double theta = Math.toRadians(getHeading() + bearing);
        double fx = tagx - Math.cos(theta) * range;
        double fy = tagy - Math.sin(theta) * range;
        return String.format("id=%d R=%.2f B=%.2f Y=%.2f\n   fx=%.2f fy=%.2f",
                             detection.id, range, bearing, yaw, fx, fy );
    }
    public boolean setManualExposure(LinearOpMode op,
                                     int exposureMS,
                                     int gain) {
        if (visionPortal == null) { return false; }
        while (!op.isStopRequested()
                   && (visionPortal.getCameraState()
                        != VisionPortal.CameraState.STREAMING)) {
            op.sleep(20);
        }
        if (!op.isStopRequested()) {
            ExposureControl exposureControl =
                visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                op.sleep(50);
            }
            exposureControl.setExposure((long)exposureMS,
                                        TimeUnit.MILLISECONDS);
            op.sleep(20);
            GainControl gainControl =
                visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            op.sleep(20);
            return (true);
        }
        return (false);
    }

}
//aka mini bot
