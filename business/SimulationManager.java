package ro.utcn.tudor.business;

import ro.utcn.tudor.GUI;
import ro.utcn.tudor.model.Server;
import ro.utcn.tudor.model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int nrOfServers;
    private int nrOfTasks;

    private int minArrivalTime;
    private int maxArrivalTime;

    private int minServiceTime;
    private int maxServiceTime;

    private int waitingTimeTotal = 0;
    private int peekHour = 0;
    private int maxWaitingTime = 0;
    private double averageServiceTime = 0;

    private Scheduler scheduler;
    private final BlockingQueue<Task> tasks;
    private final Strategy simStrategy = new TimeStrategy();
    private final GUI myGUI;

    public SimulationManager(GUI myGUI) {
        this.myGUI = myGUI;
        tasks = new LinkedBlockingQueue<>();

    }

    @Override
    public void run() {
        int currentTime = 0;
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("current_log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (currentTime < timeLimit) {

            assert myWriter != null;
            printQueues(currentTime, myWriter);
            for (Task task : tasks) {
                if (task.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(task);
                    tasks.remove(task);
                }
            }

            updateTime(currentTime);
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!workingServers() && tasks.isEmpty()) {
                printQueues(currentTime, myWriter);
                break;
            }
        }
        double averageWaitingTime = (double) waitingTimeTotal / (currentTime * nrOfServers);
        DecimalFormat df = new DecimalFormat("0.00");

        try {
            myGUI.addText("\n");
            myGUI.addText("Average service time: " + df.format(averageServiceTime) + "\n");
            myGUI.addText("Average waiting time: " + df.format(averageWaitingTime) + "\n");
            myGUI.addText("Peek hour: " + peekHour + "\n");

            assert myWriter != null;
            myWriter.write("\n");
            myWriter.write("Average service time: " + df.format(averageServiceTime) + "\n");
            myWriter.write("Average waiting time: " + df.format(averageWaitingTime) + "\n");
            myWriter.write("Peek hour: " + peekHour);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingTimeTotal = 0;

    }

    private void printQueues(int currentTime, FileWriter myWriter) {

        try {
            myGUI.addText("TIME " + currentTime + "\n");
            myWriter.write("TIME " + currentTime + "\n");

            if (!tasks.isEmpty()) {
                myGUI.addText("Waiting tasks: " + tasks + "\n");
                myWriter.write("Waiting tasks: " + tasks + "\n");
            }
            for (int i = 0; i < nrOfServers; i++) {
                myGUI.addText("Queue " + i + ": ");
                myWriter.write("Queue " + i + ": ");

                myGUI.addText(scheduler.getServers().get(i).toString() + "\n");
                myWriter.write(scheduler.getServers().get(i).toString());
                myWriter.write("\n");

            }
            myWriter.write("\n");
            myGUI.addText("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateTime(int currentTime) {
        List<Server> servers = scheduler.getServers();
        servers.forEach(s -> {
            AtomicInteger waitingPeriod = s.getWaitingPeriod();
            if (waitingPeriod.get() > maxWaitingTime) {
                maxWaitingTime = waitingPeriod.get();
                peekHour = currentTime + 1;
            }
            waitingTimeTotal += waitingPeriod.get();
            if (waitingPeriod.get() != 0) {
                waitingPeriod.getAndDecrement();
            }
            BlockingQueue<Task> tasks = s.getTasks();
            Task peek = tasks.peek();
            if (peek != null) {
                peek.setServiceTime(peek.getServiceTime() - 1);
                if (peek.getServiceTime() == 0) {
                    tasks.remove(peek);
                }
            }

        });
    }

    private boolean workingServers() {
        List<Server> servers = scheduler.getServers();
        for (Server server : servers) {
            if (!server.getTasks().isEmpty()) return true;
        }

        return false;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setNrOfServers(int nrOfServers) {
        this.nrOfServers = nrOfServers;
    }

    public void setNrOfTasks(int nrOfTasks) {
        this.nrOfTasks = nrOfTasks;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public void generateTasks() {
        Random rand = new Random();
        double sum = 0;
        for (int i = 0; i < nrOfTasks; i++) {
            int arrTime = minArrivalTime + rand.nextInt(maxArrivalTime - minArrivalTime);
            int serviceTime = minServiceTime + rand.nextInt(maxServiceTime - minServiceTime);
            sum += serviceTime;
            tasks.add(new Task(i + 1, arrTime, serviceTime));
        }

        averageServiceTime = (double) sum / nrOfTasks;
    }

    public void initScheduler() {
        scheduler = new Scheduler(nrOfServers, simStrategy);
    }
}
