package love.simbot.example.listener.ClassBox;

import cn.hutool.http.HttpUtil;
import net.sf.json.JSONObject;
import org.json.JSONArray;

/**
 * @author zeng
 * @date 2022/7/11 9:55
 * @user 86188
 */
public class GeoAPI {

    public String adm1;
    public String adm2;
    public String id;
    // 去除消息中的空格


    public String CityInfo(String city) {
        try {


            String URL = "https://geoapi.qweather.com/v2/city/lookup?location=" + city + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
            String jsonStr = HttpUtil.get(URL);
            JSONObject object = JSONObject.fromObject(jsonStr);
            // 获得名为"T1348647853363"的String
            String location = object.getString("location");
            JSONArray jsonArray;
            jsonArray = new JSONArray(location);

            this.adm1 = jsonArray.getJSONObject(0).getString("adm1");
            this.adm2 = jsonArray.getJSONObject(0).getString("adm2");
            this.id = jsonArray.getJSONObject(0).getString("id");
            return jsonArray.getJSONObject(0).getString("id");
        } catch (Exception e) {
            return null;
        }
    }

    public String WeatherInfo(String city) {
        city = city.replace((char) 12288, ' ');    // 将中文空格替换为英文空格
        city = city.trim();
        CityInfo(city);
        String URL = "https://devapi.qweather.com/v7/weather/now?location=" + id + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
        // 获取城市
        try {
            String jsonStr = HttpUtil.get(URL);
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            String updateTime = jsonObject.getString("updateTime");
            JSONObject now = jsonObject.getJSONObject("now");
            String temp = now.getString("temp");// 温度
            String feelsLike = now.getString("feelsLike");// 体感温度
            String text = now.getString("text");//  文字描述
            String wind360 = now.getString("wind360"); // 360°风向
            String windDir = now.getString("windDir");// 风向
            String windScale = now.getString("windScale");// 风力等级
            String humidity = now.getString("humidity");// 相对湿度
            String precip = now.getString("precip");// 当前小时累计降水量
            String pressure = now.getString("pressure");// 大气压强
            String vis = now.getString("vis");// 能见度
            String cloud = now.getString("cloud");// 能见度

            String weatherInfo = "无量姬已经获取到了 " + adm1 + adm2 + "市 的天气呢\n更新时间：" + updateTime + "\n温度："
                    + temp + "°C\n体感温度：" + feelsLike + "°C\n天气状况：" + text + "\n风向："
                    + windDir + "-" + wind360 + "°\n风力等级：" + windScale + " 级\n相对湿度:"
                    + humidity + "%\n当前小时累计降水量：" + precip + "\n大气压强：" + pressure + " 百帕\n能见度："
                    + vis + " 公里";
            if (cloud != null) {
                weatherInfo += "\n云量：" + cloud + "%";
            }
            return weatherInfo + "\n" + DailyWeather() + "\n" + WeatherDay();

        } catch (Exception e) {
            return "姬姬没有找到" + city + "的天气呢，要不换一个试试";
        }
    }

    public String WeatherDay() {

        String URL = "https://devapi.qweather.com/v7/weather/3d?location=" + id + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
        String jsonStr = HttpUtil.get(URL);
        JSONObject object = JSONObject.fromObject(jsonStr);
        // 获得名为"daily"的String
        String daily = object.getString("daily");
        JSONArray jsonArray;
        jsonArray = new JSONArray(daily);

        String nextDay = jsonArray.getJSONObject(1).getString("fxDate");// 日期
        String sunrise = jsonArray.getJSONObject(1).getString("sunrise");// 日出时间
        String sunset = jsonArray.getJSONObject(1).getString("sunset");// 日落时间
        String tempMax = jsonArray.getJSONObject(1).getString("tempMax");// 最高温度
        String tempMin = jsonArray.getJSONObject(1).getString("tempMin");// 最低温度
        String textDay = jsonArray.getJSONObject(1).getString("textDay");// 日间天气
        String textNight = jsonArray.getJSONObject(1).getString("textNight");// 日间天气
        return "--------" + nextDay + "-------\n日出时间:" + sunrise + "  日落时间:" + sunset
                + "\n最高气温:" + tempMax + " °C   最低气温:" + tempMin + " °C\n日间天气:" + textDay
                + "\n夜间天气:" + textNight;
    }

    public String DailyWeather() {
        String URL = "https://devapi.qweather.com/v7/indices/1d?type=1,2&location=" + id + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
        String jsonStr = HttpUtil.get(URL);
        JSONObject object = JSONObject.fromObject(jsonStr);
        // 获得名为"daily"的String
        String daily = object.getString("daily");
        JSONArray jsonArray;
        jsonArray = new JSONArray(daily);

        String text = jsonArray.getJSONObject(0).getString("text");// 日期
        return "姬姬温馨提示：" + text;
    }

    public String GeoCity() {
        return  null;
    }
}

