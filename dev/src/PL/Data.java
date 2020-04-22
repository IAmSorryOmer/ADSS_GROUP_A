package PL;

import BL.Employee;
import BL.Store;

import java.util.LinkedList;
import java.util.List;

public class Data {
    public static List<Store> stores;
    public static List<Employee> employees;
    final static int storeNumbers = 9;

    public static void Initialize()
    {
        stores = new LinkedList<>();
        for (int i =1; i <= storeNumbers; i++)
        {
            stores.add(new Store(i));
        }
        employees = new LinkedList<>();
        String jobs1[] = {"cashier","shift manager"};
        String jobs2[] = {"security","shift manager","storage"};
        String [] capableShifts1 = new String[7];
        String [] capableShifts2 = new String[7];


        capableShifts1[1] ="morning";
        capableShifts1[2] = "evening";

        capableShifts2[6] = "morningevening";
        capableShifts2[1] = "morning";


        Employee employee1 = new Employee(jobs1,"aviv",1,"4036",1,100,"no special", "19/4/2020");
        Employee employee2 = new Employee(jobs2,"tal",2,"5041",1,50,"no special", "19/4/2020");
        Employee employee3 = new Employee(jobs1,"dani",3,"6165",2,30,"no special", "20/4/2020");

        employee1.setCapable_shifts(capableShifts1);
        employee2.setCapable_shifts(capableShifts2);

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        getStore(1).getEmployees().add(employee1);
        getStore(1).getEmployees().add(employee2);
        getStore(2).getEmployees().add(employee3);

        //adding shifts for store 1
        Store store = getStore(1);
        store.getSchedule().getDays()[1].enterEmployeeToShift("morning",1,"shift manager");

        store.getSchedule().getDays()[2].enterJobToShift("evening","cashier",2);
        store.getSchedule().getDays()[2].enterEmployeeToShift("evening",1,"shift manager");

        store.getSchedule().getDays()[6].enterJobToShift("evening","security",2);
        store.getSchedule().getDays()[1].enterJobToShift("evening","storage",2);

    }
    public static Store getStore(int store_num)
    {
        for (int i = 0; i < storeNumbers; i++) {

            if (stores.get(i).getStore_num() == store_num) {
                return stores.get(i);
            }
        }
        return null;
    }
    public static List<Store> getStores() {
        return stores;
    }

    public static Employee getEmployeeById(int id)
    {
        for (Store s: stores) {
            for (Employee e : s.getEmployees()) {
                if (e.getID() == id)
                    return e;
            }
        }
        return null;
    }

    public static List<Employee> getEmployees() {
        return employees;
    }
}
