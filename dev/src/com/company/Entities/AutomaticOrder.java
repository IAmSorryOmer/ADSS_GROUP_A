package com.company.Entities;

import java.time.LocalDate;
import java.util.Map;

public class AutomaticOrder extends SingleProviderOrder {

    private int orderDays;

    public AutomaticOrder(Provider provider, String orderId, int orderDays) {
        super(provider, orderId, null);
        this.orderDays = orderDays;
    }

    public int getOrderDays() {
        return orderDays;
    }

    public void setOrderDays(int orderDays) {
        this.orderDays = orderDays;
    }
    public boolean isComingAtDay(int day){
        return (orderDays & (1 << day)) != 0;
    }
    public String stringifyArrivalDays(){
        String arr[] = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String result = "";
        for(int i = 0; i< 7;i++){
            if(isComingAtDay(i))
                result += arr[i] + ", ";
        }
        if(result.length() != 0)
            result = result.substring(0,result.length()-2);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("automatic order. come at days: ").append(stringifyArrivalDays())
                .append(", order details: ").append("\n");
        Provider provider = super.getProvider();
        stringBuilder.append("Provider name: ").append(provider.getName()).append(", Provider Id: ").append(provider.getProviderID()).
                append(".\nProvider address: ").append(provider.getCommunicationDetails().getAddress())
                .append(".\n").append("Order ID: ").append(super.getOrderID()).append(", contact phone: ").append(provider.getCommunicationDetails().getPhoneNum()).append(".\n");
        return stringBuilder.toString();
    }
}
