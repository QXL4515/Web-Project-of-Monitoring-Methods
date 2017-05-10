package BSRM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ComputeWi_C0 implements ComputeWi{
	public HashMap<HashMap<String, String>, Double> computeWi(ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList, 
			HashMap<HashMap<String, String>, Integer> ll2Num, HashMap<HashMap<String, String>, Integer> ll2C0, double preset_value){
		System.out.println("Begin to computeWi_C0...");
		long begin = System.currentTimeMillis();
		
		HashMap<HashMap<String, String>, Double> ll2W0Temp = new HashMap<HashMap<String, String>, Double>();
		int n = userList.size() * webServiceList.size();//记录所有数据个数
		Iterator<Entry<HashMap<String, String>, Integer>> c0Iterator = ll2C0.entrySet().iterator();
		int nCi = 0;//记录所有 c0/c1 的个数
		while(c0Iterator.hasNext()){
			Entry<HashMap<String, String>, Integer> entry = c0Iterator.next();
			nCi += entry.getValue();
		}
		
		Iterator<Entry<HashMap<String, String>, Integer>> numIterator = ll2Num.entrySet().iterator();
		while(numIterator.hasNext()){
			Entry<HashMap<String, String>, Integer> entry = numIterator.next();
			if(ll2C0.containsKey(entry.getKey())){
				int nCiRi = ll2C0.get(entry.getKey());
				int nRi = entry.getValue();
				double wi = nCiRi * 1.0 / nCi * Math.log(n / nRi);
				ll2W0Temp.put(entry.getKey(), wi);
			}else{ 
				ll2W0Temp.put(entry.getKey(), preset_value);
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("End to computeWi_C0, takes" + new ComputeTime().computeTime(begin, end) + "ms...");
		return ll2W0Temp;
	}
}
