package iBSRM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputePrePro_C0 implements ComputePrePro_CX{
	public double computePrePro_CX(double plC0, int x, int y, ArrayList<UserBean> userList, 
			ArrayList<WebServiceBean> webServiceList, int[][] tp){
		//计算先验概率
		int temp = tp[x][y];

		double pXiC0 = Math.pow(plC0, temp) * Math.pow(1 - plC0, (1 - temp));
		HashMap<String, String> ll = new HashMap<String, String>();
		ll.put(userList.get(x).getNation(), webServiceList.get(y).getNation());
	double wi_pXiC0 = Math.log(1 - pXiC0) + temp * Math.log(pXiC0/(1 - pXiC0));
		return wi_pXiC0;
	}
}
