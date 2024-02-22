package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Elevator {
    public DcMotor leftElevator;
    public DcMotor rightElevator;

    public Elevator(HardwareMap hm, String leftmotorname, String rightmotorname)
    {
        leftElevator = hm.get(DcMotor.class, leftmotorname);
        rightElevator = hm.get(DcMotor.class, rightmotorname);

        leftElevator.setDirection(DcMotorSimple.Direction.REVERSE);
        leftElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public class MoveElevator implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public MoveElevator(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                leftElevator.setPower(power);
                rightElevator.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                leftElevator.setPower(0);
                rightElevator.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }

    public Action moveElevator(double power, double time) {
        return new MoveElevator(power, time);
    }
}
