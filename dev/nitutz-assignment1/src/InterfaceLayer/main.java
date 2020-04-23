package InterfaceLayer;

import java.util.LinkedList;
import java.util.Scanner;

public class main {
	public static Scanner reader = new Scanner(System.in);
public static void Main(String[] args) {
	String choice = "";
	while(true) {
		switch (choice) {
			case "1":
				System.out.println("\nname:");
				String name = reader.nextLine();
				System.out.println("\nID:");
				String id = reader.nextLine();
				System.out.println("\nCreditCard");
				String card = reader.nextLine();
				System.out.println("\nneeds transport(true/false):");
				boolean needTransport = Boolean.parseBoolean((reader.nextLine()));
				System.out.println("\ndelayDays?");
				int DelayDays = Integer.parseInt(reader.nextLine());
				System.out.println("\nisFixedDays?:");
				boolean IsFixedDays = Boolean.parseBoolean((reader.nextLine()));
				System.out.println("\nPhone:");
				String PhoneNum = reader.nextLine();
				System.out.println("\nAddress:");
				String Address = reader.nextLine();
				System.out.println("\nquantityForDiscount(0 if not specified in contract):");
				int quantityForDiscount = Integer.parseInt(reader.nextLine());
				ProviderInterface.ProviderCreator(id, card, needTransport, DelayDays, new LinkedList<String>(), name, IsFixedDays, PhoneNum, Address, quantityForDiscount);
				break;
			case "3":
				System.out.println("\nID:");
				String ID = reader.nextLine();
				System.out.println(ProviderInterface.printDetails(ID));
				break;
			case "2":
				System.out.println("\nID:");
				String id1 = reader.nextLine();
				System.out.println("\nname:");
				String name1 = reader.nextLine();
				System.out.println("\nCreditCard");
				String card1 = reader.nextLine();
				System.out.println("\nneeds transport(true/false):");
				boolean needTransport1 = Boolean.parseBoolean((reader.nextLine()));
				System.out.println("\ndelayDays?");
				int DelayDays1 = Integer.parseInt(reader.nextLine());
				System.out.println("\nisFixedDays?:");
				boolean IsFixedDays1 = Boolean.parseBoolean((reader.nextLine()));
				System.out.println("\nPhone:");
				String PhoneNum1 = reader.nextLine();
				System.out.println("\nAddress:");
				String Address1 = reader.nextLine();
				System.out.println("\nquantityForDiscount(0 if not specified in contract):");
				int quantityForDiscount1 = Integer.parseInt(reader.nextLine());
				ProviderInterface.editDetails(id1, needTransport1, DelayDays1, new LinkedList<String>(), name1);
				ProviderInterface.editDetails(id1, IsFixedDays1, PhoneNum1, Address1, quantityForDiscount1);
				break;
			case "4":
				System.out.println("\nID:");
				String ID1 = reader.nextLine();
				System.out.println(OrdersInterface.printItems(ID1));
			case "7":
				System.exit(0);
		}
	}
}
}
