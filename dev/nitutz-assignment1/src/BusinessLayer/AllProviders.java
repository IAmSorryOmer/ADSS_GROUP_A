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
	public boolean addProvider(Provider p) {
		return providers.add(p);
	}
	public Provider getProvidersByID(String ID){
		for(Provider prov : providers) {
			if(prov.getProviderID().equals(ID))
				return prov;
		}
		return null;
	}
	
}
