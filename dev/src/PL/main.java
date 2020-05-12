//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Scanner;
//public class main {
//    public static void main(String[] args){
//        boolean loadedData=false;
//        while(true){
//            System.out.println("What would you like to do today?");
//            System.out.println("press 1 to see the database");
//            System.out.println("press 2 to add a delivery to the database");
//            System.out.println("press 3 to load data");
//            System.out.println("press 4 to quit the program");
//            Scanner sc=new Scanner(System.in);
//            String input=sc.nextLine();
//            if(input.equals("1")){
//            System.out.println(Interface.viewDeliveries());
//            }
//            if(input.equals("2")){
//                System.out.println("Please enter date:");
//                String date=sc.nextLine();
//
//                System.out.println("Please enter hour:");
//                String hour=sc.nextLine();
//
//                System.out.println("Please enter truck id:");
//                String truckid=sc.nextLine();
//
//                System.out.println("Please enter the name of the driver:");
//                String driver=sc.nextLine();
//
//                System.out.println("Please enter the source of the delivery:");
//                String src=sc.nextLine();
//
//                System.out.println("Please enter the total weight of the stuff you want to deliver:");
//                int weight=Integer.parseInt(sc.nextLine());
//                boolean stop=false;
//                List<String> numberlst=new LinkedList<>();
//                HashMap<String,String> destlst=new HashMap<>();
//                HashMap<String,HashMap<String,Integer>> productlst=new HashMap<>();
//                while(true) {
//
//                    System.out.println("Please enter the number of the file or \"r\" to stop entering files");
//                    String number = sc.nextLine();
//                    if(number.equals("r")){
//                        break;
//                    }
//                    numberlst.add(number);
//                    System.out.println("Please enter address of a destination for that file:");
//                    String dest = sc.nextLine();
//                    destlst.put(number,dest);
//                    productlst.put(number,new HashMap<String,Integer>());
//                    while(true) {
//                        System.out.println("Please enter name of product or \"r\" to stop entering products");
//                        String product = sc.nextLine();
//                        if(product.equals("r")){
//                            break;
//                        }
//
//                        System.out.println("Please enter quantity of that product");
//                        int quantity = Integer.parseInt(sc.nextLine());
//                        productlst.get(number).put(product,quantity);
//
//                    }
//                }
//            }
//            if(input.equals("3")){
//                if(!loadedData) {
//                    Interface.addDestination("BambaLand 18", "0381298427", "Omer Barak", "Area C");
//                    Interface.addDestination("ShlomoHaGever 76", "032984322", "Nisim Nisim", "Jerusalem");
//                    Interface.addDestination("Sally Acorn 7", "329034223", "Tomer Fridman", "Jerusalem");
//                    Interface.addDestination("Julie-su the echidna 50", "0294892001", "Mirai Zura", "Tel Aviv");
//                    Interface.addTruck("4732", 17, 85, "Hoverman 2");
//                    Interface.addTruck("2751", 13, 62, "Bamboo");
//                    Interface.addDriver("Footy Mcsprooge", License.C1New);
//                    Interface.addDriver("Oldy Feelcheek", License.CPlusE);
//                    loadedData=true;
//                }
//
//            }
//            if(input.equals("4")){
//                return;
//            }
//        }
//    }
//}
//
////Final version