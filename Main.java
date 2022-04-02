package ro.utcn.tudor;

import ro.utcn.tudor.business.SimulationManager;
import ro.utcn.tudor.model.Server;
import ro.utcn.tudor.model.Task;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        GUI frame = new GUI();
        SimulationManager simulationManager = new SimulationManager(frame);
        Controller controller = new Controller(frame, simulationManager);





    }
}
