package com.company.LogicLayer;

import com.company.Entities.AutomaticOrder;
import com.company.Entities.SingleProviderOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class OrderTask extends TimerTask {
    private AutomaticOrder automaticOrder;

    public OrderTask(AutomaticOrder automaticOrder) {
        this.automaticOrder = automaticOrder;
    }

    @Override
    public void run() {
        LocalDate nextTimerDate = SingleProviderOrderController.getNextAutoOrderDate(automaticOrder);
        long time = LocalDateTime.now().until(nextTimerDate.atStartOfDay(), ChronoUnit.HOURS);//TODO change to millis
        Timer nextTimer = new Timer();
        System.out.println("scheduling order to " + LocalDateTime.now().plus(time, ChronoUnit.SECONDS).toString());
        nextTimer.schedule(new OrderTask(automaticOrder), time*1000);
        SingleProviderOrder singleProviderOrder = new SingleProviderOrder(UUID.randomUUID().toString(), automaticOrder.getProvider(), new HashMap<>(automaticOrder.getOrderItems()), LocalDate.now());
        try {
            SingleProviderOrderController.SingleProviderOrderCreator(singleProviderOrder, automaticOrder.getProvider().getProviderID());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("there was a problem while creating order of automatic order number " + automaticOrder.getOrderID());
        }
        try {
            //TODO maybe dont update automatically
            ProductController.handleOrder(singleProviderOrder);
        }
        catch (Exception e) {
            System.out.println("there was a problem while handling items recieved from automatic order number " + automaticOrder.getOrderID());
        }
    }
}
