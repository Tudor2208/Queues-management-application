package ro.utcn.tudor.business;

import ro.utcn.tudor.model.Server;
import ro.utcn.tudor.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private final List<Server> servers;
    private Strategy strategy;

    public Scheduler(int nrOfServers, Strategy strategy) {
        servers = new ArrayList<>();
        this.strategy = strategy;

        for (int i = 0; i < nrOfServers; i++) {
            Server s = new Server();
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }

    public void changePolicy(StrategyPolicy policy) {
        switch (policy) {
            case SHORTEST_TIME -> {
                strategy = new TimeStrategy();
            }
            case SHORTEST_QUEUE -> {
                strategy = new ShortestQueueStrategy();
            }
            default -> strategy = new ShortestQueueStrategy();
        }
    }

    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
