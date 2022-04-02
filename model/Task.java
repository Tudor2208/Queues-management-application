package ro.utcn.tudor.model;

public class Task {
    private final int ID;
    private final int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(String.valueOf(ID));
        builder.append(", ");
        builder.append(String.valueOf(arrivalTime));
        builder.append(", ");
        builder.append(String.valueOf(serviceTime));
        builder.append(")");
        return builder.toString();
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
