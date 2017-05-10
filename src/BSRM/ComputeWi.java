package BSRM;

import java.util.ArrayList;
import java.util.HashMap;

public interface ComputeWi {
	public HashMap<HashMap<String, String>, Double> computeWi(ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList, 
			HashMap<HashMap<String, String>, Integer> ll2Num, HashMap<HashMap<String, String>, Integer> ll2C0, double preset_value);
}
