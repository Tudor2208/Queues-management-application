package ro.utcn.tudor.business;

import ro.utcn.tudor.model.Server;
import ro.utcn.tudor.model.Task;

import java.util.List;

public interface Strategy {

    void addTask(List<Server> servers, Task task);
}
