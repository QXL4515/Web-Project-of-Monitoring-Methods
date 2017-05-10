 package iBSRM;

public class ComputePlC0 implements ComputePlCX{
	public double computePlCX(int nC0Xl, int nC0){
		System.out.println("Begin to computePlC0...");
		return (nC0Xl * 1.0 + 1) / (nC0 + 2);
	}
}
