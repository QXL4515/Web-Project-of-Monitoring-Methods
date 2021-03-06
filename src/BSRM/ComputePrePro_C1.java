package BSRM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputePrePro_C1 implements  ComputePrePro_CX{
	public double computePrePro_CX(double plC1, int x, int y, 
			ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			HashMap<HashMap<String, String>, Double> ll2Wi_C1, int[][] tp){
		//�����������
		int temp = tp[x][y];
		String W;
	//	System.out.println( tp[x][y]);
		double pXiC1 = Math.pow(plC1, temp) * Math.pow((1 - plC1), (1 - temp));
		HashMap<String, String> ll = new HashMap<String, String>();
		ll.put(userList.get(x).getNation(), webServiceList.get(y).getNation());
		String LL = ll.put(userList.get(x).getNation(), webServiceList.get(y).getNation());
		
		double wi_c1 = ll2Wi_C1.get(ll) ;
		
		W = "*" + wi_c1;
		double wi_pXiC1 =wi_c1 * ( Math.log(1 - pXiC1) + temp *  Math.log(pXiC1/(1 - pXiC1)));
		return wi_pXiC1;
	}
}
