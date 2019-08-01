package CallBack;

import CallBack.Seller.doHomeWork;

public class TestCallBack {
	public static void main(String[] args) {
		int a=589;
		int b=989;
		Student student = new Student("小明");
		student.callHelp(a, b);
		int c = 26497;
	    int d = 11256;
	    Seller seller=new Seller("老奶奶");
	    Object object=seller.getClass();
	    seller.callHelp(c, d);
	}
}
