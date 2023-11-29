package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.teamcode.robostate;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Main Auto OPMode")
public class MainTeleopOpMode extends OpMode {

    private RoboStateManager _STATE_MANAGER_;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        // Init the State Manager
        _STATE_MANAGER_ = new RoboStateManager();

        // Add all states 
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

        // Check to see if we have any states to run
        if (_STATE_MANAGER_.MoreStates())
        {
            // Start execution of the first state
            _STATE_MANAGER_.StartStateExecute();
        }
        else
        {
            // Do nothing
        }        
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        // Check to see if the current state is finished executing
        if (_STATE_MANAGER_.IsStateFinished())
        {
            // Get and execute the next state if available
            // Execution will be stopped if no new state is available
            _STATE_MANAGER_.NextState();
        }
        else
        {
            _STATE_MANAGER_.ExecuteActive();
        }      
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

        // Stop execution of all states
        _STATE_MANAGER_.EndExecute();
    }
}
