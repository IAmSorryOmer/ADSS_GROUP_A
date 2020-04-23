
import java.util.LinkedList;
import java.util.List;

public class DriverController {
	static DriverController instance;
	private List<Driver> drivers;

	private DriverController(){
		drivers = new LinkedList<>();
	}

	public static  DriverController getInstance(){
		if (instance == null){
			instance = new DriverController();
		}
		return instance;
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
}
//Final version