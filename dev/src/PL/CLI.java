package PL;

import BL.Manager;
import DAL.Mapper;
import IL.EmployeeController;
import IL.ManagerController;

import javax.crypto.spec.PSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class CLI {
    public static void print_shifts()
    {
        System.out.println("1.sunday morning      2.sunday evening");
        System.out.println("3.monday morning      4.monday evening");
        System.out.println("5.tuesday morning     6.tuesday evening");
        System.out.println("7.wednesday morning   8.wednesday evening");
        System.out.println("9.thursday morning    10.thursday evening");
        System.out.println("11.friday morning     12.friday evening");
        System.out.println("13.saturday morning   14.saturday evening");

    }
    public static void print_Storage_employee_menu()
    {
        System.out.println("1. Insert available times for shifts");
        System.out.println("2. Watch shifts of this week");
        System.out.println("3. Log out");
    }
    public static void print_employee_menu()
    {
        System.out.println("1. Insert available times for shifts");
        System.out.println("2. Watch shifts of this week");
        System.out.println("3. Log out");
    }
    public static void print_store_manager_menu()
    {
        System.out.println("What do you want to do manager?");
        while(true) {

        }
    }
    public static void print_logistic_manager_menu()
    {
        // @TODO
    }


    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);

        ManagerController managerController = ManagerController.getInstance();
        EmployeeController employeeController = EmployeeController.getInstance();
        if (!managerController.loadStores())//Initializing the day
        {
            System.out.println("Initializing System..");
            System.out.println("ENTER MONTH");
            int month = scanner.nextInt();
            System.out.println("ENTER DAY");
            int day = scanner.nextInt();

            managerController.initializeStores(month, day);
        }

        //CHOOSE ROLE IN THE SYSTEM
        int choice = 0;
        while (choice != 5) {
            System.out.println("Hello, Welcome To Super-Li System");
            System.out.println("Choose Your Role Manager/Employee or exit program");
            System.out.println("1.Manager");
            System.out.println("2.Employee");
            System.out.println("3.Store manager");
            System.out.println("4.Logistic manager");
            System.out.println("5.Exit");
            choice = scanner.nextInt();
//            scanner.nextLine();


            if (choice == 1) {
                System.out.println("Please enter Your ID:");
                int id = scanner.nextInt();

                //Super-manager menu
                int operationNum = 20;
                while (operationNum != 0) {
                    System.out.println("Hello Manager, choose operation:");
                    System.out.println("0.Log out");
                    System.out.println("1.Add new employee");
                    System.out.println("2.Add employee to a shift");
                    System.out.println("3.Add a role to a shift");
                    System.out.println("4.Update employee details");
                    System.out.println("5.Watch capable hours and capable roles of employees for shifts");
                    System.out.println("6.Watch employees");
                    System.out.println("7.Watch shifts");
                    System.out.println("8.Remove Employee");
                    System.out.println("9.Add Destination");
                    System.out.println("10.Add Delivery");
                    System.out.println("11.Add Truck");
                    System.out.println("12.View Deliveries");
                    System.out.println("13.Watch Date of the first day in this week");



                    operationNum = scanner.nextInt();
                    scanner.nextLine();
                    switch (operationNum) {
                        case 0: {
                            System.out.println("logging out");
                            
                        }
                        //Entering new employee
                        void enterEmployee() {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("name,employee id,bank account,store num,salary,employee conditions,start date");
                            System.out.println("aviv,5,6045,1,3000,no special conditions,12/4/2020");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 7) {
                                System.out.println("un appropriate amount of details inserted");
                                
                            }
                            System.out.println("is the new employee a driver? y/n");
                            String answer = scanner.nextLine();
                            while (!(answer.equals("y") || answer.equals("n")))
                            {
                                System.out.println("enter y or n");
                                answer = scanner.nextLine();
                            }
                            if (answer.equals("y"))
                            {
                                System.out.println("enter the driver license");
                                String license = scanner.nextLine();

                                String[] role = {"driver"};
                                System.out.println(managerController.addDriverEmployee(role,detailsStringArr[0],
                                        Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                        Integer.parseInt(detailsStringArr[4]), detailsStringArr[5], detailsStringArr[6],license));
                            }
                            else {
                                System.out.println("enter capable jobs, as in the example:");
                                System.out.println("cashier,shift manager");
                                String roles = scanner.nextLine();

                                String[] rolesArr = roles.split(",");

                                String addingNewEmployee = managerController.addEmployee(rolesArr, detailsStringArr[0],
                                        Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                        Integer.parseInt(detailsStringArr[4]), detailsStringArr[5], detailsStringArr[6]);
                                System.out.println(addingNewEmployee);
                            }
                            
                        }
                        //Assigning employee to shift
                        void assignEmployeeToShift() {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("store num,employee id,day num(1-7),day part(morning-evening),role");
                            System.out.println("1,5,2,morning,cashier");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 5) {
                                System.out.println("un appropriate amount of details inserted");
                                
                            }

                            String addingEmployeeToShift = managerController.addToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), Integer.parseInt(detailsStringArr[2]), detailsStringArr[3], detailsStringArr[4]);
                            System.out.println(addingEmployeeToShift);
                            
                        }
                        //Adding a role to a shift, still need someone to fill that role
                        void addRoleToShift(){
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("store num,day num(1-7),day part(morning-evening),role,amount");
                            System.out.println("1,3,evening,shift manager,2");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 5) {
                                System.out.println("un appropriate amount of details inserted");
                                
                            }
                            String addingRoleToShift = managerController.addRoleToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], detailsStringArr[3], Integer.parseInt(detailsStringArr[4]));
                            System.out.println(addingRoleToShift);
                            
                        }
                        //update details of employee
                        void updateEmployeeDetails() {

                            System.out.println("Enter Employee ID:");
                            int eID = scanner.nextInt();
                            System.out.println("Enter Store Number:");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println(managerController.getEmployeeDetails(store_num, eID));
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("name,bank account,salary,employee conditions");
                            System.out.println("tal,6156,3000,no special conditions");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 4) {
                                System.out.println("un appropriate amount of details inserted");
                                
                            }
//                            System.out.println("enter capable jobs, as in the example:");
//                            System.out.println("cashier,shift manager");
//                            String roles = scanner.nextLine();
//                            String[] rolesArr = roles.split(",");

                            String updatingEmployee = managerController.updateEmployee(store_num, detailsStringArr[0],
                                    eID, detailsStringArr[1], Integer.parseInt(detailsStringArr[2]),
                                    detailsStringArr[3]);
                            System.out.println(updatingEmployee);




                            /*
                            System.out.println("Enter Employee ID:");
                            int eID = scanner.nextInt();
                            System.out.println("Enter Store Number:");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();

                                System.out.println(managerController.getEmployeeDetails(store_num,eID));
                                System.out.println("enter the following details, as in the example:");
                                System.out.println("name,bank account,salary,employee conditions,start date");
                                System.out.println("tal,6156,3000,no special conditions,12/4/2020");
                                String details = scanner.nextLine();
                                String[] detailsStringArr = details.split(",");
                                if (detailsStringArr.length != 6) {
                                    System.out.println("un appropriate amount of details inserted");
                                    
                                }
                                System.out.println("enter capable jobs, as in the example:");
                                System.out.println("cashier,shift manager");
                                String roles = scanner.nextLine();
                                String[] rolesArr = roles.split(",");

                                String updatingEmployee = managerController.updateEmployee(Integer.parseInt(detailsStringArr[0]), rolesArr, detailsStringArr[1],
                                        eID, detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                        detailsStringArr[4], detailsStringArr[5]);
                                System.out.println(updatingEmployee); */

                            
                        }
                        //Watch all the hours that people are capable to work in
                        void watchCapableHours() {
                            System.out.println("enter the store num");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.getCapableShiftsByEmployees(store_num));
                            
                        }
                        //Watch all the details of all the employees
                        void watchEmployeeDetails {
                            System.out.println(managerController.getEmployeesDetails());
                            
                        }
                        //Watch details of all the shifts
                        void watchShiftDetails {

                            System.out.println("enter store num");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.getShiftsDetails(store_num));
                            
                        }
                        //Remove employee from system
                        void removeEmployee() {
                            System.out.println("enter employee id");
                            int ID = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.removeEmployee(ID));
                            
                        }
                        //Add new destination, delivery can go to that destination now
                        void addDestination() {
                            System.out.println("enter address");
                            String address = scanner.nextLine();

                            System.out.println("enter contact");
                            String contact = scanner.nextLine();

                            System.out.println("enter phone");
                            String phone = scanner.nextLine();

                            System.out.println("enter area");
                            String area = scanner.nextLine();

                            boolean goodInput = false;
                            while (!goodInput){
                                goodInput = true;
                                if (address.isEmpty()){
                                    System.out.println("empty address, please enter a new one");
                                    address = scanner.nextLine();
                                    goodInput = false;
                                }
                                if (contact.isEmpty()){
                                    System.out.println("empty contact, please enter a new one");
                                    contact = scanner.nextLine();
                                    goodInput = false;
                                }
                                if (phone.isEmpty()){
                                    System.out.println("empty phone number, please enter a new one");
                                    phone = scanner.nextLine();
                                    goodInput = false;
                                }
                                if (area.isEmpty()){
                                    System.out.println("empty area, please enter a new one");
                                    area = scanner.nextLine();
                                    goodInput = false;
                                }
                                for (int i = 0; i < phone.length(); i++) {
                                    if (!((phone.charAt(i) >= '0' & phone.charAt(i) <= '9') | (phone.charAt(i) == '-'))){
                                        System.out.println("invalid phone number, please enter a new one");
                                        phone = scanner.nextLine();
                                        goodInput = false;
                                        
                                    }
                                }
                            }
                            managerController.addDestination(address, contact, phone, area);
                            
                        }
                        //Add a new delivery to the system
                        void addDelivery() {
                                sendDelivery();
                              
                        }
                        //Add a new truck to the system
                        void addTruck()
                        {
                            System.out.println("enter model");
                            String model = scanner.nextLine();

                            System.out.println("enter Truck id");
                            String truckId = scanner.nextLine();

                            System.out.println("enter store number");
                            int store_num = scanner.nextInt();

                            System.out.println("enter weight");
                            int weight = scanner.nextInt();

                            System.out.println("enter max weight");
                            int maxWeight = scanner.nextInt();



                            boolean goodInput = false;
                            while (!goodInput) {
                                goodInput = true;
                                if (truckId.isEmpty()) {
                                    System.out.println("empty truck id, please enter a new one");
                                    truckId = scanner.nextLine();
                                    goodInput = false;
                                }
                                if (model.isEmpty()) {
                                    System.out.println("empty model, please enter a new one");
                                    model = scanner.nextLine();
                                    goodInput = false;
                                }
                            }
                            managerController.addTruck(store_num,truckId, weight, maxWeight, model);
                            
                        }
                        //View all the deliveries
                        void viewDeliveries()
                        {
                            System.out.println("enter store number");
                            int store_num = scanner.nextInt();

                            System.out.println(managerController.viewDeliveries(store_num));
                            
                        }
                        //Watch the date of the first day in the week(for some reason)
                        void WatchSunday()
                        {
                            System.out.println(managerController.getSundayDate());
                            
                        }
                    }
                }
            }

            //If you are an employee which is not a manager, you enter this menu
            if (choice == 2) {
                System.out.println("Enter Employee ID:");
                int eID = scanner.nextInt();
                System.out.println("Enter Store Number:");
                int store_num = scanner.nextInt();


                if (employeeController.connect(eID,store_num)) {
                    //If you are a storage worker, you enter here the storage worker menu
                    if (employeeController.isStorage())
                    {
                        System.out.println("hello storage employee " + employeeController.getActiveName() + "\nchoose operation:");
                        int operationNum = 0;
                        operationNum = scanner.nextInt();
                        scanner.nextLine();
                        print_Storage_employee_menu();
                        while (operationNum != 3) {



                        }
                    }
                    //menu for normal employees(non storage)
                    System.out.println("hello employee " + employeeController.getActiveName() + "\nchoose operation:");
                    int operationNum = 0;
                    while (operationNum != 3) {

                        print_employee_menu();
                        operationNum = scanner.nextInt();
                        scanner.nextLine();
                        switch (operationNum) {
                            case 1: {
                                if (!employeeController.hasActiveUserAssignedShifts()) {
                                    print_shifts();
                                    System.out.println("enter numbers of the capable shifts with ',' between each number, for example: ");
                                    System.out.println("1,2,4,12");
                                    String input = scanner.nextLine();
                                    String[] detailsStringArr = input.split(",");
                                    int[] capableShifts = new int[detailsStringArr.length];
                                    for (int i = 0; i < detailsStringArr.length; i++) {
                                        capableShifts[i] = Integer.parseInt(detailsStringArr[i]);
                                    }
                                    System.out.println(employeeController.enterMyCapableShifts(capableShifts));
                                }
                                else {
                                    System.out.println("you already assigned shifts");
                                }
                                
                            }
                            case 2: {
                                System.out.println(employeeController.watchMyShifts());
                                
                            }
                            case 3:
                            {
                                System.out.println("logging out");
                                
                            }
                        }
                    }
                } else {
                    System.out.println("there is no employee with that id \n");
                }

            }
            if (choice ==3) //STORE MANAGER-NOT IMPLEMENTED
            {
                print_store_manager_menu();
                System.out.println("hello Store manager" + "\nchoose operation:");
                int operationNum = 0;
                while (operationNum != 3) {


                }

            }
            if (choice ==4) //LOGISTIC-NOT IMPLEMENTED
            {
                print_logistic_manager_menu();
                System.out.println("hello Logistic Manager" + employeeController.getActiveName() + "\nchoose operation:");
                int operationNum = 0;
                while (operationNum != 3) {
                    
                }
            }
        }
        System.out.println("BYE BYE");
    }
    public static void sendDelivery(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Delivery can be added to this week only");
        System.out.println("Please enter date, of a day in this week, for example : 1.5 (it's a random date)");
        String date = scanner.nextLine();

        System.out.println("Please enter hour in format of: 23:40 (4 digits) ..");
        String hour = scanner.nextLine();
        while (!isCorrectHour(hour))
        {
            System.out.println("illegal hour, insert again");
            hour = scanner.nextLine();
        }
        System.out.println("Please enter *return* hour in format of: 23:40 (4 digits) ..");
        String returnHour = scanner.nextLine();
        while (!isCorrectHour(returnHour))
        {
            System.out.println("illegal hour, insert again");
            returnHour = scanner.nextLine();
        }


        System.out.println("Please enter truck id:");
        String truckid = scanner.nextLine();

        System.out.println("Please enter the name of the driver:");
        String driver = scanner.nextLine();

        System.out.println("Please enter the source of the delivery:");
        String src = scanner.nextLine();

        System.out.println("Please enter the total weight of the stuff you want to deliver:");
        int weight = Integer.parseInt(scanner.nextLine());
        boolean stop = false;
        List<String> numberlst = new LinkedList<>();
        HashMap<String, String> destlst = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> productlst = new HashMap<>();
        while (true) {

            System.out.println("Please enter the number of the file or \"r\" to stop entering files");
            String number = scanner.nextLine();
            if (number.equals("r")) {
                
            }
            numberlst.add(number);
            System.out.println("Please enter address of a destination for that file:");
            String dest = scanner.nextLine();
            destlst.put(number, dest);
            productlst.put(number, new HashMap<String, Integer>());
            while (true) {
                System.out.println("Please enter name of product or \"r\" to stop entering products");
                String product = scanner.nextLine();
                if (product.equals("r")) {
                    
                }

                System.out.println("Please enter quantity of that product");
                int quantity = Integer.parseInt(scanner.nextLine());
                productlst.get(number).put(product, quantity);

            }
        }
        System.out.println("Enter Store Num");
        int store_num = scanner.nextInt();
        System.out.println(ManagerController.getInstance().addDelivery(store_num,date,hour,truckid,driver,src,weight,numberlst,destlst,productlst,returnHour));
    }
}
