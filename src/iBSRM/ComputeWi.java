package iBSRM;

import java.util.ArrayList;
import java.util.HashMap;

public interface ComputeWi {
	public HashMap<HashMap<String, String>, Double> computeWi(ArrayList<UserBean> userList, ArrayList<WebServiceBean> webServiceList, 
			HashMap<HashMap<String, String>, Integer> ll2Num, HashMap<HashMap<String, String>, Integer> ll2C0, double preset_value);
}
