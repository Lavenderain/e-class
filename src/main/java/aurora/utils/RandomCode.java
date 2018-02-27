package aurora.utils;

import java.util.Random;

/**
 * 生成6位随机英文字符串
 */
public class RandomCode {
    /**
     *
     * @return 6位随机英文字符串     */
	public static String getRandemCode1(){
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
	    Random random = new Random();   
	    StringBuffer seg = new StringBuffer();   
	    for (int i = 0; i < 6; i++) {   
	        int number = random.nextInt(base.length());   
	        seg.append(base.charAt(number));   
	    }
		return seg.toString();
	    }


	/**
	 *
	 * @return 4位随机数字字符串     */
	public static String getRandemCode2(){
		String base = "0123456789";
		Random random = new Random();
		StringBuffer seg = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int number = random.nextInt(base.length());
			seg.append(base.charAt(number));
		}
		return seg.toString();
	}
}
