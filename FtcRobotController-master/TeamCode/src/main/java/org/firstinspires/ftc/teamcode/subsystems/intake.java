package org.firstinspires.ftc.teamcode.subsystems;

public class intake {
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
}
