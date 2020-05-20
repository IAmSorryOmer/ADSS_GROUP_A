package BL;

import DAL.DDelivery;
import DAL.Mapper;
import IL.Callback;

import java.util.*;

public class DeliveryController {

	private List<Delivery> deliveries;
	private DestController dControl;
	private DriverController driverControl;
	private TruckController tControl;

	public DeliveryController(){
		deliveries = new LinkedList<>();
		dControl = DestController.getInstance();
		driverControl = new DriverController();
		tControl = new TruckController();
	}
	public void Load(int store_num, DAL.Mapper mapper)
	{
		List<DAL.DDelivery> Ddeliveries = mapper.loadDeliveries(store_num);
		for (int i = 0; i < Ddeliveries.size(); i++)
		{
			Delivery delivery = new Delivery(Ddeliveries.get(i));
			delivery.Load(mapper);
			deliveries.add(delivery);
		}

		driverControl.load(store_num,mapper);
		tControl.Load(store_num,mapper);
	}

	public String toString(){
		String ret = "";
		for (Delivery delivery:deliveries){
			ret += "\ndate: " + delivery.getDate();
			ret += "\ntime: " + delivery.getDispatchTime();
			ret += "\nTruck: " + delivery.getTID();
			ret += "\ndriver: " + delivery.getDName();
			ret += "\nsource: " + delivery.getSource();
			ret += "\nweight: " + delivery.getPreWeight();
			int i = 0;
			for (Destination destination:delivery.getDestinationList()){
				ret += "\ndestination " + ++i + ": " + destination.getAddress();
			}
			ret += "\n";
		}
		return ret;
	}


	public String addDelivery(Mapper mapper,String date, String time, String ID, String name, String source, int weight,
							  List<String> numberedFiles, HashMap<String,String> numDest,
							  HashMap<String,HashMap<String,Integer>> products,int store_num, String returnHour){
		for (String destination: numDest.values()){
			if (!checkAddress(destination)){
//				return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
//						HashMap<String,HashMap<String,Integer>> p)-> {
//					Scanner scanner = new Scanner(System.in);
//					String str = "";
//					while (true){
//						str = scanner.nextLine();
//						if (nd.containsValue(str)){
//							break;
//						}
//						System.out.println("the destination you have entered is not in the system, select a destination to remove");
//					}
//					String number = "";
//					for (String num : nd.keySet()){
//						if (nd.get(num).equals(str)){
//							number = num;
//							break;
//						}
//					}
//					nf.remove(number);
//					nd.remove(number);
//					p.remove(str);
//					System.out.println("insert new weight");
//					String string = scanner.nextLine();
//					w = Integer.parseInt(string);
//					addDelivery(d, t, i, n, s, w, nf, nd, p);
//				};
				return "The destination entered does not exist. Operation code: 1";
			}
		}
		if (!checkAreas(numDest.values())){
//			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
//					HashMap<String,HashMap<String,Integer>> p)->{
//				Scanner scanner = new Scanner(System.in);
//				System.out.println("two destinations with different areas, please choose destinations to remove, insert \"s\" to stop");
//				while(true){
//					String str = scanner.nextLine();
//					if (str.equals("s"))
//						break;
//					if (!nd.containsValue(str)){
//						continue;
//					}
//					String number = "";
//					for (String num : nd.keySet()){
//						if (nd.get(num).equals(str)){
//							number = num;
//							break;
//						}
//					}
//					nf.remove(number);
//					nd.remove(number);
//					p.remove(str);
//				}
//				System.out.println("insert new weight");
//				String string = scanner.nextLine();
//				w = Integer.parseInt(string);
//				Callback c = addDelivery(d, t, i, n, s, w, nf, nd, p);
//				c.run(d ,t, i, n, s, w, nf, nd, p);
//			};
			return "Two destination with different areas. Operation code: 2";
		}
		if (!checkLicense(name, ID)){
//			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
//					HashMap<String,HashMap<String,Integer>> p)->{
//				Scanner scanner = new Scanner(System.in);
//				System.out.println("driver does not have a license for the truck, change the truck or driver: t/d");
//				String str = scanner.nextLine();
//				if (str.equals("t")){
//					System.out.println("new truck ID");
//					i = scanner.nextLine();
//					System.out.println("insert new weight");
//					String string = scanner.nextLine();
//					w = Integer.parseInt(string);
//				}
//				else{
//					System.out.println("new driver");
//					n = scanner.nextLine();
//				}
//				addDelivery(d, t, i, n, s, w, nf, nd, p);
//			};
			return "Driver does not have the right license for the truck. Operation code: 3";
		}
		if (!checkMaxWeight(ID, weight)){
//			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
//					HashMap<String,HashMap<String,Integer>> p)->{
//				Scanner scanner = new Scanner(System.in);
//				System.out.println("the chosen truck cannot drive with the given weight");
//				System.out.println("choose: change truck (t), remove destinations (d) or remove products (p)");
//				String str = scanner.nextLine();
//				switch (str) {
//					case "t":
//						System.out.println("enter new truck id");
//						i = scanner.nextLine();
//						break;
//					case "d":
//						System.out.println("please choose destinations to remove, insert \"s\" to stop");
//						while(true){
//							String dest = scanner.nextLine();
//							if (dest.equals("s"))
//								break;
//							if (!nd.containsValue(dest)){
//								continue;
//							}
//							String number = "";
//							for (String num : nd.keySet()){
//								if (nd.get(num).equals(dest)){
//									number = num;
//									break;
//								}
//							}
//							nf.remove(number);
//							nd.remove(number);
//							p.remove(dest);
//						}
//						break;
//					default:
//						while (true){
//							System.out.println("please choose destinations to remove items from, insert \"s\" to stop");
//							String dest = scanner.nextLine();
//							if (dest.equals("s"))
//								break;
//							if (!p.containsKey(dest)){
//								continue;
//							}
//							System.out.println("please choose a product to remove");
//							String product = scanner.nextLine();
//							System.out.println("please choose a quantity to remove");
//							int quantity = Integer.parseInt(scanner.nextLine());
//							if (quantity < p.get(dest).get(product)){
//								p.get(dest).put(product, p.get(dest).get(product) - quantity);
//							}
//							else if (quantity == p.get(dest).get(product)){
//								p.get(dest).remove(product);
//							}
//						}
//						break;
//				}
//				System.out.println("enter new weight");
//				w = Integer.parseInt(scanner.nextLine());
//				addDelivery(d, t, i, n, s, w, nf, nd, p);
//			};
			return "The given weight exceeds the max weight of the truck. Operation code: 4";
		}
		List<NumberedFile> numberedFileList=new LinkedList<>();

		for(String num:numberedFiles){
			numberedFileList.add(new NumberedFile(num,numDest.get(num),products.get(num)));
		}
		List<Destination> destinationList=new LinkedList<>();
		for(String dest:numDest.values()){
			destinationList.add(dControl.getDest(dest));
		}
		Delivery newDelivery = new Delivery(date, time, ID, name, source, weight,destinationList, numberedFileList);
		deliveries.add(newDelivery);
		mapper.saveDelivery(newDelivery.getId(),date,time,ID,name,source,weight,store_num,returnHour);

		for (NumberedFile numberedFile:newDelivery.getFiles()) {
			mapper.saveNumberedFile(numberedFile.getNumber(), numberedFile.getAddress(), newDelivery.getId());
			for (String product : numberedFile.getProductNames().keySet())
			{
				mapper.saveProduct(numberedFile.getNumber(),product,numberedFile.getProductNames().get(product),newDelivery.getId());
			}
		}


		/// -----------------------




		/// ================
		//	mapper.saveDelivery()
//		return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
//				HashMap<String,HashMap<String,Integer>> p)-> {};
		return "Added the delivery successfully. Operation code: 0";
	}



	private boolean checkAddress(String destination){
		return dControl.checkDestination(destination);
	}

	private boolean checkAreas(Collection<String> destinations){
		return dControl.checkAreas(destinations);
	}

	private boolean checkLicense(String name, String TID){
		switch (driverControl.getLicense(name)){
			case C1New: if (tControl.getWeight(TID) > 12){
				return false;
			}
			case C1Old: if (tControl.getWeight(TID) >  15){
				return false;
			}
			case NoLicense:
		}
		return true;
	}

	private boolean checkMaxWeight(String ID, int weight){
		return weight <= tControl.getMaxWeight(ID);
	}

	public void addDriver(License license, String name, int id) {
		driverControl.addDriver(license,name,id);
	}

	public void addTruck(String id, int weight, int maxWeight, String model) {
		tControl.addTruck(id,weight,maxWeight,model);
	}
}


//Final version