package BL;

import DAL.DDelivery;
import DAL.Mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Delivery {
    private static int idGenerator=0;
    private int id;
    private String date;
    private String dispatchTime;
    private String TID;
    private String DName;
    private String source;
    private int preWeight;
    private List<Destination> destinationList;
    private List<NumberedFile> files;

    public Delivery(String date, String dispatchTime, String TID, String DName, String source, int preWeight, List<Destination> destinationList, List<NumberedFile> file) {
        idGenerator++;
        this.id=idGenerator+1;
        this.date = date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        this.destinationList = destinationList;
        this.files=file;
    }


    public Delivery(DAL.DDelivery deliver) {
        this.date = deliver.date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        destinationList = new LinkedList<>();
        this.id=deliver.id;
    }

    public  void Load( Mapper mapper)
    {
       List<DAL.DDestination> destinations = mapper.loadDestinations(id);
       List<DAL.DNumberedFile> numberedFiles = mapper.loadNumberedFiles(id);
       for (int i = 0; i < destinations.size(); i++)
       {
           destinationList.add(new Destination(destinations.get(i)));
       }

        for (int i = 0; i < numberedFiles.size(); i++)
        {
            NumberedFile numberedFile = new NumberedFile(numberedFiles.get(i));
            files.add(numberedFile);
            numberedFile.load(id);

        }
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
    