package ro.utcn.tudor;

import ro.utcn.tudor.business.SimulationManager;
import ro.utcn.tudor.business.StrategyPolicy;

public class Controller {

    public Controller(GUI myGUI, SimulationManager simulationManager) {

        myGUI.addBtnSimulateAL(al -> {
            myGUI.setText("");
            String txtClients = myGUI.getTxtClients();
            String txtQueues = myGUI.getTxtQueues();
            String txtSimTime = myGUI.getTxtSimTime();
            String txtMinArrTime = myGUI.getMinArrTime();
            String txtMaxArrTime = myGUI.getMaxArrTime();
            String txtMinServiceTime = myGUI.getMinServiceTime();
            String txtMaxServiceTime = myGUI.getMaxServiceTime();
            String txtPolicy = (String) myGUI.getPolicy();

            try{
                int clients = Integer.parseInt(txtClients);
                int queues = Integer.parseInt(txtQueues);
                int simTime = Integer.parseInt(txtSimTime);
                int minArrTime = Integer.parseInt(txtMinArrTime);
                int maxArrTime = Integer.parseInt(txtMaxArrTime);
                int minServiceTime = Integer.parseInt(txtMinServiceTime);
                int maxServiceTime = Integer.parseInt(txtMaxServiceTime);
                StrategyPolicy policy;

                switch(txtPolicy){
                    case "Shortest Queue" -> {
                        policy = StrategyPolicy.SHORTEST_QUEUE;
                    }

                    case "Shortest waiting time" -> {
                        policy = StrategyPolicy.SHORTEST_TIME;
                    }

                    default -> {
                        policy = StrategyPolicy.SHORTEST_TIME;
                    }
                }

                simulationManager.setNrOfServers(queues);
                simulationManager.initScheduler();
                simulationManager.getScheduler().changePolicy(policy);
                simulationManager.setNrOfTasks(clients);
                simulationManager.setMinArrivalTime(minArrTime);
                simulationManager.setMaxArrivalTime(maxArrTime);
                simulationManager.setMinServiceTime(minServiceTime);
                simulationManager.setMaxServiceTime(maxServiceTime);
                simulationManager.setTimeLimit(simTime);

                Thread t = new Thread(simulationManager);
                simulationManager.generateTasks();

                t.start();


            } catch(NumberFormatException ex) {
                myGUI.displayMessage("Introdu numai date numerice!");
            }

        });
    }
}
