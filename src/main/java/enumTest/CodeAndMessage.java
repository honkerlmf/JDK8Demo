package enumTest;

/**
 * 自定义枚举
 * @author 
 */
public enum CodeAndMessage {
    /**
     * 设置枚举值，注意与后面的field字段对应
     */
      SUCCESS(1,"成功"),
      WARNING(0,"警告"),
      ERROR(-1,"报错");

    private int code;
    private String message;

    @Override  
    public String toString() {  
        return this.name() + "(" + this.code + "," + this.message + ")";
    }

    /**
     * 根据code获取message
     * @param code
     * @return
     */
    public static String getMessage(int code){
        //通过enum.values()获取所有的枚举值
        for(CodeAndMessage codeAndMessage : CodeAndMessage.values()){
            //通过enum.get获取字段值
            if(codeAndMessage.code == code){
                return codeAndMessage.message;
            }
        }
        return null;
    }

    /**
     * 根据code获取CodeAndMessage
     * @param code
     * @return
     */
    public static CodeAndMessage getCodeAndMessage(int code){
        for(CodeAndMessage codeAndMessage : CodeAndMessage.values()){
            if(codeAndMessage.code == code){
                return codeAndMessage;
            }
        }
        return null;
    }

    private CodeAndMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

public static void main(String[] args) throws Exception{
    //测试自定义枚举
    System.out.println();
    LOGGER.info("测试自定义枚举");
    LOGGER.info("获取一个枚举值" + CodeAndMessage.SUCCESS.getClass().toString() + " : " + CodeAndMessage.SUCCESS);
    //通过CodeAndMessage.values()获取枚举值数组
    for (CodeAndMessage codeAndMessage : CodeAndMessage.values()) {
        LOGGER.info("遍历枚举--" + codeAndMessage.getClass().toString() + " : " + codeAndMessage);
    }
    //通过code获取message
    LOGGER.info("通过code获取message: " + CodeAndMessage.getMessage(1));
    //通过code获取枚举对象CodeAndMessage
    LOGGER.info("通过code获取枚举对象CodeAndMessage: " + CodeAndMessage.getCodeAndMessage(-1));
    //通过枚举值获取枚举对象
    LOGGER.info("通过enum.valueOf(name)获取枚举对象: " + CodeAndMessage.valueOf("SUCCESS"));
    LOGGER.info("通过Enum.valueOf(enumClass,name)获取枚举对象: " + Enum.valueOf(CodeAndMessage.class, "SUCCESS"));
}

// setter and getter

public int getCode() {
	return code;
}

public void setCode(int code) {
	this.code = code;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
}
class LOGGER{
	public static void info(String info){
		System.out.println(info);
	}
}