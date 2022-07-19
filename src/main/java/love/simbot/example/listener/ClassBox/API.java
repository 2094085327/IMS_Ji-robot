package love.simbot.example.listener.ClassBox;

import cn.hutool.http.HttpUtil;
import net.sf.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author zeng
 * @date 2022/6/24 16:12
 * @user 86188
 */
public class API {

    public Map<String, Object> params = new HashMap<>();

    // 人工智能APi
    public String result(String message) {

        // 接口地址
        String requestUrl = "https://api.ownthink.com/bot?appid=6b571b31ba3fb3a18c9361738b644a46&spoken=" + message;

        // 去除消息中的空格
        // 将中文空格替换为英文空格
        message = message.trim();
        message = message.replace((char) 12288, ' ');

        //请求数据
        //params用于存储请求数据的参数
        params.put("API_KEY", "KxfWKvDylXtWXkb1YqMIYSmm");
        params.put("appid", "ae8009f3d0c07ae156271f15f28e11e1");
        params.put("SECRET_KEY", "29af4375c3a146f3adabc3aa56a5eaf0");
        params.put("spoken", message);
        try {
            //调用HttpUtil.post方法，这个方法主要用于请求地址，并加上请求参数
            String jsonStr = HttpUtil.post(requestUrl, params);

            //JSONObject从String中得到数据，提取数据，并且输出数据
            JSONObject jsonObj = JSONObject.fromObject(jsonStr);//从字符串转成java代码
            JSONObject data = jsonObj.getJSONObject("data");
            JSONObject info = data.getJSONObject("info");
            return info.getString("text");
        } catch (Exception e) {

            return "[CAT:face,id=277]";
        }
    }

    // 每日一言APi
    public String EverydaySentences() {

        // 因为不需要推送参数，所以直接使用get方法
        String jsonStr = HttpUtil.get("https://v.api.aa1.cn/api/yiyan/index.php");//https://saying.api.azwcl.com/saying/get

        // 匹配标签<p>
        String reg = ".*?(<(p>)(.*)</\\2).*";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(jsonStr);// 指定要匹配的字符串
        if (matcher.find()) {
            System.out.println(matcher.group(3));
            return matcher.group(3);
        } else {

            return "没什么想说的呢~";
        }
    }

    // 青年大学习API
    public String YouthStudy() {
        return HttpUtil.createGet("https://api.klizi.cn/API/other/youth.php").execute().body();
    }

    // 二刺螈图片
    public String TwoDimensional() {
        int random = (int) (Math.random() * 3);
        String url = "";
        switch (random) {
            case 0:
                url = "https://api.lolicon.app/setu/v2?tag=萝莉|少女&tag=白丝|黑丝";
                break;
            case 1:
            case 2:
                url = "https://api.lolicon.app/setu/v2?tag=萝莉|少女&tag=白丝|黑丝";
                break;
            default:
        }
        try {


            String jsonStr = HttpUtil.get(url);
            JSONObject object = JSONObject.fromObject(jsonStr);
            String data = object.getString("data");
            JSONArray jsonArray;
            jsonArray = new JSONArray(data);
            System.out.println(jsonArray.getJSONObject(0).getJSONObject("urls"));

            return jsonArray.getJSONObject(0).getJSONObject("urls").getString("original");
        } catch (Exception e) {
            return "https://gchat.qpic.cn/gchatpic_new/2094085327/2083469072-2232305563-72311C09F00D0DBEF47CF5B070311E46/0?term&#61;2";
        }
    }

}