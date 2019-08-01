/**
 * 
 */
package test;

/**
 * @author Administrator
 *
 */
public class TestTorch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Torch torch=new Torch(20);
		torch.chongdian();
		Boolean isopen=torch.Open();
		if (isopen) {
			System.out.println("当前外层手电筒状态显示为开！");
		}else{
			System.out.println("当前外层手电筒状态显示为关！");
		}
		
	}

}
