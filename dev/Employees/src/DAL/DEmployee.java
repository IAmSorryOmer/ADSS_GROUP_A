package DAL;

public class DEmployee {


    private String name;
    private int ID;
    private String bankAccount;
    private int salary;
    private int store_num;
    private String employee_conditions;
    private String start_date;
    private boolean hasAssignedShifts;
    private String[] capable_jobs;
    private String[] capable_shifts;

    public DEmployee(String name, int ID, String bankAccount, int salary, int store_num, String employee_conditions, String start_date, boolean hasAssignedShifts) {

        this.name = name;
        this.ID = ID;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.store_num = store_num;
        this.employee_conditions = employee_conditions;
        this.start_date = start_date;
        this.hasAssignedShifts = hasAssignedShifts;

    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public int getSalary() {
        return salary;
    }

    public int getStore_num() {
        return store_num;
    }

    public String getEmployee_conditions() {
        return employee_conditions;
    }

    public String getStart_date() {
        return start_date;
    }

    public boolean isHasAssignedShifts() {
        return hasAssignedShifts;
    }

    public String[] getCapable_shifts() {
        return capable_shifts;
    }

    public void setCapable_jobs(String[] capable_jobs) {
        this.capable_jobs = capable_jobs;
    }

    public void setCapable_shifts(String[] capable_shifts) {
        this.capable_shifts = capable_shifts;
    }

    public String[] getCapable_jobs() {
        return capable_jobs;
    }

}
