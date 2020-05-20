//import BL.Employee;
//import BL.Store;
//import IL.EmployeeController;
//import IL.ManagerController;
//import BL.Validation;
//import org.junit.*;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class TESTS {
//    private Employee validEmployee1;
//    private Employee validEmployee2;
//    private Employee validEmployee3;
//    private Employee notValidEmployee1;
//    private Employee notValidEmployee2;
//    private ManagerController managerController = ManagerController.getInstance();
//    private EmployeeController employeeController = EmployeeController.getInstance();
//
//    @Before
//    public void createEmployeesAndStores() {
//        List<Store> stores = new LinkedList<>();
//        Store store1 = new Store(1);
//        Store store2 = new Store(2);
//        stores.add(store1);
//        stores.add(store2);
//
//
//        String jobs1[] = {"cashier","shift manager"};
//        String jobs2[] = {"security","shift manager","storage"};
//        String jobs3[] = {};
//
//        String [] capableShifts1 = new String[7];
//        String [] capableShifts2 = new String[7];
//
//
//
//        capableShifts1[1] ="morning";
//        capableShifts1[2] = "evening";
//
//        capableShifts2[6] = "morningevening";
//        capableShifts2[1] = "morning";
//
//
//        validEmployee1 = new Employee(jobs1,"aviv",1,"4036",1,100,"no special", "19/4/2020");
//        validEmployee2 = new Employee(jobs2,"tal",2,"5041",2,50,"no special", "19/4/2020");
//        validEmployee3 = new Employee(jobs1,"e3",3,"123",1,100,"no special", "19/4/2020");
//        store1.getEmployees().add(validEmployee3);
//        validEmployee3.setCapable_shifts(capableShifts1);
//
//        //store num  5  doesn't exists
//        notValidEmployee1 = new Employee(jobs1,"e4",4,"231",5,200,"no special","19/4/2020");
//        //empty jobs array
//        notValidEmployee2 = new Employee(jobs3,"e5",5,"231",1,200,"no special","19/4/2020");
//
//
//
//        store1.getSchedule().getDays()[2].enterJobToShift("evening","cashier",2);
//        store1.getSchedule().getDays()[2].enterEmployeeToShift("evening",1,"cashier");
//
//        store1.getSchedule().getDays()[6].enterJobToShift("evening","security",2);
//        store1.getSchedule().getDays()[1].enterJobToShift("evening","storage",2);
//
//
//        managerController.setStores(stores);
//        employeeController.setStores(stores);
//
//        managerController.setStoreNumbers(2);
//    }
//
//    //MANAGER  ********************************************
//    @Test
//    public void testAddingValidEmployee() {
//        Assert.assertEquals(managerController.addEmployee(validEmployee1.getCapable_jobs(), "aviv", 1, "4036", 1, 100, "no special", "19/4/20")
//                , "success");
//    }
//    @Test
//    public void testAddingEmployee_StoreNotExist() {
//        //store num  5  doesn't exists
//        Assert.assertNotEquals(managerController.addEmployee(notValidEmployee1.getCapable_jobs(),"e4",4,"231",5,200,"no special","19/4/2020")
//                , "success");
//    }
//    @Test
//    public void testAddingEmployee_NotValidEmployee() {
//        //empty jobs array
//        Assert.assertNotEquals(managerController.addEmployee(notValidEmployee2.getCapable_jobs(),"e5",5,"231",1,200,"no special","19/4/2020")
//                , "success");
//    }
//
//    @Test
//    public void testGetEmployeeByID() {
//        Assert.assertEquals(validEmployee3,managerController.getEmployeeById(validEmployee3.getID()));
//    }
//
//    @Test
//    // public String addToShift(int employeeId, int store_num, int dayNum, String dayPart, String role)
//    public void test_Valid_AddingEmployeeToShift() {
//       Assert.assertEquals(managerController.addToShift(validEmployee3.getID(),3,"evening","shift manager"),
//               "success");
//    }
//
//    @Test
//    //public static String checkCabaleShifts(String[] capable_shifts, int dayNum, String dayPart,String[] capable_roles, String role )
//    public void test_Validation_NotValidCapableShifts()
//    {
//
//        Assert.assertNotEquals(Validation.checkCabaleShifts(validEmployee1.getCapable_shifts(),4,"evening",
//                                                        validEmployee1.getCapable_jobs(),validEmployee1.getCapable_jobs()[0]),"success");
//    }
//    @Test
//    public  void  test_Validation_NotValidRoles()
//    {
//        Assert.assertNotEquals(Validation.checkCabaleShifts(validEmployee1.getCapable_shifts(),2,"evening",
//                validEmployee1.getCapable_jobs(),"security"),"success");
//    }
//
//    @Test
//    //public String updateEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date)
//    public void testUpdateEmployee_Valid()
//    {
//        managerController.updateEmployee(validEmployee3.getJobs(),validEmployee3.getName(),validEmployee3.getID(),validEmployee3.getBankAccount()
//                ,300,validEmployee3.getEmployee_conditions(),validEmployee3.getStart_date());
//
//        Assert.assertEquals(300,validEmployee3.getSalary());
//    }
//
//
//
//    //EMPLOYEE  ****************************************************
//    @Test
//    //public boolean connect(int id)
//    public void testLogin()
//    {
//        Assert.assertTrue(employeeController.connect(validEmployee3.getID()));
//        Assert.assertEquals(employeeController.activeUserEmployee,validEmployee3);
//    }
//    @Test
//    public void testChoosingShifts()
//    {
//        int [] numbers = {1,3,15,34};
//        Assert.assertNotEquals("success",Validation.checkSelectedShifts(numbers));
//    }
//
//
//}