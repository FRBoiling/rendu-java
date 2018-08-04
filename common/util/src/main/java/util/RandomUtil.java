package util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with Intellij IDEA
 * Description:
 * User: Boil
 * Date: 2018-07-31
 * Time: 09:46
 **/
public class RandomUtil {
    /***
     *  随机数 [0,n)  线性同余法伪随机数生成器。缺点：可预测。
     * @param n
     * @return
     */
    public static int getRandom(int n){
        Random random = new Random();
        return random.nextInt(n);
    }

    /***
     * 随机数[0,n) 加密强随机数。缺点：效率较低
     * @param n
     * @return
     */
    public static int getSecureRandom(int n){
        SecureRandom random= null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return random.nextInt(n);
    }
}
