package BL;

import java.util.LinkedList;
import java.util.List;

public class Store {

    private List<Employee> employees;
    private WeekSchedule schedule;
    private int store_num;

    public Store(int store_num)
    {
        this.store_num = store_num;
        this.employees = new LinkedList<>();
        this.schedule = new WeekSchedule();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String addEmployeeToShift(int employeeId, int dayNum, String dayPart, String role) {
        return schedule.getDays()[dayNum-1].enterEmployeeToShift(dayPart,employeeId,role);
    }

    public int getStore_num() {
        return store_num;
    }

    public String addRoleToShift(int dayNum, String dayPart, String role, int amount) {
        return schedule.getDays()[dayNum-1].enterJobToShift(dayPart,role,amount);
    }

    public WeekSchedule getSchedule() {
        return schedule;
    }
}
