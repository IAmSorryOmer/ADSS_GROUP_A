package BL;

import DAL.DDriver;
import DAL.Mapper;

import java.util.LinkedList;
import java.util.List;

public class DriverController {

	private List<Driver> drivers;

	public DriverController(){
		drivers = new LinkedList<>();
	}


	public void addDriver(License license, String name){
		drivers.add(new Driver(license,name));
	}
	public License getLicense(String name){
		if (getDriver(name) == null)
			return License.NoLicense;
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

    public void load(int store_num, Mapper mapper) {
		List<DDriver> dDrivers= mapper.getDrivers(store_num);
		for(int i=0;i< dDrivers.size();++i){
			Driver x=new Driver(dDrivers.get(i));
			drivers.add(x);
		}
    }


}

//Final version