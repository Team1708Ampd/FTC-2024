package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Plane;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Main Teleop Mode", group = "2024")
public class MainTeleopOpMode extends LinearOpMode {

    private DcMotor frontRightDrive;
    private DcMotor backRightDrive;
    private DcMotor frontLeftDrive;
    private DcMotor backLeftDrive;
    private Intake intakeSub = new Intake();
    private Claw claw = new Claw();
    private Plane plane = new Plane();
    private Elevator elevator = new Elevator();

    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;

    private static final boolean USE_WEBCAM = true;  // Set true to use a webcam, or false for a phone camera
    @Override
    public void runOpMode() {
        initAprilTag();

        if (USE_WEBCAM)
            setManualExposure(6, 250);

        frontRightDrive = hardwareMap.get(DcMotor.class, "Front Right");
        backRightDrive = hardwareMap.get(DcMotor.class, "Back Right");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "Front Left");
        backLeftDrive = hardwareMap.get(DcMotor.class, "Back Left");

        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeSub.intakeMotor = hardwareMap.get(DcMotor.class, "Intake Motor");
        intakeSub.intakeLeft = hardwareMap.get(CRServo.class, "Left Intake");
        intakeSub.intakeRight = hardwareMap.get(CRServo.class, "Right Intake");
        intakeSub.intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        claw.clawServo = hardwareMap.get(CRServo.class, "Claw Servo");
        claw.wristServo = hardwareMap.get(CRServo.class, "Wrist Servo");

        plane.planeServo = hardwareMap.get(CRServo.class, "Plane");

        elevator.leftElevator = hardwareMap.get(DcMotor.class, "Left Elevator");
        elevator.rightElevator = hardwareMap.get(DcMotor.class, "Right Elevator");
        elevator.leftElevator.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.leftElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.rightElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double y, x, rx;

        waitForStart();
        while(opModeIsActive()) {
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            moveRobot(y, x);


            if(gamepad2.y) {
                claw.setWristServo(1);
            } else if(gamepad2.b) {
                claw.setWristServo(-1);
            } else {
                claw.setWristServo(0);
            }

            if(gamepad1.x) {
                intakeSub.setPower(1);
            } else if(gamepad1.a) {
                intakeSub.setPower(-1);
            } else {
                intakeSub.setPower(0);
            }

            if(gamepad2.left_bumper) {
                elevator.setPower(1);
            } else if (gamepad2.right_bumper) {
                elevator.setPower(-1);
            } else {
                elevator.setPower(0);
            }

            if(gamepad1.start) {
                plane.setPower(1);
            } else if(gamepad1.back) {
                plane.setPower(-1);
            } else {
                plane.setPower(0);
            }
        }
    }

    public void moveRobot(double y, double x) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x), 1);
        double frontLeftPower = (y + x) / denominator;
        double backLeftPower = (y - x) / denominator;
        double frontRightPower = (y - x) / denominator;
        double backRightPower = (y + x) / denominator;

        frontLeftDrive.setPower(frontLeftPower);
        backLeftDrive.setPower(backLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backRightDrive.setPower(backRightPower);
    }

    private void initAprilTag() {
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Create the vision portal by using a builder.
        if (USE_WEBCAM) {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
        } else {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(BuiltinCameraDirection.BACK)
                    .addProcessor(aprilTag)
                    .build();
        }
    }

    /*
     Manually set the camera gain and exposure.
     This can only be called AFTER calling initAprilTag(), and only works for Webcams;
    */
    private void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        // Set camera controls unless we are stopping.
        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure(exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }
}
