
import java.util.List;
import java.util.Objects;

public class Delivery {
    private String date;
    private String dispatchTime;
    private String TID;
    private String DName;
    private String source;
    private int preWeight;
    private List<Destination> destinationList;
    private List<NumberedFile> files;

    public Delivery(String date, String dispatchTime, String TID, String DName, String source, int preWeight, List<Destination> destinationList, List<NumberedFile> file) {
        this.date = date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        this.destinationList = destinationList;
        this.files=file;
    }
    public List<NumberedFile> getFiles() {
        return files;
    }


    public String getDate() {
        return date;
    }


    public String getDispatchTime() {
        return dispatchTime;
    }


    public String getTID() {
        return TID;
    }


    public String getDName() {
        return DName;
    }


    public String getSource() {
        return source;
    }
    public int getPreWeight() {
        return preWeight;
    }


    public List<Destination> getDestinationList() {
        return destinationList;
    }

//Final version

}
    