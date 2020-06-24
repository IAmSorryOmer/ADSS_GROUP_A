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
    private String address;
    private String orderId;

    private String returnHour;

    public Delivery(LocalDate date, String dispatchTime, String TID, String DName, String source, int preWeight, String address,String orderId) {

        this.id=idGenerator+1;
        this.address=address;
        this.date = date;
        this.dispatchTime = dispatchTime;
        this.TID = TID;
        this.DName = DName;
        this.source = source;
        this.preWeight = preWeight;
        this.orderId=orderId;
    }



    public Delivery(DAL.DDelivery deliver) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//2005-nov-12
        LocalDate date = LocalDate.parse(deliver.getDate(), formatter);
        this.date =  date;
        this.dispatchTime = deliver.getDispatchTime();
        this.TID = deliver.getTID();
        this.DName = deliver.getDName();
        this.source = deliver.getSource();
        this.preWeight = deliver.getPreWeight();
        this.address=deliver.getAddress();
        this.orderId=deliver.getOrderId();
        this.id=deliver.getId();
        if(id>=deliver.getId()){
            idGenerator=deliver.getId()+1;
        }
    }

    public  void Load( Mapper mapper)
    {
      // List<DAL.DDestination> destinations = mapper.loadDestinations();



    }

    public int getId() {
        return id;
    }

    public static int getIdGenerator()
    {
        return idGenerator;
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

    public String getAddress(){return  address;}




//Final version

}
    