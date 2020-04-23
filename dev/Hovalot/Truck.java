public class Truck {

    private String id;
    private int weight;
    private int maxWeight ;
    private String model;
    public Truck(String id, int weight, int maxWeight, String model) {
        this.id = id;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.model = model;
    }

    public String getId() {
        return id;
    }


    public int getWeight() {
        return weight;
    }


    public int getMaxWeight() {
        return maxWeight;
    }


    public String getModel() {
        return model;
    }



}

//Final version