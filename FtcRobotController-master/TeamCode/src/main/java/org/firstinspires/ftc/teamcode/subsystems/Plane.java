package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Plane {
    public CRServo planeServo;

    public Plane(HardwareMap hm, String planename)
    {
        planeServo = hm.get(CRServo.class, planename);
    }

    public class RunPlane implements Action
    {
        private boolean initialized = false;
        private double power = 0.0;
        private double timeToRun = 0.0;
        private ElapsedTime runtime = new ElapsedTime();

        public RunPlane(double pwr, double time)
        {
            power = pwr;
            timeToRun = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                planeServo.setPower(power);
                runtime.reset();
                initialized = true;
            }

            if (runtime.milliseconds() >= timeToRun) {
                planeServo.setPower(0);
                return false;
            } else {                
                return true;
            }
        }
    }

    public Action runPlane(double power, double time) {
        return new RunPlane(power, time);
    }
}
