package BSRM;

import java.util.ArrayList;
import java.util.HashMap;

public interface ComputePrePro_CX {
	public double computePrePro_CX(double plC1, int x, int y, 
			ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			HashMap<HashMap<String, String>, Double> ll2Wi_Ci, int[][] tp);
}
