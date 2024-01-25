package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Claw {
    public CRServo wristServo;
    public CRServo clawServo;

    public void setClawServo(double power) {
        clawServo.setPower(power);
    }

    public void setWristServo(double power) {
        clawServo.setPower(power);
    }
}