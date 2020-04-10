import java.util.List;

public class Delivery {
    private String date;
    private String dispatchTime;
    private String TID;
    private String DName;
    private String source;
    private int preWeight;
    private List<NumberedFile> numberedFiles;

    public Delivery(String date, String dispatchTime, String TID, String DName, String source, int preWeight, List<NumberedFile> numberedFiles) {
        this.date = date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        this.numberedFiles = numberedFiles;
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

    public List<NumberedFile> getNumberedFiles() {
        return numberedFiles;
    }

    public void setNumberedFiles(List<NumberedFile> numberedFiles) {
        this.numberedFiles = numberedFiles;
    }

}
    