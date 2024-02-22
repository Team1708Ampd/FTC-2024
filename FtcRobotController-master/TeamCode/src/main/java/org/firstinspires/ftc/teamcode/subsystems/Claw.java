package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Claw {
    public CRServo wristServo;
    public CRServo clawServo;

    public class OpenClaw implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public OpenClaw(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                clawServo.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                clawServo.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }

    public Action openClaw(double power, double time) {
        return new OpenClaw(power, time);
    }

    public class MoveWrist implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public MoveWrist(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                wristServo.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                wristServo.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }

    public Action moveWrist(double power, double time) {
        return new MoveWrist(power, time);
    }
}