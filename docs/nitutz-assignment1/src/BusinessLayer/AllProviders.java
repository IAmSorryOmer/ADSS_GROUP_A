package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

public class AllProviders {
	private List<Provider> providers;
	
	private AllProviders() {
		providers  = new LinkedList<Provider>();
	}
	private static class SingeltonHolder {
		private static AllProviders instance = new AllProviders();
	}
	public static AllProviders getInstance() {
		return SingeltonHolder.instance;
	}
	public static boolean addProvider(AllProviders ap, Provider p) {
		return ap.providers.add(p);
	}
	public static List<Provider> getProvidersByID(AllProviders ap, String ID){
		List<Provider> list = new LinkedList<Provider>();
		for(Provider prov : ap.providers) {
			if(prov.getProviderID().equals(ID))
				list.add(prov);
		}
		return list;
	}
}
