/**
 * @author: ZhangLG
 * @date:2019年5月29日 下午3:45:08
 * @version : v1.0
 */

package test;

import java.util.Objects;

public class TestJDK7Objects {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String i = null;
		String q="0";
		//使用jdk1.7的object判断避免空值
		if(Objects.equals(i, q)){
			System.out.println("比对对象值相同！");
		};
	}

}
