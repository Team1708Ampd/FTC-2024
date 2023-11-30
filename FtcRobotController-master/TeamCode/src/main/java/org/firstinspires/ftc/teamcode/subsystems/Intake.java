package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake extends LinearOpMode {
    private DcMotor intakeMotor = hardwareMap.get(DcMotor.class,"Intake Motor");

    CRServo intakeleft = hardwareMap.get(CRServo.class,"Left Intake");
    CRServo intakeright = hardwareMap.get(CRServo.class,"Right Intake");
    public void setpower (double power) {
        intakeMotor.setPower(power);
    }
    public void setServopower (double power) {
        intakeleft.setPower(power);
        intakeright.setPower(power);
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
