package DAL;

public class DDelivery {
    private int id;
    private String date;
    private String dispatchTime;
    private String TID;
    private String DName;
    private String source;
    private int preWeight;
    private String returnHour;

    public DDelivery(int id, String date, String dispatchTime, String TID, String DName, String source, int preWeight,String returnHour) {
        this.id = id;
        this.date = date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        this.returnHour = returnHour;
    }

    public String getReturnHour() {
        return returnHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPreWeight() {
        return preWeight;
    }

    public void setPreWeight(int preWeight) {
        this.preWeight = preWeight;
    }

}