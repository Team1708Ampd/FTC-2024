package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climb {
    public DcMotor leftClimb;
    public DcMotor rightClimb;

    public void setPower(double power) {
        leftClimb.setPower(power);
        rightClimb.setPower(power);
    }
}
