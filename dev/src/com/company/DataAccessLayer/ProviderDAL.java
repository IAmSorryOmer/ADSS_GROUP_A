package com.company.DataAccessLayer;

import com.company.Entities.Category;
import com.company.Entities.CommunicationDetails;
import com.company.Entities.Provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProviderDAL {
    public static final String providerTable = "Provider";
//    public static final String providerId = "ProviderId";
//    public static final String name = "Name";
//    public static final String creditCardNumber = "CreditCardNumber";
//    public static final String needsTransport = "NeedsTransport";
//    public static final String delayDays = "DelayDays";
//    public static final String arrivalDays = "ArrivalDays";


    public static HashMap<String, Provider> mapper = new HashMap<>();
    private static boolean updated = false;

    public static void insertProvider(Provider provider){
        String sql = "INSERT INTO Provider(ProviderId, Name, CreditCardNumber, NeedsTransport, DelayDays, ArrivalDays) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, provider.getProviderID());
            preparedStatement.setString(2, provider.getName());
            preparedStatement.setString(3, provider.getCreditCardNumber());
            preparedStatement.setInt(4, provider.isNeedsTransport()?1:0);
            preparedStatement.setInt(5, provider.getDelayDays());
            preparedStatement.setInt(6, provider.getArrivalDays());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql2 = "Insert into CommunicationDetails(Adress, PhoneNumber, IsFixedDays, ProviderId) values (?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql2);
            preparedStatement.setString(1, provider.getCommunicationDetails().getAddress());
            preparedStatement.setString(2, provider.getCommunicationDetails().getPhoneNum());
            preparedStatement.setInt(3, provider.getCommunicationDetails().getIsFixedDays() ? 1:0);
            preparedStatement.setString(4, provider.getProviderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        mapper.put(provider.getProviderID(), provider);
    }
    public static void updateProvider(Provider provider){
        String sql = "update Provider set Name = ?, CreditCardNumber = ?, NeedsTransport = ?, DelayDays = ?, ArrivalDays = ? where ProviderId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, provider.getName());
            preparedStatement.setString(2, provider.getCreditCardNumber());
            preparedStatement.setInt(3, provider.isNeedsTransport() ? 1 : 0);
            preparedStatement.setInt(4, provider.getDelayDays());
            preparedStatement.setInt(5, provider.getArrivalDays());
            preparedStatement.setString(6, provider.getProviderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String sql2 = "update CommunicationDetails set Adress = ?, PhoneNumber = ?, IsFixedDays = ? where ProviderId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql2);
            preparedStatement.setString(1, provider.getCommunicationDetails().getAddress());
            preparedStatement.setString(2, provider.getCommunicationDetails().getPhoneNum());
            preparedStatement.setInt(3, provider.getCommunicationDetails().getIsFixedDays() ? 1 : 0);
            preparedStatement.setString(4, provider.getProviderID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Provider getProviderById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "SELECT * FROM Provider join CommunicationDetails on CommunicationDetails.ProviderId = Provider.ProviderId WHERE Provider.ProviderId = ?;";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<Provider> result = resultSetToProviders(preparedStatement.executeQuery());
                if(result.size() == 0)
                    return null;
                return result.get(0);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public static List<Provider> loadAll(){
        String sql = "SELECT * FROM Provider join CommunicationDetails on CommunicationDetails.ProviderId = Provider.ProviderId;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            List<Provider> toReturn = updated? new LinkedList<>(mapper.values()) : resultSetToProviders(preparedStatement.executeQuery());
            updated = true;
            return toReturn;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    private static List<Provider> resultSetToProviders(ResultSet resultSet) throws SQLException{
        List<Provider> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String providerId = resultSet.getString("ProviderId");
            String name = resultSet.getString("Name");
            String creditCardNumber = resultSet.getString("CreditCardNumber");
            boolean needsTransport = resultSet.getInt("NeedsTransport") == 1;
            int delayDays = resultSet.getInt("DelayDays");
            int arrivalDays = resultSet.getInt("ArrivalDays");
            String adress = resultSet.getString("Adress");
            String phoneNumber = resultSet.getString("PhoneNumber");
            boolean fixedDays = resultSet.getInt("IsFixedDays") == 1;
            CommunicationDetails communicationDetails = new CommunicationDetails(null, fixedDays, phoneNumber, adress, null);
            Provider provider = new Provider(providerId, creditCardNumber, needsTransport, delayDays, arrivalDays, name, communicationDetails);
            communicationDetails.setProvider(provider);
            if(mapper.containsKey(providerId)){
                toReturn.add(mapper.get(providerId));
            }
            else {
                mapper.put(provider.getProviderID(), provider);
                toReturn.add(provider);
            }
        }
        return toReturn;
    }
}
