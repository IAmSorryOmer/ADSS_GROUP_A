package BL;

import DAL.DDelivery;
import DAL.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Delivery {
    private static int idGenerator=-1;
    private int id;
    private LocalDate date;
    private String dispatchTime;
    private String TID;
    private String DName;
    private String source;
    private int preWeight;
    private List<Destination> destinationList;
    private List<NumberedFile> files;
    private String returnHour;

    public Delivery(LocalDate date, String dispatchTime, String TID, String DName, String source, int preWeight, List<Destination> destinationList, List<NumberedFile> file) {

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

        this.files = new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//2005-nov-12
        LocalDate date = LocalDate.parse(deliver.getDate(), formatter);
        this.date =  date;
        this.dispatchTime = deliver.getDispatchTime();
        this.TID = deliver.getTID();
        this.DName = deliver.getDName();
        this.source = deliver.getSource();
        this.preWeight = deliver.getPreWeight();
        destinationList = new LinkedList<>();
        this.id=deliver.getId();
        this.returnHour = deliver.getReturnHour();
        if(id>=deliver.getId()){
            idGenerator=deliver.getId()+1;
        }
    }

    public  void Load( Mapper mapper)
    {
        this.files = new LinkedList<>();
      // List<DAL.DDestination> destinations = mapper.loadDestinations();
       List<DAL.DNumberedFile> numberedFiles = mapper.loadNumberedFiles(id);


        for (int i = 0; i < numberedFiles.size(); i++)
        {
            NumberedFile numberedFile = new NumberedFile(numberedFiles.get(i));
            files.add(numberedFile);
            numberedFile.load(id,mapper);
        }
        for (int j = 0; j < numberedFiles.size(); j++) {

            for (Destination destination : DestController.getInstance().getDestinations()) {
                if (numberedFiles.get(j).getAddress().equals(destination.getAddress())) {
                    destinationList.add(destination);
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public static int getIdGenerator()
    {
        return idGenerator;
    }
    public List<NumberedFile> getFiles() {
        return files;
    }


    public LocalDate getDate() {
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
    