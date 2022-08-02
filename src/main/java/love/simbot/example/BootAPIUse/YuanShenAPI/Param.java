package love.simbot.example.BootAPIUse.YuanShenAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zeng
 * @date 2022/8/2 19:12
 * @user 86188
 */
public class Param {

//    public static void main(String[] args) {
//        System.out.println(getParam("name", "https://www.baidu.com/s?name=hello&age=88&key=word"));
//        System.out.println(getParam("age", "https://www.baidu.com/s?name=hello&age=88&key=word"));
//        System.out.println(getParam("key", "https://www.baidu.com/s?name=hello&age=88&key=word"));
//
//
//    }

    public static String getParam(String key, String url, int times) {
        // Pattern pattern = Pattern.compile("(\\?|&)"+key+"=([^&]*)");
        Pattern pattern2 = Pattern.compile("([\\s\\S]*)" + key + "=([^&]*)" + "([\\s\\S]*)");
        // Matcher matcher = pattern.matcher(url);
        Matcher matcher2 = pattern2.matcher(url);
        if (matcher2.find()) {
            // System.out.println(matcher.group(2));
            // System.out.println(matcher2.group(1));

            return matcher2.group(1) + key + "=" + times + matcher2.group(3);

        }
        return "";
    }
}