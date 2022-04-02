package ro.utcn.tudor.business;

import ro.utcn.tudor.model.Server;
import ro.utcn.tudor.model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task task) {
        int min = Integer.MAX_VALUE;
        int serverIndex = 0, i = 0;
        for (Server server : servers) {
            if (server.getWaitingPeriod().get() < min) {
                min = server.getWaitingPeriod().get();
                serverIndex = i;
            }
            i++;
        }

        servers.get(serverIndex).addTask(task);

    }
}

