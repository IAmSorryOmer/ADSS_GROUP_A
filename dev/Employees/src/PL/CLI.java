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
    public static boolean isCorrectHour(String x){
        if(x.length()!=5){return false;}
        if(x.charAt(0)=='2'){
            if(x.charAt(1)<'0' || x.charAt(1)>'3'){
                return false;
            }
        }
        else if(x.charAt(0)=='1' || x.charAt(0)=='0'){
            if(x.charAt(1)<'0' || x.charAt(1)>'9'){
                return false;
            }
        }
        else{
            return false;
        }
        if(x.charAt(2)!=':'){
            return false;
        }
        if(x.charAt(3)<'0' || x.charAt(3)>'5'){
            return false;
        }
        if(x.charAt(4)<'0'||x.charAt(4)>'9'){
            return false;
        }
        return true;
    }
    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);
        ManagerController managerController = ManagerController.getInstance();
        EmployeeController employeeController = EmployeeController.getInstance();
        if (!managerController.loadStores())
        {
            System.out.println("ENTER MONTH");
            int month = scanner.nextInt();
            System.out.println("ENTER DAY");
            int day = scanner.nextInt();

            managerController.initializeStores(month, day);
        }
//        mapper.saveEmployee(a,"aviv",1,"1",1,1,"no","1/1",1);
//        mapper.saveCapableShifts(0,"morning",1);
//        mapper.saveShiftManger("3.2","shift manager",0,0,1,0,1);

        int choice = 0;
        while (choice != 3) {
            System.out.println("Hello, Welcome To Super-Li System");
            System.out.println("Choose Your Role Manager/Employee or exit program");
            System.out.println("1.Manager");
            System.out.println("2.Employee");
            System.out.println("3.Exit");
            choice = scanner.nextInt();
//            scanner.nextLine();


            if (choice == 1) {
                System.out.println("Please enter Your ID:");
                int id = scanner.nextInt();
//                scanner.nextLine();


         //       managerController.setActiveUserManager(new Manager(id));
                int operationNum = 20;
                while (operationNum != 0) {
                    System.out.println("Hello Manager, choose operation:");
                    System.out.println("0.Log out");
                    System.out.println("1.Add new employee");
                    System.out.println("2.Add employee to a shift");
                    System.out.println("3.Add a role to a shift");
                    System.out.println("4.Update employee details");
                    System.out.println("5.Watch capable hours of employees for shifts");
                    System.out.println("6.Watch employees");
                    System.out.println("7.Watch shifts");
                    System.out.println("8.Remove Employee");
                    System.out.println("9.Add Destination");
                    System.out.println("10.Add Delivery");
                    System.out.println("11.Add Truck");
                    System.out.println("12.View Deliveries");



                    operationNum = scanner.nextInt();
                    scanner.nextLine();
                    switch (operationNum) {
                        case 0: {
                            System.out.println("logging out");
                            break;
                        }
                        case 1: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("name,employee id,bank account,store num,salary,employee conditions,start date");
                            System.out.println("aviv,5,6045,1,3000,no special conditions,12/4/2020");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 7) {
                                System.out.println("un appropriate amount of details inserted");
                                break;
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
                            break;
                        }
                        case 2: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("store num,employee id,day num(1-7),day part(morning-evening),role");
                            System.out.println("1,5,2,morning,cashier");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 5) {
                                System.out.println("un appropriate amount of details inserted");
                                break;
                            }

                            String addingEmployeeToShift = managerController.addToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), Integer.parseInt(detailsStringArr[2]), detailsStringArr[3], detailsStringArr[4]);
                            System.out.println(addingEmployeeToShift);
                            break;
                        }
                        case 3: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("store num,day num(1-7),day part(morning-evening),role,amount");
                            System.out.println("1,3,evening,shift manager,2");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 5) {
                                System.out.println("un appropriate amount of details inserted");
                                break;
                            }
                            String addingRoleToShift = managerController.addRoleToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], detailsStringArr[3], Integer.parseInt(detailsStringArr[4]));
                            System.out.println(addingRoleToShift);
                            break;
                        }
                        case 4: {

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
                                break;
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
                                    break;
                                }
                                System.out.println("enter capable jobs, as in the example:");
                                System.out.println("cashier,shift manager");
                                String roles = scanner.nextLine();
                                String[] rolesArr = roles.split(",");

                                String updatingEmployee = managerController.updateEmployee(Integer.parseInt(detailsStringArr[0]), rolesArr, detailsStringArr[1],
                                        eID, detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                        detailsStringArr[4], detailsStringArr[5]);
                                System.out.println(updatingEmployee); */

                            break;
                        }
                        case 5: {
                            System.out.println("enter the store num");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.getCapableShiftsByEmployees(store_num));
                            break;
                        }
                        case 6: {
                            System.out.println(managerController.getEmployeesDetails());
                            break;
                        }
                        case 7: {

                            System.out.println("enter store num");
                            int store_num = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.getShiftsDetails(store_num));
                            break;
                        }
                        case 8: {
                            System.out.println("enter employee id");
                            int ID = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(managerController.removeEmployee(ID));
                            break;
                        }
                        case 9: {
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
                                        break;
                                    }
                                }
                            }
                            managerController.addDestination(address, contact, phone, area);
                            break;
                        }
                        case 10: {

                            System.out.println("Please enter date:");
                            String date = scanner.nextLine();

                            System.out.println("Please enter hour in format of: 23:40 | 06:30 ..");
                            String hour = scanner.nextLine();
                            while (!isCorrectHour(hour))
                            {
                                System.out.println("illegal hour, insert again");
                                hour = scanner.nextLine();
                            }
                            System.out.println("Please enter *return* hour in format of: 23:40 | 06:30 ..");
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
                                    break;
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
                                        break;
                                    }

                                    System.out.println("Please enter quantity of that product");
                                    int quantity = Integer.parseInt(scanner.nextLine());
                                    productlst.get(number).put(product, quantity);

                                }
                            }
                            System.out.println("Enter Store Num");
                            int store_num = scanner.nextInt();
                            System.out.println(managerController.addDelivery(store_num,date,hour,truckid,driver,src,weight,numberlst,destlst,productlst,returnHour));
                            break;
                        }

                        case 11:
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
                            break;
                        }
                        case 12:
                        {
                            System.out.println("enter store number");
                            int store_num = scanner.nextInt();

                            System.out.println(managerController.viewDeliveries(store_num));
                            break;
                        }
                    }
                }
            }
            if (choice == 2) {
                System.out.println("Enter Employee ID:");
                int eID = scanner.nextInt();
                System.out.println("Enter Store Number:");
                int store_num = scanner.nextInt();


                if (employeeController.connect(eID,store_num)) {
                    System.out.println("hello employee " + employeeController.getActiveName() + "\nchoose operation:");
                    int operationNum = 0;
                    while (operationNum != 3) {

                        System.out.println("1. Insert available times for shifts");
                        System.out.println("2. Watch shifts of this week");
                        System.out.println("3. Log out");
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
                                break;
                            }
                            case 2: {
                                System.out.println(employeeController.watchMyShifts());
                                break;
                            }
                            case 3:
                            {
                                System.out.println("logging out");
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("there is no employee with that id \n");
                }

            }
        }
        System.out.println("BYE BYE");
    }
}
