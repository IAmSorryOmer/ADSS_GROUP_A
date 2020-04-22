package PL;

import BL.Manager;
import IL.EmployeeController;
import IL.ManagerController;

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
    public static void main(String[] args) {

        Data.Initialize();
        Scanner scanner = new Scanner(System.in);

        int choice = 0;
        while (choice != 3) {
            System.out.println("Hello, Welcome To Super-Li System");
            System.out.println("Choose Your Role Manager/Employee or exit program");
            System.out.println("1.Manager");
            System.out.println("2.Employee");
            System.out.println("3.Exit");
            choice = scanner.nextInt();
            scanner.nextLine();


            if (choice == 1) {
                System.out.println("Please enter Your ID:");
                int id = scanner.nextInt();
                scanner.nextLine();
                ManagerController managerController = ManagerController.getInstance();
                managerController.setStores(Data.getStores());
                managerController.setActiveUserManager(new Manager(id));
                int operationNum = 0;
                while (operationNum != 9) {
                    System.out.println("Hello Manager, choose operation:");
                    System.out.println("1.Add new employee");
                    System.out.println("2.Add employee to a shift");
                    System.out.println("3.Add a role to a shift");
                    System.out.println("4.Update employee details");
                    System.out.println("5.Watch capable hours of employees for shifts");
                    System.out.println("6.Watch employees");
                    System.out.println("7.Watch shifts");
                    System.out.println("8.Remove Employee");
                    System.out.println("9.Log out");

                    operationNum = scanner.nextInt();
                    scanner.nextLine();
                    switch (operationNum) {
                        case 1: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("name,employee id,bank account,store num,salary,employee conditions,start date");
                            System.out.println("aviv,5,6045,1,3000,no special conditions,12/4/2020");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 7)
                            {
                                System.out.println("un appropriate amount of details inserted");
                                break;
                            }

                            System.out.println("enter capable jobs, as in the example:");
                            System.out.println("cashier,shift manager");
                            String roles = scanner.nextLine();

                            String[] rolesArr = roles.split(",");

                            String addingNewEmployee = managerController.addEmployee(rolesArr, detailsStringArr[0],
                                    Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                    Integer.parseInt(detailsStringArr[4]), detailsStringArr[5], detailsStringArr[6]);
                            System.out.println(addingNewEmployee);
                            break;
                        }
                        case 2: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("employee id,day num(1-7),day part(morning-evening),role");
                            System.out.println("5,2,morning,cashier");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 4)
                            {
                                System.out.println("un appropriate amount of details inserted");
                                break;
                            }

                            String addingEmployeeToShift = managerController.addToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], detailsStringArr[3]);
                            System.out.println(addingEmployeeToShift);
                            break;
                        }
                        case 3: {
                            System.out.println("enter the following details, as in the example:");
                            System.out.println("store num,day num(1-7),day part(morning-evening),role,amount");
                            System.out.println("1,3,evening,shift manager,2");
                            String details = scanner.nextLine();
                            String[] detailsStringArr = details.split(",");
                            if (detailsStringArr.length != 5)
                            {
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
                            scanner.nextLine();
                            if (managerController.isEmployeeExists(eID))
                            {
                                System.out.println(managerController.getEmployeeDetails(eID));
                                System.out.println("enter the following details, as in the example:");
                                System.out.println("name,bank account,salary,employee conditions,start date");
                                System.out.println("tal,6156,3000,no special conditions,12/4/2020");
                                String details = scanner.nextLine();
                                String[] detailsStringArr = details.split(",");
                                if (detailsStringArr.length != 5)
                                {
                                    System.out.println("un appropriate amount of details inserted");
                                    break;
                                }
                                System.out.println("enter capable jobs, as in the example:");
                                System.out.println("cashier,shift manager");
                                String roles = scanner.nextLine();
                                String[] rolesArr = roles.split(",");
                                String updatingEmployee = managerController.updateEmployee(rolesArr, detailsStringArr[0],
                                        eID, detailsStringArr[1], Integer.parseInt(detailsStringArr[2]),
                                        detailsStringArr[3], detailsStringArr[4]);
                                System.out.println(updatingEmployee);
                            }
                            else
                            {
                                System.out.println("no employee with that id");
                            }

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
                            System.out.println(managerController.getEmployeesNameID());
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
                            System.out.println("logging out");
                            break;
                        }

                    }
                }
            }
            if (choice == 2) {
                System.out.println("Please enter Your ID:");
                int id = scanner.nextInt();
                scanner.nextLine();
                EmployeeController employeeController = EmployeeController.getInstance();
                employeeController.setStores(Data.getStores());
                if (employeeController.connect(id)) {
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
