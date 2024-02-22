package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    public DcMotor intakeMotor;

    public CRServo intakeLeft;
    public CRServo intakeRight;

    public Intake(HardwareMap hm, String leftmotorname, String rightmotorname, String mainmotorname)
    {
        intakeLeft = hm.get(CRServo.class, leftmotorname);
        intakeRight = hm.get(CRServo.class, rightmotorname);
        intakeMotor = hm.get(DcMotor.class, mainmotorname);

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class SetMainIntake implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public SetMainIntake(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intakeMotor.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                intakeMotor.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }

    public Action setMainIntake (double power, double time) {
        return new SetMainIntake(power, time);
    }

    public class SetLRIntake implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public SetLRIntake(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                intakeLeft.setPower(power);
                intakeRight.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }
    public Action setLRIntake (double power, double time) {
        return new SetLRIntake(power, time);
    }
}
