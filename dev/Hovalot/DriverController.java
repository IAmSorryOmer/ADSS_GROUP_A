package Hovalot;

import java.util.LinkedList;

public class DriverController {
	static DriverController instance;
	private List<Driver> drivers;

	private DriverController(){
		drivers = new LinkedList<>();
	}

	static public DriverController getInstance(){
		if (instance == null){
			instance = new DriverController();
		}
		return instance;
	}

	public License getLicense(String name){
		return getDriver(name).getLicense();
	}

	private Driver getDriver(String name){
		for (Driver driver : drivers){
			if (driver.getName().equals(name)){
				return driver;
			}
		}
		return null;
	}
}
