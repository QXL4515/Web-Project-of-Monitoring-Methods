package iBSRM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadUserInformationDataFromTxt implements ReadListDataFromTxt{
	private String path;
	private ArrayList<UserBean> userList;
	
	public ReadUserInformationDataFromTxt(String path){
		this.path = path;
	}
	
	public ArrayList<UserBean> readData(){
		char[] buffer = new char[1024];
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(new File(path));
			br = new BufferedReader(new InputStreamReader(fis));
			userList = new ArrayList<UserBean>();
			String temp;
			while ((temp=br.readLine()) != null) {
				String[] paras = temp.split("	");
				int id = Integer.parseInt(paras[0]);
				String ip = paras[1];
				String nation = paras[2];
				double longitude = Double.parseDouble(paras[3]);
				double latitude = Double.parseDouble(paras[4]);
				userList.add(new UserBean(id, ip, nation, longitude, latitude));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(userList != null){
			return userList;
		}else{
			System.out.println("没有用户数据");
			return null;
		}
	}
	
}
