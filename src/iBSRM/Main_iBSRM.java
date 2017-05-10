package iBSRM;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JFileChooser;



public class Main_iBSRM {
	// 目标β值
	private static double BETA = 0.36; 				// BETA为QoS属性标准。即设置的一个基线——满足QoS要求的比例数	 与这个基线 进行比较
	private static double QoS_VALUE = 8.0;			// QoS属性值要求
	private static final int DISTANCE = 1000;
	private static final double PERSET_VALUE = 0.0;
	private static ArrayList<UserBean> userList = new ArrayList<UserBean>();
	private static ArrayList<WebServiceBean> webServiceList = new ArrayList<WebServiceBean>();
	private static HashMap<HashMap<String, String>, Integer> ll2Num = new HashMap<HashMap<String, String>, Integer>();// 记录所有国家组合的总数
	private static HashMap<HashMap<String, String>, Integer> ll2C0 = new HashMap<HashMap<String, String>, Integer>();// 记录所有国家组合的C0个数
	private static HashMap<HashMap<String, String>, Integer> ll2C1 = new HashMap<HashMap<String, String>, Integer>();// 记录所有国家组合的C1个数
	private static double[][] rtData, tpData;
	private static int[][] tp, rt; 						// 记录吞吐量、响应时间对应的0-1值
	private static int nC0Xl, nC1Xl, nC0, nC1;			// Xl 表示 Xk = 1;
														// nC0、nC1指的是通过先验概率的n——即计算先验概率的分子。
	private static double prePro_C0 = 1.0;
	private static double prePro_C1 = 1.0;
	

	public Main_iBSRM(String USER_PATH,String WS_PATH,String TP_PATH,String RT_PATH){
		this.USERINFORMATION_DATA_PATH=USER_PATH;
		this.WSINFORMATION_DATA_PATH = WS_PATH;
		this.TPINFORMATION_DATA_PATH=TP_PATH;
		this.RTINFORMATION_DATA_PATH=RT_PATH;
	}


	public void StartLis() {

			nC0 = 0;
			nC1 = 0;
			long begin_n = 0;
			long end_n = 0;
			readData(USERINFORMATION_DATA_PATH, WSINFORMATION_DATA_PATH,
					RTINFORMATION_DATA_PATH, TPINFORMATION_DATA_PATH);// 读取文件数据
			traversalTPMatrix(userList, webServiceList, tpData);// 处理tp数据，如果需要处理rt数据则把二维数组换成rtdata


			
			double plC0 = new ComputePlC0().computePlCX(nC0Xl, nC0);// 计算Pc0
			double plC1 = new ComputePlC1().computePlCX(nC1Xl, nC1);// 计算Pc1

			nC0 = 0;
			nC1 = 0;
			int n = 0; // 循环中目前为止 的样本数量
			int x = 0; // 循环中目前为止 小于给定QoS值的样本数量
			double K;
			String R0;
			String R1;
			begin_n = System.currentTimeMillis();

			for (int i = 0; i < DISTANCE; i++) {
				for (int m = i; m < tpData.length; m += DISTANCE) {
					for (int q = 0; q < DISTANCE; q++) {
						for (int p = q; p < tpData[0].length; p += DISTANCE) {
							n++;
							if (n == 5001) {
								end_n = System.currentTimeMillis();
								System.out.println("begin_n: " + begin_n);
								System.out.println("end_n: " + end_n);
//								outTA.append("2000个样本所用时间" + (end_n - begin_n) + "\r\n");
								return;
							}
							if (tpData[m][p] <= QoS_VALUE) {
								x++;
							}
							double aftPro_C0 = computeAftPro_C0(plC0, m, p, userList, webServiceList, tpData, x, n, tp);
							double aftPro_C1 = computeAftPro_C1(plC1, m, p, userList, webServiceList, tpData, x, n, tp);
							K = Math.pow(Math.abs(aftPro_C0), BETA) / Math.pow(Math.abs(aftPro_C1), BETA);
							try {
								FileWriter fw = new FileWriter(OUT_PATH + "/test_K.txt", true);
								BufferedWriter bw = new BufferedWriter(fw);
								String G = "K = " + K;
								bw.write(G);
								bw.newLine();
								bw.flush();
								fw.close();
								bw.close();
							} catch (Exception e) {
								e.printStackTrace();
							}

							R0 = "******* aftPro_C0: " + aftPro_C0 + " *******";
							R1 = "******* aftPro_C1: " + aftPro_C1 + " *******";
							try {
								FileWriter fw = new FileWriter(OUT_PATH + "/test_data.txt", true);
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(R0);
								bw.newLine();
								bw.write(R1);
								bw.newLine();
								bw.flush();
								fw.close();
								bw.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (K > 1) {
								try {
									FileWriter fw = new FileWriter(OUT_PATH + "/test_YorN.txt", true);
									BufferedWriter bw = new BufferedWriter(fw);
									String s = "1";
									bw.write(s);
									bw.newLine();
									bw.flush();
									fw.close();
									bw.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (K < 1) {
								try {
									FileWriter fw = new FileWriter(OUT_PATH + "/test_YorN.txt", true);
									BufferedWriter bw = new BufferedWriter(fw);
									String s = "-1";
									bw.write(s);
									bw.newLine();
									bw.flush();
									fw.close();
									bw.close();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
	}

	public static Long[] readData(String userPath, String webSevicePath,
			String RTPath, String TPPath) {

		Long[] time = new Long[4];
		// 读取userList数据
		userList = new ReadUserInformationDataFromTxt(userPath)
				.readData();
		System.out.println("Read user information success...");
		// 读取webService数据
		webServiceList = new ReadWebServiceInformationDataFromTxt(
				webSevicePath).readData();
		System.out.println("Read web service information success...");

		rtData = new double[userList.size()][webServiceList.size()];
		tpData = new double[userList.size()][webServiceList.size()];
		rt = new int[userList.size()][webServiceList.size()];
		tp = new int[userList.size()][webServiceList.size()];
		// 读取rt数据
		System.out.println("Begin to read RT matrix...");
		time[0] = System.currentTimeMillis();
		rtData = new ReadRTDataFromTxt(RTINFORMATION_DATA_PATH,
				userList.size(), webServiceList.size()).readData();
		time[1] = System.currentTimeMillis();
		System.out.println("End to read RT matrix, takes"
				+ new ComputeTime().computeTime(time[0], time[1])
				+ "ms...");

		// 读取tp数据
		System.out.println("Begin to read PT matrix...");
		time[2] = System.currentTimeMillis();
		tpData = new ReadTPDataFromTxt(TPINFORMATION_DATA_PATH,
				userList.size(), webServiceList.size()).readData();
		time[3] = System.currentTimeMillis();
		System.out.println("End to read PT matrix, takes"
				+ new ComputeTime().computeTime(time[2], time[3])
				+ "ms...");
		return time;
	}

	/**
	 * @param userList
	 * @param webServiceList
	 * @param matrix
	 */
	public static void traversalTPMatrix(ArrayList<UserBean> userList,
			ArrayList<WebServiceBean> webServiceList, double[][] matrix) {
		System.out.println("Begin to traversalMatrix...");
		long begin = System.currentTimeMillis();
		int n = 0;
		int x = 0;
		double c = 0.0;
		nC0Xl = 0;
		nC1Xl = 0;
		nC0 = 0;
		nC1 = 0;
		for (int i = 0; i < DISTANCE; i++) {
			for (int m = i; m < matrix.length; m += DISTANCE) {
				for (int q = 0; q < DISTANCE; q++) {
					for (int p = q; p < matrix[0].length; p += DISTANCE) {

						matrix[m][p] = 10.0;
						n++;
						UserBean userInformation = userList.get(m);
						WebServiceBean webServiceInformation = webServiceList
								.get(p);
						HashMap<String, String> ll = new HashMap<String, String>();
						ll.put(userInformation.getNation(),
								webServiceInformation.getNation());
						if (ll2Num.containsKey(ll)) {
							ll2Num.put(ll, ((Integer) ll2Num.get(ll)) + 1);
						} else {
							ll2Num.put(ll, 1);
						}
						if (matrix[m][p] <= QoS_VALUE) {
							x++;
							tp[m][p] = 1;
							nC0Xl++;
							nC1Xl++;
							c = x * 1.0 / n;
							if (c >= BETA) { /* 为了把功能分离开吧 */// 达不到QoS值标准的数目占目前总数的比值大于等于我们约定的临界值β，那么显然不落在c1中。
								nC1Xl--;
							} else {
								nC0Xl--;
							}
						}
						c = x * 1.0 / n;
						if (c >= BETA) { /* 为了把功能分离开吧 */
							if (ll2C0.containsKey(ll)) {
								ll2C0.put(ll, ((Integer) ll2C0.get(ll)) + 1);
							} else {
								ll2C0.put(ll, 1);
							}
							nC0++;
						} else {
							if (ll2C1.containsKey(ll)) {
								ll2C1.put(ll, ((Integer) ll2C1.get(ll)) + 1);
							} else {
								ll2C1.put(ll, 1);
							}
							nC1++;
						}
					}
				}
			}
		}
		System.out.println("BETA:" + (x * 1.0 / n));
		long end = System.currentTimeMillis();
		System.out.println("End to traversalMatrix, takes" + new ComputeTime().computeTime(begin, end) + "ms...");
	}

	public static double computePro_C0(double[][] tpData, int x, int n) {
		// 根据C0计算似然概率，为后面计算后验概率做准备
		double c = x * 1.0 / n;
		if (c >= BETA) {
			nC0++;
		}
		double pro_C0 = (nC0 * 1.0 + 1) / (n + 2);
		return pro_C0;
	}

	public static double computePro_C1(double[][] tpData, int x, int n) {
		// 根据C1计算似然概率，为后面计算后验概率做准备
		double c = x * 1.0 / n;
		if (c < BETA) {
			nC1++;
		}
		double pro_C1 = (nC1 * 1.0 + 1) / (n + 2);
		return pro_C1;
	}

	public static double computeAftPro_C0(double plC0, int a, int b,
			ArrayList<UserBean> userList,
			ArrayList<WebServiceBean> webServiceList,
			double[][] tpData, int x, int n, int[][] tp) {

		double pro_C0 = computePro_C0(tpData, x, n);

		prePro_C0 = prePro_C0 + new ComputePrePro_C0().computePrePro_CX(plC0, a, b, userList, webServiceList,  tp);

		double pC0X = new ComputePCiX().computePCiX(pro_C0, prePro_C0);
		return pC0X;

	}

	public static double computeAftPro_C1(double plC1, int a, int b,
			ArrayList<UserBean> userList,
			ArrayList<WebServiceBean> webServiceList,
			double[][] tpData, int x, int n, int[][] tp) {

		double pro_C1 = computePro_C1(tpData, x, n);

		prePro_C1 = prePro_C1 + new ComputePrePro_C1().computePrePro_CX(plC1, a, b, userList, webServiceList, tp);

		double pC1X = new ComputePCiX().computePCiX(pro_C1, prePro_C1);
		return pC1X;
	}

}
