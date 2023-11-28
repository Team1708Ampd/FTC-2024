package org.firstinspires.teamcode.robostate;



// Generic class representing a robotic state task 

public abstract class RoboState {

    String _stateName = "";
    boolean _stateActive;

    public RoboState(String name)
    {
        _stateName = name;
        _stateActive = false;
    }

    public abstract void init();
    public abstract void execute();
    public abstract bool isfinished();    
}


