package love.simbot.example.listener.ClassBox;

import catcode.CatCodeUtil;
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
        message = message.replace((char) 12288, ' ');    // 将中文空格替换为英文空格
        message = message.trim();

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

    // 天气查询API
    public String Weather(String Msg) {
        String URL = "https://api.klizi.cn/API/other/tianqi_c.php?msg=" + Msg;

        // 尝试获取数据，当输入错误时返回提示
        try {
            String jsonStr = HttpUtil.get(URL);

            // 获取城市
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject real = data.getJSONObject("real");
            JSONObject station = real.getJSONObject("station");
            String province = station.getString("province");
            String city = station.getString("city");
            String publish_time = real.getString("publish_time");

            // 获取天气状况
            JSONObject weather = real.getJSONObject("weather");
            String temperature = weather.getString("temperature");
            String temperatureDiff = weather.getString("temperatureDiff");
            String humidity = weather.getString("humidity");
            String info = weather.getString("info");
            String feelst = weather.getString("feelst");

            // 获取风力状况
            JSONObject wind = real.getJSONObject("wind");
            String direct = wind.getString("direct");
            String power = wind.getString("power");
            String speed = wind.getString("speed");

            // 获取警报状态
            JSONObject warn = real.getJSONObject("warn");
            String alert = warn.getString("alert");
            String issuecontent = warn.getString("issuecontent");
            String signaltype = warn.getString("signaltype");
            String signallevel = warn.getString("signallevel");
            String result0 = "\n----------\n警报：\n\t警告：" + alert + "\n\n\t发布内容：" + issuecontent + "\n\n\t灾害类型："
                    + signaltype + "\n\n\t灾害等级：" + signallevel;

            // 获取空气质量
            JSONObject air = data.getJSONObject("air");
            String aqi = air.getString("aqi");
            String text = air.getString("text");

            String result = "成功获取 " + province + " " + city + "市 天气状况" + "\n上次更新时间：" + publish_time
                    + "\n当前天气状况：\n\t温度：" + temperature + "\n\t温差：" + temperatureDiff + "\n\t湿度："
                    + humidity + "\n\t天气情况：" + info + "\n\t体感温度：" + feelst + "\n----------\n当前风力情况：\n\t风向："
                    + direct + "\n\t风力：" + power + "\n\t风速：" + speed + "\n----------\n空气状况：\n\t空气质量："
                    + aqi + "\n\t等级：" + text;

            // 当存在警报信息时合并消息
            if (!alert.equals("9999")) {
                result += result0;
            }


            return result;
        } catch (Exception e) {
            // 报错时返回
            return "无量姬没有查询到" + Msg + "的天气哦~ 请输入正确的城市或再试试~";
        }
    }

    // 二刺螈图片
    public String TwoDimensional() {
        int random = (int) (Math.random() * 3);
        String url = "";
        switch (random) {
            case 0:
                url = "https://api.lolicon.app/setu/v2?tag=萝莉|少女&tag=白丝|黑丝&r18=1";
                break;
            case 1:
            case 2:
                url = "https://api.lolicon.app/setu/v2?tag=萝莉|少女&tag=白丝|黑丝";
                break;
        }

        String jsonStr = HttpUtil.get(url);
        JSONObject object = JSONObject.fromObject(jsonStr);
        String data = object.getString("data");
        JSONArray jsonArray;
        jsonArray = new JSONArray(data);
        System.out.println(jsonArray.getJSONObject(0).getJSONObject("urls"));

        return jsonArray.getJSONObject(0).getJSONObject("urls").getString("original");
    }

}