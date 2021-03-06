package BSRM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputePrePro_C0 implements ComputePrePro_CX{
	public double computePrePro_CX(double plC0, int x, int y, 
			ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			HashMap<HashMap<String, String>, Double> ll2Wi_C0, int[][] tp){
		//�����������
		int temp = tp[x][y];
		String E = "" + temp;
		 try {
  		   FileWriter fw = new FileWriter("D:/test_zhuang_new/YorN_QoS/test_YorN_QoS.txt",true);
  		   BufferedWriter bw = new BufferedWriter(fw);
//  		   bw.write(E +'\n');
  		 bw.write(E);
  		   bw.newLine();
  		   bw.flush();
  		   fw.close();
  		   bw.close();
  	 }catch (Exception e) {
  		   // TODO Auto-generated catch block
  		   e.printStackTrace();
  		  }
		double pXiC0 = Math.pow(plC0, temp) * Math.pow(1 - plC0, (1 - temp));
		HashMap<String, String> ll = new HashMap<String, String>();
		ll.put(userList.get(x).getNation(), webServiceList.get(y).getNation());
		double wi_c0 = ll2Wi_C0.get(ll) ;
		String W;
		String LL = ll.put(userList.get(x).getNation(), webServiceList.get(y).getNation());
		double wi_pXiC0 = wi_c0 * (Math.log(1 - pXiC0) + temp * Math.log(pXiC0/(1 - pXiC0)));
		return wi_pXiC0;
	}
}
