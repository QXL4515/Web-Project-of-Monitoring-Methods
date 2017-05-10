package BSRM;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;



public class Main  extends HttpServlet{
	//目标β值
	private static final double BETA = 0.8; 
	private static final double QOS_VALUE = 10.0;
	private static final int DISTANCE = 1000;
	private static final double PERSET_VALUE = 0.0;
	private static final int T = 1000;
	private static ArrayList<UserInformation> userList = new ArrayList<UserInformation>();
	private static ArrayList<WebServiceInformation> webServiceList = new ArrayList<WebServiceInformation>();
	private static HashMap<HashMap<String, String>, Integer> ll2Num = new HashMap<HashMap<String, String>, Integer>();//记录所有国家组合的总数 
	private static HashMap<HashMap<String, String>, Integer> ll2C0 = new HashMap<HashMap<String, String>, Integer>();//记录所有国家组合的C0个数
	private static HashMap<HashMap<String, String>, Integer> ll2C1 = new HashMap<HashMap<String, String>, Integer>();//记录所有国家组合的C1个数
	private static HashMap<HashMap<String, String>, Double> ll2Wi_C0 = new HashMap<HashMap<String, String>, Double>();//记录所有国家组合的Wi_C0
	private static HashMap<HashMap<String, String>, Double> ll2Wi_C1 = new HashMap<HashMap<String, String>, Double>();//记录所有国家组合的Wi_C1
	private static double[][] rtData, tpData;
	
	private static int shortMonlength=500;
//	private static double record[];
	private static Vector<Double> record_NQoS=new Vector<Double>();
	private static Vector<Double> recordpreProC0=new Vector<Double>();
	private static Vector<Double> recordpreProC1=new Vector<Double>();
	private static Vector<Integer> YorN=new Vector<Integer>();
	
	private static int[][] tp, rt;
	private static int nC0Xl, nC1Xl, nC0, nC1;// Xl 表示 Xk = 1
	private static double prePro_C0 = 1.0;
	private static double prePro_C1 = 1.0;
		
	public Main(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if("sprt".equals(action)){
		} else if("ibsrm".equals(action)){
			//Start_js(request, response);
			Making_ibsrm(request,response);

		} else if("wbsrm".equals(action)){
			//Start_js(request, response);
			Making_wbsrm(request,response);

		} else if("1".equals(action)){
			//Start_js(request, response);
			Making_1(request,response);

		} else if("2".equals(action)){
			//Start_js(request, response);
			Making_2(request,response);

		}
		
	}
	
	
	private void Making_1(HttpServletRequest request, HttpServletResponse response){
		ArrayList xList = new ArrayList();
		ArrayList yList = new ArrayList();
		try{
			FileInputStream fis = new FileInputStream("C:\\test_zhuang_new\\test_BETA.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String[] split=null;
			boolean flag=true;
			String str = br.readLine();
			ArrayList<Double> list = new ArrayList<Double>();
			 double α=0.05;
			 double β=0.1;
			double P0 = 0.9;
			 double P1 = 0.75;
			Double C0=(double) 1;
			Double C1=(double) -1;
			Double C2=(double) 0;
			Double A = Math.log((1-β)/α);
			Double B = Math.log(β/(1-α));
			Double F = (double) 0;

			
			while(str!=null && !"".equals(str.trim())){
				split = str.split("\r\n");
				str = br.readLine();
				list.add(Double.valueOf(split[0]));
			}
			for(int j=0;j<list.size();j++){
				if(j==84){
					break;
				}
				//α,β
				if(A < F+list.get(j)*Math.log(P1/P0) + (1-list.get(j))*(Math.log((1-P1)/(1-P0)))){
					xList.add(Double.valueOf(j));
					yList.add(C1);
				}else if (B > F+list.get(j)*Math.log(P1/P0) + (1-list.get(j))*(Math.log((1-P1)/(1-P0)))){
					xList.add(Double.valueOf(j));
					yList.add(C0);
				}else{
					xList.add(Double.valueOf(j));
					yList.add(C2);
				}
			}
			request.setAttribute("xList", xList);
			request.setAttribute("yList", yList);
			
			JSONArray jsonx = JSONArray.fromObject(xList);
			JSONArray jsony = JSONArray.fromObject(yList);
			JSONObject arrays = new JSONObject();
			arrays.put("jsonx", jsonx);
			JSONObject joy = new JSONObject();
			joy.put("name", "SPRT");
			joy.put("data", jsony);
			JSONArray arr = new JSONArray();
			arr.add(joy);
			JSONObject ob = new JSONObject();
			ob.put("jsonx", jsonx);
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			JSONObject jo = new JSONObject();
			jo.put("name", "SPRT");
			jo.put("data", jsony);
			session.setAttribute("4", jo);
			
			if(session.getAttribute("1")!=null) {
				JSONObject arr1 = (JSONObject)session.getAttribute("1");
				arr.add(arr1);
			} 
			if(session.getAttribute("2")!=null) {
				JSONObject arr2 = (JSONObject)session.getAttribute("2");
				arr.add(arr2);
			}
			if(session.getAttribute("3")!=null) {
				JSONObject arr4 = (JSONObject)session.getAttribute("3");
				arr.add(arr4);
			}
			if(session.getAttribute("5")!=null) {
				JSONObject arr5 = (JSONObject)session.getAttribute("5");
				arr.add(arr5);
			}


			ob.put("jsony", arr);
			
			out.write(ob.toString());
			//request.getRequestDispatcher("Main.jsp").forward(request, response);
		}catch(Exception e){
			
		}
		
	}
	
	
	private void Making_2(HttpServletRequest request, HttpServletResponse response){
		ArrayList xList = new ArrayList();
		ArrayList yList = new ArrayList();
		try{
			FileInputStream fis = new FileInputStream("C:\\test_zhuang_new\\test_BETA.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String[] split=null;
			boolean flag=true;
			String str = br.readLine();
			ArrayList<Double> list = new ArrayList<Double>();
			 double α=0.05;
			 double β=0.1;
			double P0 = 0.9;
			 double P1 = 0.75;
			Double C0=(double) 1;
			Double C1=(double) -1;
			Double C2=(double) 0;
			Double A = Math.log((1-β)/α);
			Double B = Math.log(β/(1-α));
			Double F = (double) 0;

			
			while(str!=null && !"".equals(str.trim())){
				split = str.split("\r\n");
				str = br.readLine();
				list.add(Double.valueOf(split[0]));
			}
			for(int j=0;j<list.size();j++){
				if(j==84){
					break;
				}
				//α,β
				if(list.get(j) > (-Math.log(((1-P1)/(1-P0))*j)+Math.log(β/(1-α))) / Math.log((P1*(1-P0)))/(P0*(1-P1))){

					xList.add(Double.valueOf(j));
					yList.add(C0);
				}else if (list.get(j) < ((-Math.log((1-P1)/(1-P0))*j)+Math.log((1-β)/α)) / Math.log((P1*(1-P0)))/(P0*(1-P1))){
					xList.add(Double.valueOf(j));
					yList.add(C1);
				}else{
					xList.add(Double.valueOf(j));
					yList.add(C2);
				}
//				mDataset.add(Double.valueOf(j),(Double) list.get(j));
			}
			request.setAttribute("xList", xList);
			request.setAttribute("yList", yList);
			
		
			JSONArray jsonx = JSONArray.fromObject(xList);
			JSONArray jsony = JSONArray.fromObject(yList);
			JSONObject arrays = new JSONObject();
			arrays.put("jsonx", jsonx);
			//arrays.put("jsony", jsony);
			JSONObject joy = new JSONObject();
			joy.put("name", "ProMo");
			joy.put("data", jsony);
			JSONArray arr = new JSONArray();
			arr.add(joy);
			JSONObject ob = new JSONObject();
			ob.put("jsonx", jsonx);
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			JSONObject jo = new JSONObject();
			jo.put("name", "ProMo");
			jo.put("data", jsony);
			session.setAttribute("5", jo);
			
			if(session.getAttribute("1")!=null) {
				JSONObject arr1 = (JSONObject)session.getAttribute("1");
				arr.add(arr1);
			} 
			if(session.getAttribute("2")!=null) {
				JSONObject arr2 = (JSONObject)session.getAttribute("2");
				arr.add(arr2);
			}
			if(session.getAttribute("3")!=null) {
				JSONObject arr4 = (JSONObject)session.getAttribute("3");
				arr.add(arr4);
			}
			if(session.getAttribute("4")!=null) {
				JSONObject arr5 = (JSONObject)session.getAttribute("4");
				arr.add(arr5);
			}


			ob.put("jsony", arr);
			
			out.write(ob.toString());

			//request.getRequestDispatcher("Main.jsp").forward(request, response);
		}catch(Exception e){
			
		}
		
	}
	
	private void Making_wbsrm(HttpServletRequest request, HttpServletResponse response){
		ArrayList xList = new ArrayList();
		ArrayList yList = new ArrayList();
		try{
			FileInputStream fis = new FileInputStream("C:\\test_zhuang_new\\test_BETA.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String[] split=null;
			boolean flag=true;
			String str = br.readLine();
			ArrayList<String> list = new ArrayList<String>();
			int j=0;
			while(str!=null && !"".equals(str.trim())){
				split = str.split("\r\n");
				str = br.readLine();
				list.add(split[0]);
			}
			int m=0;
			for(int i= 0; i< list.size();i++){
				if(xList.size()==30){
					break;
				}
				j=i;
				m =Integer.parseInt(list.get(i)); // m+Integer.parseInt(list.get(i));
				while(j%4 == 0){
					xList.add(i/4);
					yList.add(m);
					j++;
//					m=0;
				}
			}
			request.setAttribute("xList", xList);
			request.setAttribute("yList", yList);
			
			JSONArray jsonx = JSONArray.fromObject(xList);
			JSONArray jsony = JSONArray.fromObject(yList);
			JSONObject arrays = new JSONObject();
			arrays.put("jsonx", jsonx);
			//arrays.put("jsony", jsony);
			JSONObject joy = new JSONObject();
			joy.put("name", "WBSRM");
			joy.put("data", jsony);
			JSONArray arr = new JSONArray();
			arr.add(joy);
			JSONObject ob = new JSONObject();
			ob.put("jsonx", jsonx);
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			JSONObject jo = new JSONObject();
			jo.put("name", "WBSRM");
			jo.put("data", jsony);
			session.setAttribute("3", jo);
			
			if(session.getAttribute("1")!=null) {
				JSONObject arr1 = (JSONObject)session.getAttribute("1");
				arr.add(arr1);
			} 
			if(session.getAttribute("2")!=null) {
				JSONObject arr2 = (JSONObject)session.getAttribute("2");
				arr.add(arr2);
			}
			if(session.getAttribute("4")!=null) {
				JSONObject arr4 = (JSONObject)session.getAttribute("4");
				arr.add(arr4);
			}
			if(session.getAttribute("5")!=null) {
				JSONObject arr5 = (JSONObject)session.getAttribute("5");
				arr.add(arr5);
			}


			ob.put("jsony", arr);
			
			out.write(ob.toString());
			//request.getRequestDispatcher("Main.jsp").forward(request, response);
		}catch(Exception e){
			
		}
		
	}
	
	private void Making_ibsrm(HttpServletRequest request, HttpServletResponse response){
		ArrayList xList = new ArrayList();
		ArrayList yList = new ArrayList();
		try{
			FileInputStream fis = new FileInputStream("C:\\test_zhuang_new\\IBSRM.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String[] split=null;
			boolean flag=true;
			String str = br.readLine();
			ArrayList<String> list = new ArrayList<String>();
			int j=0;
			while(str!=null && !"".equals(str.trim())){
				split = str.split("\r\n");
				str = br.readLine();
				list.add(split[0]);
			}
			int m=0;
			for(int i= 0; i< list.size();i++){
				if(xList.size()==30){
					break;
				}
				j=i;
				m =Integer.parseInt(list.get(i)); // m+Integer.parseInt(list.get(i));
				while(j%4 == 0){
					xList.add(i/4);
					yList.add(m);
					j++;
//					m=0;
				}
			}
			request.setAttribute("xList", xList);
			request.setAttribute("yList", yList);
			
			JSONArray jsonx = JSONArray.fromObject(xList);
			JSONArray jsony = JSONArray.fromObject(yList);
			JSONObject arrays = new JSONObject();
			arrays.put("jsonx", jsonx);
			//arrays.put("jsony", jsony);
			//PrintWriter out = response.getWriter();
			JSONObject joy = new JSONObject();
			joy.put("name", "iBSRM");
			joy.put("data", jsony);
			JSONArray arr = new JSONArray();
			arr.add(joy);
			JSONObject ob = new JSONObject();
			ob.put("jsonx", jsonx);
			
			
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			JSONObject jo = new JSONObject();
			jo.put("name", "iBSRM");
			jo.put("data", jsony);
			session.setAttribute("2", jo);
			
			if(session.getAttribute("1")!=null) {
				JSONObject arr2 = (JSONObject)session.getAttribute("1");
				arr.add(arr2);
			}
			if(session.getAttribute("3")!=null) {
				JSONObject arr3 = (JSONObject)session.getAttribute("3");
				arr.add(arr3);
			}
			if(session.getAttribute("4")!=null) {
				JSONObject arr4 = (JSONObject)session.getAttribute("4");
				arr.add(arr4);
			}
			if(session.getAttribute("5")!=null) {
				JSONObject arr5 = (JSONObject)session.getAttribute("5");
				arr.add(arr5);
			}
			
			
			ob.put("jsony", arr);
			
			out.write(ob.toString());
			//request.getRequestDispatcher("Main.jsp").forward(request, response);
		}catch(Exception e){
			
		}
		
	}
	
	private void Making_sprt(HttpServletRequest request, HttpServletResponse response){
		ArrayList xList = new ArrayList();
		ArrayList yList = new ArrayList();
		try{
			FileInputStream fis = new FileInputStream("C:\\test_zhuang_new\\YorN_QoS\\test_YorN_QoS.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String[] split=null;
			boolean flag=true;
			String str = br.readLine();
			ArrayList<String> list = new ArrayList<String>();
			int j=0;
			while(str!=null && !"".equals(str.trim())){
				split = str.split("\r\n");
				str = br.readLine();
				list.add(split[0]);
			}
			int m=0;
			for(int i= 0; i< list.size();i++){
				if(xList.size()==30){
					break;
				}
				j=i;
				m =Integer.parseInt(list.get(i));
				while(j%4 == 0){
					xList.add(i/4);
					yList.add(m);
					j++;
//					m=0;
				}
			}
			request.setAttribute("xList", xList);
			request.setAttribute("yList", yList);
			
			JSONArray jsonx = JSONArray.fromObject(xList);
			JSONArray jsony = JSONArray.fromObject(yList);
			JSONObject arrays = new JSONObject();
			arrays.put("jsonx", jsonx);
			//arrays.put("jsony", jsony);
			JSONObject joy = new JSONObject();
			joy.put("name", "Chan");
			joy.put("data", jsony);
			JSONArray arr = new JSONArray();
			arr.add(joy);
			JSONObject ob = new JSONObject();
			ob.put("jsonx", jsonx);
			
			
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			JSONObject jo = new JSONObject();
			jo.put("name", "Chan");
			jo.put("data", jsony);
			session.setAttribute("1", jo);
			
			if(session.getAttribute("2")!=null) {
				JSONObject arr2 = (JSONObject)session.getAttribute("2");
				arr.add(arr2);
			}
			if(session.getAttribute("3")!=null) {
				JSONObject arr3 = (JSONObject)session.getAttribute("3");
				arr.add(arr3);
			} 
			if(session.getAttribute("4")!=null) {
				JSONObject arr4 = (JSONObject)session.getAttribute("4");
				arr.add(arr4);
			}
			if(session.getAttribute("5")!=null) {
				JSONObject arr5 = (JSONObject)session.getAttribute("5");
				arr.add(arr5);
			}
			
			ob.put("jsony", arr);
			//out.write(arrays.toString());
			out.write(ob.toString());
			//request.getRequestDispatcher("Main.jsp").forward(request, response);
		}catch(Exception e){
			
		}
		
	}
	
	private   void Start_js(HttpServletRequest request, HttpServletResponse response)  {
		nC0 = 0;
		nC1 = 0;
		long begin_n = 0;
		long end_n = 0;
		readData(USERINFORMATION_DATA_PATH, WSINFORMATION_DATA_PATH, RTINFORMATION_DATA_PATH, TPINFORMATION_DATA_PATH);//读取文件数据
		traversalMatrix(userList, webServiceList, tpData);//处理tp数据，如果需要处理rt数据则把二维数组换成rtdata
	
	    ll2Wi_C0 = new ComputeWi_C0().computeWi(userList, webServiceList, ll2Num, ll2C0, PERSET_VALUE);//计算Wi_C0
		ll2Wi_C1 = new ComputeWi_C1().computeWi(userList, webServiceList, ll2Num, ll2C1, PERSET_VALUE);//计算Wi_C1
	

	
		
		double plC0 = new ComputePlC0().computePlCX(nC0Xl, nC0);//计算plC0"
		double plC1 = new ComputePlC1().computePlCX(nC1Xl, nC1);//计算plC1"
		
		
	    System.out.println("plC0" + plC0);
	    System.out.println("plC1" + plC1);
		nC0 = 0;
		nC1 = 0;
		int n = 0;
		int count = 0;
		int x = 0;
		double K;
		String R0;
		String R1;
		begin_n = System.currentTimeMillis();
		
		for(int i=0; i<DISTANCE; i++){
			for(int m=i; m<tpData.length; m+=DISTANCE){
				for(int q=0; q<DISTANCE; q++){
					for(int p=q; p<tpData[0].length; p+=DISTANCE){
						count++;
						n++;
						if (n == 5001)
						{
							end_n = System.currentTimeMillis();
							System.out.println(end_n );
							System.out.println(begin_n);
							System.out.println("5000个样本所用时间:" + (end_n - begin_n));
							return;
						}
						
						record_NQoS.add(tpData[m][p]);
						
						if(tpData[m][p] <= QOS_VALUE){
							x++;
						}	
						
						double aftPro_C0;
						double aftPro_C1;
						
						if(count>shortMonlength){
							double abandon= record_NQoS.get(count-shortMonlength);
							if(abandon <= QOS_VALUE){
								x--;
							}
							
							double c = x * 1.0 / shortMonlength;
							if ( c >= BETA) {
								YorN.add(1);
								nC0++;
							}else{
								YorN.add(0);
								nC1++;
							}	
							
							aftPro_C0 = computeAftPro_C0(plC0, m, p,
									userList, webServiceList, ll2Wi_C0, tpData,
									x, count, tp);//
							aftPro_C1 = computeAftPro_C1(plC1, m, p,
									userList, webServiceList, ll2Wi_C1, tpData,
									x, count, tp);
							K = Math.pow(Math.abs(aftPro_C0), BETA)
									/ Math.pow(Math.abs(aftPro_C1), BETA);
						}else{
							double c = x * 1.0 / count;
							if(c >= BETA ){
								YorN.add(1);
								nC0++;
							}else{
								YorN.add(0);
								nC1++;
							}
							aftPro_C0 = computeAftPro_C0(plC0, m, p,
									userList, webServiceList, ll2Wi_C0, tpData,
									x, count, tp);//
							aftPro_C1 = computeAftPro_C1(plC1, m, p,
									userList, webServiceList, ll2Wi_C1, tpData,
									x, count, tp);
							K = Math.pow(Math.abs(aftPro_C0), BETA)
									/ Math.pow(Math.abs(aftPro_C1), BETA);
						}
						
						
					
				       try {
			    		 
			    		   FileWriter fw = new FileWriter(OUT_PATH +"/test_K.txt",true);
			    		   BufferedWriter bw = new BufferedWriter(fw);
		//	    		   String s = "使用样本数量" + n + "样本集满足QoS属性";
			    		   String G = ""+ K;
//			    		   bw.write(G +'\n');
			    		   bw.write(G);
			    		   bw.newLine();
			    		   bw.flush();
			    		   fw.close();
			    		   bw.close();
				       	}catch (Exception e) {
			    		   // TODO Auto-generated catch block
			    		   e.printStackTrace();
			    		  }
			    	
				        R0 = "*******aftPro_C0" + aftPro_C0 + "*******" ;
				        R1 = "*******aftPro_C1" + aftPro_C1 + "*******";
				        try {
				    		   
				    		   FileWriter fw = new FileWriter(OUT_PATH +"/test_data.txt",true);
				    		   BufferedWriter bw = new BufferedWriter(fw);
//				    		   bw.write(R0 +'\n');
				    		   bw.write(R0);
				    		   bw.newLine();
//				    		   bw.write(R1 +'\n');
				    		   bw.write(R1);
				    		   bw.newLine();
				    		   bw.flush();
				    		   fw.close();
				    		   bw.close();
				    	 }catch (Exception e) {
				    		   // TODO Auto-generated catch block
				    		   e.printStackTrace();
				    		  }
		     	       
						 	 
						     if (K > 1)
							     {	
						  	 try {
					    		  
					    		   FileWriter fw = new FileWriter(OUT_PATH +"/test_BETA.txt",true);
					    		   BufferedWriter bw = new BufferedWriter(fw);
					 //   		   String s = "使用样本数量" + n + "样本集不不不不不不满足QoS属性";
					    		   String s = "1";
//					    		   bw.write(s +'\n');
					    		   bw.write(s);
					    		   bw.newLine();
					    		   bw.flush();
					    		   fw.close();
					    		   bw.close();
					    	 }catch (Exception e) {
					    		   // TODO Auto-generated catch block
					    		   e.printStackTrace();
					    		  }
						         }
						     else if( K < 1)
						     {
						    	 try {
						    		   
						    		   FileWriter fw = new FileWriter(OUT_PATH +"/test_BETA.txt",true);
						    		   BufferedWriter bw = new BufferedWriter(fw);
					//	    		   String s = "使用样本数量" + n + "样本集满足QoS属性";
						    		   String s = "-1";
//						    		   bw.write(s +'\n');
						    		   bw.write(s);
						    		   bw.newLine();
						    		   bw.flush();
						    		   fw.close();
						    		   bw.close();
						    	 }catch (Exception e) {
						    		   // TODO Auto-generated catch block
						    		   e.printStackTrace();
						    		  }
						     }
						    	
						   
						
					}
				}
			}
		
		}
		
		
	
	}
	
	public static void readData(String userPath, String webSevicePath, String RTPath, String TPPath){
		long begin, end;
		
		//读取userList数据
		userList = new ReadUserInformationDataFromTxt(userPath).readData();
		System.out.println("Read user information success...");
		//读取webService数据
		webServiceList = new ReadWebServiceInformationDataFromTxt(webSevicePath).readData();
		System.out.println("Read web service information success...");
		
		rtData = new double[userList.size()][webServiceList.size()];
		tpData = new double[userList.size()][webServiceList.size()];
		rt = new int [userList.size()][webServiceList.size()];
		tp = new int [userList.size()][webServiceList.size()];
		//读取rt数据
		System.out.println("Begin to read RT matrix...");
		begin = System.currentTimeMillis();
		rtData = new ReadRTDataFromTxt(RTINFORMATION_DATA_PATH, userList.size(), webServiceList.size()).readData();
		end = System.currentTimeMillis();
		System.out.println("End to read RT matrix, takes" + new ComputeTime().computeTime(begin, end) + "ms...");
		//读取tp数据
		System.out.println("Begin to read PT matrix...");
		begin = System.currentTimeMillis();
		tpData = new ReadTPDataFromTxt(TPINFORMATION_DATA_PATH, userList.size(), webServiceList.size()).readData();
		end = System.currentTimeMillis();
		System.out.println("End to read PT matrix, takes" + new ComputeTime().computeTime(begin, end) + "ms...");
	}
	
	public static void traversalMatrix(ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			double[][] matrix){
		System.out.println("Begin to traversalMatrix...");
		long begin = System.currentTimeMillis();
		int n = 0;
		int x = 0;
		double c = 0.0;
		nC0Xl = 0;
		nC1Xl = 0;
		nC0 = 0;
		nC1 = 0;
		for(int i=0; i<DISTANCE; i++){
			for(int m=i; m<matrix.length; m+=DISTANCE){
				for(int q=0; q<DISTANCE; q++){
					for(int p=q; p<matrix[0].length; p+=DISTANCE){
						n++;
						
						if(n>2500&&n<3000){
							matrix[m][p] =20.0;
							tpData[m][p]=20.0;
						}else{
							if(n%5==0){
								 matrix[m][p] =20.0;
								 tpData[m][p]=20.0;
							}else {
								 matrix[m][p] =3.0;
								 tpData[m][p]=3.0;
							}
						}
						
						
						UserInformation userInformation = userList.get(m);
	 					WebServiceInformation webServiceInformation = webServiceList.get(p);
						HashMap<String, String> ll = new HashMap<String, String>();
						ll.put(userInformation.getNation(), webServiceInformation.getNation());
						if(ll2Num.containsKey(ll)){
							ll2Num.put(ll, ((Integer)ll2Num.get(ll)) + 1);
						}else{
							ll2Num.put(ll, 1);
						}
						if(matrix[m][p] <= QOS_VALUE){
							x++;
							tp[m][p] = 1;
							nC0Xl++;
							nC1Xl++;
							c = x * 1.0 / n;
							if(c >= BETA){
								nC1Xl--;
							}else{
								nC0Xl--;
							}
						}
						c = x * 1.0 / n;
						if(c >= BETA){
							if(ll2C0.containsKey(ll)){
								ll2C0.put(ll, ((Integer)ll2C0.get(ll)) + 1);
							}else{
								ll2C0.put(ll, 1);
							}
							nC0++;
						}else{
							if(ll2C1.containsKey(ll)){
								ll2C1.put(ll, ((Integer)ll2C1.get(ll)) + 1);
							}else{
								ll2C1.put(ll, 1);
							}
							nC1++;	
						}
					}
				}
			}
		}
		System.out.println("BETA:" + (x * 1.0 / n));
//		System.out.println("nC0:" + nC0 + ";" + "nC1:" + nC1 + ";" + "n:" + n);
		long end = System.currentTimeMillis();
		System.out.println("End to traversalMatrix, takes" + new ComputeTime().computeTime(begin, end) + "ms...");
	}
	
	public static double computePro_C0(double[][] tpData, int x, int n){
		//根据C0计算似然概率，为后面计算后验概率做准备
		if(n>shortMonlength){	
			
			if(YorN.get(n-shortMonlength-1)==1){
				nC0--;
			}
			
			double pro_C0 = (nC0 * 1.0 + 1) / shortMonlength;
			return pro_C0;
		}else{
			
			double pro_C0 = (nC0 * 1.0 + 1)/ (n + 2);
			return pro_C0;
		}
		
	}
	
	public static double computePro_C1(double[][] tpData, int x, int n){
		//根据C1计算似然概率，为后面计算后验概率做准备
		if(n>shortMonlength){
			
			if(YorN.get(n-shortMonlength-1)==0){
				nC1--;
			}
			
			double pro_C1 = (nC1 * 1.0 + 1) / shortMonlength;
			return pro_C1;
		}else{
			
			double pro_C1 = (nC1 * 1.0 + 1)/(n + 2);
			return pro_C1;
		}
		
	}
	
	public static double computeAftPro_C0(double plC0, int a, int b, 
			ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			HashMap<HashMap<String, String>, Double> ll2Wi_C0, double[][] tpData, int x, int n, int[][] tp) {
		double pro_C0 = computePro_C0(tpData, x, n);
//		prePro_C0 = prePro_C0 * new ComputePrePro_C0().computePrePro_CX(plC0, a, b, userList, 
//				webServiceList, ll2Wi_C0, tp);
		double RpreProC0= new ComputePrePro_C0().computePrePro_CX(plC0, a, b,
				userList, webServiceList, ll2Wi_C0, tp);
		
		recordpreProC0.add(RpreProC0);
		if(n>shortMonlength){
			prePro_C0 = prePro_C0+ RpreProC0-recordpreProC0.get(n-shortMonlength);
		}else{
			prePro_C0 = prePro_C0+ RpreProC0;
		}
double pC0X = new ComputePCiX().computePCiX(pro_C0, prePro_C0);
		return pC0X;
	}
	
	public static double computeAftPro_C1(double plC1, int a, int b, 
			ArrayList<UserInformation> userList, ArrayList<WebServiceInformation> webServiceList,
			HashMap<HashMap<String, String>, Double> ll2Wi_C1, double[][] tpData, int x, int n, int[][] tp) {
		double pro_C1 = computePro_C1(tpData, x, n);

		double RprePro_C1=new ComputePrePro_C1().computePrePro_CX(plC1, a, b,
				userList, webServiceList, ll2Wi_C1, tp);
		
		recordpreProC1.add(RprePro_C1);
		if(n>shortMonlength){
			prePro_C1 = prePro_C1	+ RprePro_C1-recordpreProC1.get(n-shortMonlength);
		}else{
			prePro_C1 = prePro_C1	+ RprePro_C1;
		}
//		prePro_C1 = prePro_C1 + new ComputePrePro_C1().computePrePro_CX(plC1, a, b, userList,
//							webServiceList, ll2Wi_C1, tp);
 //   	System.out.println(pro_C1 + ";" + prePro_C1);
		double pC1X = new ComputePCiX().computePCiX(pro_C1, prePro_C1);
		return pC1X;
	}


	public static class dateImport_user implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JFileChooser jFC = new JFileChooser();
		    jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = jFC.showDialog(null, null);
		    if (result == JFileChooser.APPROVE_OPTION){
		    	File theFile = jFC.getSelectedFile();
			    String wholePath = theFile.getPath();
				Main.USERINFORMATION_DATA_PATH = wholePath;
		    }
		}
	}

	public static class dateImport_rt implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JFileChooser jFC = new JFileChooser();
		    jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = jFC.showDialog(null, null);
		    if (result == JFileChooser.APPROVE_OPTION){
		    	File theFile = jFC.getSelectedFile();
			    String wholePath = theFile.getPath();
				Main.RTINFORMATION_DATA_PATH = wholePath;
		    }
		}
	}

	public static class dateImport_tp implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JFileChooser jFC = new JFileChooser();
		    jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = jFC.showDialog(null, null);
		    if (result == JFileChooser.APPROVE_OPTION){
		    	File theFile = jFC.getSelectedFile();
			    String wholePath = theFile.getPath();
				Main.TPINFORMATION_DATA_PATH = wholePath;
		    }
		}
	}

	public static class dateImport_ws implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JFileChooser jFC = new JFileChooser();
		    jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int result = jFC.showDialog(null, null);
		    if (result == JFileChooser.APPROVE_OPTION){
		    	File theFile = jFC.getSelectedFile();
			    String wholePath = theFile.getPath();
				Main.WSINFORMATION_DATA_PATH = wholePath;
		    }
		}
	}
	
	
	
	
	
	
	
	

	
}
