package BL;



import sun.security.jca.GetInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class alerter {

	private static alerter instance;
	private List<String> logisticManagerMessages;
	private List<String> hrManagerMessages;
	private Map<Integer, Map<String, List<String>>> messages;

	private alerter(){
		messages = new HashMap();
		logisticManagerMessages = new LinkedList<>();
		hrManagerMessages = new LinkedList<>();
	}

	public static alerter GetInstance(){
		if (instance == null){
			instance = new alerter();
		}
		return instance;
	}

	public List<String> getMessages(int storeNum, String[] capableJobs){
		List<String> ret = new LinkedList<>();
		for (String job:capableJobs) {
			ret.addAll(messages.get(storeNum).get(job));
		}
		return ret;
	}

	public List<String> getLogisticManagerMessages(){
		return logisticManagerMessages;
	}

	public List<String> getHrManagerMessages(){
		return hrManagerMessages;
	}

	public void addMessages(int storeNum, String job, List<String> toAdd){
		messages.get(storeNum).get(job).addAll(toAdd);
	}

	public void addHrManagerMessages(List<String> toAdd){
		hrManagerMessages.addAll(toAdd);
	}

	public void addLogisticManagerMessages(List<String> toAdd){
		logisticManagerMessages.addAll(toAdd);
	}

	public void removeMessages(int storeNum, String job, List<String> toRemove){
		messages.get(storeNum).get(job).removeAll(toRemove);
	}

	public void removeHrManagerMessages(List<String> toRemove){
		hrManagerMessages.removeAll(toRemove);
	}

	public void removeLogisticManagerMessages(List<String> toRemove){
		logisticManagerMessages.removeAll(toRemove);
	}
}
