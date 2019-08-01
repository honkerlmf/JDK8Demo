package test;

public interface TestJDK8DefaultIn {
	//抽象方法
	public void TestJDK8Default();
	//接口方法实现
	default void test1(){
		System.out.println("JDK8新特性接口中方法的实现！");
	}
}
