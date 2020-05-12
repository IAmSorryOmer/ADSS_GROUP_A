package BL;

public class Employee {


    private String[] capable_jobs;
    private String[] capable_shifts;
    private String name;
    private int ID;
    private String bankAccount;
    private int salary;
    private int store_num;
    private String employee_conditions;
    private String start_date;
    private boolean hasAssignedShifts;


    public Employee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date)
    {
        capable_shifts = new String[7];
        for (int i =0; i < 7; i++)
            capable_shifts[i]="";
        capable_jobs = jobs;
        ID = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.employee_conditions = employee_conditions;
        this.start_date = start_date;
        this.salary = salary;
        this.store_num = store_num;
    }
    public String[] getJobs() {
        return capable_jobs;
    }

    public String[] getCapable_shifts() {
        return capable_shifts;
    }

    public int getSalary() {
        return salary;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getName() {
        return name;
    }

    public int getStore_num() {
        return store_num;
    }

    public int getID() {
        return ID;
    }

    public String getEmployee_conditions() {
        return employee_conditions;
    }

    public String getStart_date() {
        return start_date;
    }

    public String[] getCapable_jobs() {
        return capable_jobs;
    }


    public void updateEmployee(String[] jobs, String name, String bankAccount, int store_num, int salary, String employee_conditions, String start_date)
    {
        capable_jobs = jobs;
        this.name = name;
        this.bankAccount = bankAccount;
        this.employee_conditions = employee_conditions;
        this.start_date = start_date;
        this.salary = salary;
        this.store_num = store_num;
    }

    public void setCapable_shifts(String[] capable_shifts) {
        for (int i = 0; i < capable_shifts.length; i++) {
            if (capable_shifts[i] == null)
                capable_shifts[i] = "";
        }
        this.capable_shifts = capable_shifts;
        hasAssignedShifts = true;
    }

    public void setHasAssignedShifts(boolean b)
    {
        hasAssignedShifts = b;
    }
    public String toString() {

        String result;
        result = "Name: " + name + ", ID: " + ID + ", Bank Account: " + bankAccount + ", Salary: " + salary + ", Store Number: " + store_num + ", Employee Conditions: " + employee_conditions + ", Start Date: " + start_date;
        result += ", Capable Jobs: ";
        for (int i = 0; i < capable_jobs.length; i++) {
            result += capable_jobs[i] + ", ";
        }
        result = result.substring(0, result.length() - 2);
        result += "\n";
        return result;

    }

    public boolean getHasAssignedShifts() {
        return hasAssignedShifts;
    }
}
