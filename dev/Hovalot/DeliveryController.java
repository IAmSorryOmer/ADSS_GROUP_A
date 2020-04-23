import java.util.*;

public class DeliveryController {

	private static DeliveryController instance;
	private List<Delivery> deliveries;
	private DestController dControl;
	private DriverController driverControl;
	private TruckController tControl;

	private DeliveryController(){
		deliveries = new LinkedList<>();
		dControl = DestController.getInstance();
		driverControl = DriverController.getInstance();
		tControl = TruckController.getInstance();
	}

	static public DeliveryController getInstance(){
		if (instance == null){
			instance = new DeliveryController();
		}
		return instance;
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


	public Callback addDelivery(String date, String time, String ID, String name, String source, int weight,
								 List<String> numberedFiles, HashMap<String,String> numDest,
								HashMap<String,HashMap<String,Integer>> products){
		for (String destination: numDest.values()){
			if (!checkAddress(destination)){
				return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
						HashMap<String,HashMap<String,Integer>> p)-> {
					Scanner scanner = new Scanner(System.in);
					String str = "";
					while (true){
						str = scanner.nextLine();
						if (nd.containsValue(str)){
							break;
						}
						System.out.println("the destination you have entered is not in the system, select a destination to remove");
					}
					String number = "";
					for (String num : nd.keySet()){
						if (nd.get(num).equals(str)){
							number = num;
							break;
						}
					}
					nf.remove(number);
					nd.remove(number);
					p.remove(str);
					System.out.println("insert new weight");
					String string = scanner.nextLine();
					w = Integer.parseInt(string);
					addDelivery(d, t, i, n, s, w, nf, nd, p);
				};
			}
		}
		if (!checkAreas(numDest.values())){
			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
					HashMap<String,HashMap<String,Integer>> p)->{
				Scanner scanner = new Scanner(System.in);
				System.out.println("two destinations with different areas, please choose destinations to remove, insert \"s\" to stop");
				while(true){
					String str = scanner.nextLine();
					if (str.equals("s"))
						break;
					if (!nd.containsValue(str)){
						continue;
					}
					String number = "";
					for (String num : nd.keySet()){
						if (nd.get(num).equals(str)){
							number = num;
							break;
						}
					}
					nf.remove(number);
					nd.remove(number);
					p.remove(str);
				}
				System.out.println("insert new weight");
				String string = scanner.nextLine();
				w = Integer.parseInt(string);
				addDelivery(d, t, i, n, s, w, nf, nd, p);
			};
		}
		if (!checkLicense(name, ID)){
			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
					HashMap<String,HashMap<String,Integer>> p)->{
				Scanner scanner = new Scanner(System.in);
				System.out.println("driver does not have a license for the truck, change the truck or driver: t/d");
				String str = scanner.nextLine();
				if (str.equals("t")){
					System.out.println("new truck ID");
					i = scanner.nextLine();
					System.out.println("insert new weight");
					String string = scanner.nextLine();
					w = Integer.parseInt(string);
				}
				else{
					System.out.println("new driver");
					n = scanner.nextLine();
				}
				addDelivery(d, t, i, n, s, w, nf, nd, p);
			};
		}
		if (!checkMaxWeight(ID, weight)){
			return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
					HashMap<String,HashMap<String,Integer>> p)->{
				Scanner scanner = new Scanner(System.in);
				System.out.println("the chosen truck cannot drive with the given weight");
				System.out.println("choose: change truck (t), remove destinations (d) or remove products (p)");
				String str = scanner.nextLine();
				switch (str) {
					case "t":
						System.out.println("enter new truck id");
						i = scanner.nextLine();
						break;
					case "d":
						System.out.println("please choose destinations to remove, insert \"s\" to stop");
						while(true){
							String dest = scanner.nextLine();
							if (dest.equals("s"))
								break;
							if (!nd.containsValue(dest)){
								continue;
							}
							String number = "";
							for (String num : nd.keySet()){
								if (nd.get(num).equals(dest)){
									number = num;
									break;
								}
							}
							nf.remove(number);
							nd.remove(number);
							p.remove(dest);
						}
						break;
					default:
						while (true){
							System.out.println("please choose destinations to remove items from, insert \"s\" to stop");
							String dest = scanner.nextLine();
							if (dest.equals("s"))
								break;
							if (!p.containsKey(dest)){
								continue;
							}
							System.out.println("please choose a product to remove");
							String product = scanner.nextLine();
							System.out.println("please choose a quantity to remove");
							int quantity = Integer.parseInt(scanner.nextLine());
							if (quantity < p.get(dest).get(product)){
								p.get(dest).put(product, p.get(dest).get(product) - quantity);
							}
							else if (quantity == p.get(dest).get(product)){
								p.get(dest).remove(product);
							}
						}
						break;
				}
				System.out.println("enter new weight");
				w = Integer.parseInt(scanner.nextLine());
				addDelivery(d, t, i, n, s, w, nf, nd, p);
			};
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
		return (String d, String t, String i, String n, String s, int w, List<String> nf, HashMap<String,String> nd,
				HashMap<String,HashMap<String,Integer>> p)-> {};
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
}//Final version