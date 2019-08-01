package test;

public class TestJDK8Default implements TestJDK8DefaultIn{

	public void TestJDK8Default() {
		System.out.println("接口中原抽象类实现！");
	}
	
	public static void main(String[] args) {
		TestJDK8Default in=new TestJDK8Default();
		in.test1();
	}

}
