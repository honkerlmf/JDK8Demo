/**
 * 
 */
package test;

/**
 * @author Administrator 封装手电筒类的类，开放充电开关方法
 */
public class Torch {

	// 初始化电量10
	private double electricQuantity = 10;
	// 手电筒开关属性
	private Boolean isOpen = false;

	/**
	 * 构造方法 提供可以自定义赋值初始化
	 */
	public Torch(int electricQuantity) {
		this.electricQuantity = electricQuantity;
		System.out.println("初始化电量为：" + electricQuantity);
	}

	// 充电间隔一秒后冲入1点电量
	public double chongdian(){
		System.out.println("充电接入开始！");
		Thread tc = new Thread() {
			public void run() {
				try {
					while (electricQuantity<100) {
						sleep(200);
						electricQuantity++;
						System.out.println("执行一次充电,当前电量:"+electricQuantity);
						if (electricQuantity==100) {
							System.out.println("电量充满停止充电！");
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!this.isInterrupted()) {
					  this.interrupt();
					  System.out.println("电量充满关闭充电线程！");
				}
				System.out.println("充电线程运行中！");
			}
		};
		tc.start();
		return electricQuantity;
	}

	// 设置开关(开)
	public Boolean Open() {
		isOpen = true;
		System.out.println("开关被打开！");
		Thread t1 = new Thread() {
			public void run() {
				while (electricQuantity > 0&&isOpen) {
					try {
						sleep(500);
						electricQuantity--;
						System.out.println("电量被消耗到：" + electricQuantity);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (electricQuantity == 0) {
					isOpen = false;
					System.out.println("无电量，手电筒自动关闭了");
				}
			}
		};
		// 启动消耗电量的线程
		t1.start();
		return isOpen;
	}

	// 设置开关(关)
	public Boolean Close() {
		isOpen = false;
		System.out.println("开关被关闭！");
		return isOpen;
	}

}
