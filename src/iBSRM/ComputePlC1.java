package iBSRM;

public class ComputePlC1 implements ComputePlCX{
	public double computePlCX(int nC1Xl, int nC1){
		System.out.println("Begin to computePlC1...");
		return (nC1Xl * 1.0 + 1) / (nC1 + 2);//Ϊ�˱�����ַ�ĸΪ��������ʹ��ƽ������
	}
}
