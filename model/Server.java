package ro.utcn.tudor.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        tasks.add(task);
        waitingPeriod.addAndGet(task.getServiceTime());
    }

    @Override
    public void run() {
        while (true) {
            Task currTask = tasks.peek();
            try {
                if (currTask != null) {
                    Thread.sleep(currTask.getServiceTime() * 1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tasks.remove(currTask);
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Waiting time: ");
        builder.append(waitingPeriod);
        builder.append("\n");
        if (tasks.size() != 0) {
            builder.append(tasks.toString());
        } else {
            builder.append("Closed");
        }
        return builder.toString();
    }
}
