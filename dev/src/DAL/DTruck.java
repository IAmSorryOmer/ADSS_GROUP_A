package DAL;

public class DTruck {
    private String id;
    private int weight;
    private int maxWeight;
    private String model;

    public DTruck(String id, int weight, int maxWeight, String model) {
        this.id = id;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}