package InterfaceLayer;

import java.util.LinkedList;
import java.util.Scanner;

public class mainRunner{
public static Scanner reader = new Scanner(System.in);

public static void main(String[] args) {
	ProviderInterface.ProviderCreator("1", "1234123412341234", false, 0, new LinkedList<String>(), "Bob", false, "0541212312", "SomeAddress 1", 0);
	ProviderInterface.ProviderCreator("2", "1234123412341235", false, 0, new LinkedList<String>(), "Jhon", false, "0541444312", "SomeAddress 2", 0);
	ProviderInterface.ProviderCreator("3", "1767673412341234", false, 0, new LinkedList<String>(), "Rose", false, "0541287654", "SomeAddress 3", 0);
	ProviderInterface.ProviderCreator("4", "9874123412341234", false, 0, new LinkedList<String>(), "Iris", false, "0566662312", "SomeAddress 1", 0);
	ProviderInterface.addItem("1", 0, "10", 10.99);
	ProviderInterface.addItem("1", 0, "12", 99.99);
	ProviderInterface.addItem("1", 0, "11", 29.99);
	ProviderInterface.addItem("2", 0, "10", 10.99);
	ProviderInterface.addItem("4", 0, "17", 1.99);
	
	
	while(true) {
		System.out.println("\nChoose Option");
		String choice = reader.nextLine();
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
				break;
			case "5":
				System.out.println("\nID:");
				String ID2 = reader.nextLine();
				System.out.println("\nProductIDInCatalog:");
				String catalogItemID = reader.nextLine();
				System.out.println("\nAmount");
				int Amount = reader.nextInt();
				OrdersInterface.AddToOrder(ID2, catalogItemID, Amount);
				break;
			case "6":
				System.out.println("\nID:");
				String ID3 = reader.nextLine();
				System.out.println("\nProductIDInCatalog:");
				String catalogItemID1 = reader.nextLine();
				System.out.println("\nAmount");
				int Amount2 = reader.nextInt();
				OrdersInterface.EditOrder(ID3, catalogItemID1, Amount2);
				break;
			case "7":
				System.exit(0);
		}
	}
}
}
