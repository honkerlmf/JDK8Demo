/**
 * @author: ZhangLG
 * @date:2019年5月31日 上午10:49:21
 * @version : v1.0
 */

import java.text.NumberFormat;

public class JavaFillBit {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        int   n   =   101;   
        NumberFormat   formatter   =   NumberFormat.getNumberInstance();   
        formatter.setMinimumIntegerDigits(4);   
        formatter.setGroupingUsed(false);   
        String   s   =   formatter.format(n);   
        
        System.out.println(s);

//        String 
        System.out.println(Integer.parseInt(s));
        
    }

}

