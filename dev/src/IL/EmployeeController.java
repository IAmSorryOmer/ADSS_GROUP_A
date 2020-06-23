package IL;

import BL.*;
import DAL.Mapper;
import Entities.Pair;
import Entities.Provider;
import Entities.SingleProviderOrder;

import java.time.LocalDate;
import java.util.List;

public class EmployeeController {

    public Employee activeUserEmployee;

    private Store store;

    private static EmployeeController instance = null;

    public static EmployeeController getInstance()
    {
        if (instance == null)
        {
            instance = new EmployeeController();
        }
        return  instance;
    }

//    public void  setActiveUserEmployee(Employee employee)
//    {
//        this.activeUserEmployee = employee;
//    }

    public boolean hasActiveUserAssignedShifts()
    {
        return activeUserEmployee.getHasAssignedShifts();
    }

    public String getActiveName()
    {
        return activeUserEmployee.getName();
    }

    public String watchMyShifts()
    {
        String shifts = "";
        for (int i = 0; i < 7; i++)
        {
            for (String role : store.getSchedule().getDays()[i].getMorning().keySet())
            {
                for (int j=0; j <store.getSchedule().getDays()[i].getMorning().get(role).length; j++)
                {
                    if (store.getSchedule().getDays()[i].getMorning().get(role)[j] == activeUserEmployee.getID())
                       shifts += "in day " + (i+1) + " morning shift as: " + role +"\n";
                }
            }

            for (String role : store.getSchedule().getDays()[i].getEvening().keySet())
            {
                for (int j=0; j <store.getSchedule().getDays()[i].getEvening().get(role).length; j++)
                {
                    if (store.getSchedule().getDays()[i].getEvening().get(role)[j] == activeUserEmployee.getID())
                        shifts += "in day " + (i+1) + " evening shift as: " + role +"\n";
                }
            }

        }
        if (shifts.equals(""))
            shifts = "no shifts at the moment";
        return shifts;
    }

    public boolean connect(int id,int store_num)
    {
        store = ManagerController.connect(id,store_num);
        if (store != null) {
            activeUserEmployee = store.getEmployeeById(id);
            return true;
        }
        else
            return false;
    }

    public String enterMyCapableShifts(int [] shifts_Num)
    {
        if (activeUserEmployee.getHasAssignedShifts())
        {
            return "you already assigned shifts for this week";
        }
        String result = Validation.checkSelectedShifts(shifts_Num);
        if (result.equals("success")) {
            String[] myShifts = new String[7];

            for (int i = 0; i < shifts_Num.length; i++) {
                if (shifts_Num[i] % 2 == 0) {
                    myShifts[(shifts_Num[i] - 1) / 2] += "evening";
                } else {
                    myShifts[shifts_Num[i] / 2] += "morning";
                }
            }

            activeUserEmployee.setCapable_shifts(myShifts);
            ManagerController.saveCapableShifts(activeUserEmployee);

        }
        return result;
    }

// assignment 3
    public boolean isStorage(){
        return activeUserEmployee.isStorage();
    }
    public void SingleProviderOrderCreator(SingleProviderOrder singleProviderOrder, String providerId) {
        SingleProviderOrderController.SingleProviderOrderCreator(singleProviderOrder, providerId);
        Provider p=ProviderController.getProvierByID(providerId);
        if(p.isNeedsTransport()){
            Pair<Integer[], LocalDate> info=store.findDateForOrder();
            if(info==null){
                //TODO Send message to human resource manager
            }
            //add details to order
        }

    }

    //ORDER INTERFACE


    //PRODUCT INTERFACE



    //REPORT INTERFACE


}
