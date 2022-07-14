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

    // 城市地理信息方法
    public String CityInfo(String city) {
        try {
            // 去除消息中的空格
            city = city.replace((char) 12288, ' ');    // 将中文空格替换为英文空格
            city = city.trim();

            String URL = "https://geoapi.qweather.com/v2/city/lookup?location=" + city
                    + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
            String jsonStr = HttpUtil.get(URL);
            JSONObject object = JSONObject.fromObject(jsonStr);

            // 获得名为"location"的String
            String location = object.getString("location");
            JSONArray jsonArray;
            jsonArray = new JSONArray(location);

            // 赋值，便于其他方法调用
            this.adm1 = jsonArray.getJSONObject(0).getString("adm1");
            this.adm2 = jsonArray.getJSONObject(0).getString("adm2");
            this.id = jsonArray.getJSONObject(0).getString("id");

            String cityName = jsonArray.getJSONObject(0).getString("name");// 城市名称
            String cityCountry = jsonArray.getJSONObject(0).getString("country"); // 所属国家
            float cityLat = Float.parseFloat(jsonArray.getJSONObject(0).getString("lat"));// 维度
            float cityLon = Float.parseFloat(jsonArray.getJSONObject(0).getString("lon"));// 经度
            String cityTz = jsonArray.getJSONObject(0).getString("tz");// 时区
            String cityType = jsonArray.getJSONObject(0).getString("type");// 城市地区的属性
            String cityLonChange;
            String cityLatChange;

            // 将获得的经纬度调整为统一格式
            if (cityLon < 0) {
                cityLon = -cityLon;
                cityLonChange = cityLon + "°W";
            } else {
                cityLonChange = cityLon + "°E";
            }

            if (cityLat < 0) {
                cityLat = -cityLat;
                cityLatChange = cityLat + "°S";
            } else {
                cityLatChange = cityLat + "°N";
            }

            return "城市名称:" + cityName + "\n城市ID:" + id + "\n所属国家:" + cityCountry
                    + "\n上级行政区划:" + adm1 + "\n所属一级行政区划:" + adm2
                    + "\n城市经纬度:\n" + cityLatChange + "\n" + cityLonChange + "\n时区:"
                    + cityTz + "\n城市地区属性:" + cityType;
        } catch (Exception e) {
            return "姬姬没有找到" + city + "的地理信息呢，要不换一个试试";

        }
    }


    // 城市天气信息方法
    public String WeatherInfo(String city) {
        // 去除消息中的空格
        city = city.replace((char) 12288, ' ');    // 将中文空格替换为英文空格
        city = city.trim();

        // 调用城市地理信息方法以获取城市id
        CityInfo(city);

        String URL = "https://devapi.qweather.com/v7/weather/now?location=" + id + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";

        // 获取城市温度信息
        try {
            String jsonStr = HttpUtil.get(URL);
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            String updateTime = jsonObject.getString("updateTime"); // 更新时间
            JSONObject now = jsonObject.getJSONObject("now");
            String temp = now.getString("temp"); // 温度
            String feelsLike = now.getString("feelsLike"); // 体感温度
            String text = now.getString("text"); //  文字描述
            String wind360 = now.getString("wind360"); // 360°风向
            String windDir = now.getString("windDir"); // 风向
            String windScale = now.getString("windScale"); // 风力等级
            String humidity = now.getString("humidity"); // 相对湿度
            String precip = now.getString("precip"); // 当前小时累计降水量
            String pressure = now.getString("pressure"); // 大气压强
            String vis = now.getString("vis"); // 能见度
            String cloud = now.getString("cloud"); // 能见度

            String weatherInfo = "无量姬已经获取到了 " + adm1 + adm2 + "市 的天气呢\n更新时间：" + updateTime + "\n温度："
                    + temp + "°C\n体感温度：" + feelsLike + "°C\n天气状况：" + text + "\n风向："
                    + windDir + "-" + wind360 + "°\n风力等级：" + windScale + " 级\n相对湿度:"
                    + humidity + "%\n当前小时累计降水量：" + precip + "\n大气压强：" + pressure + " 百帕\n能见度："
                    + vis + " 公里";

            // 云量信息有可能为空
            if (cloud != null) {
                weatherInfo += "\n云量：" + cloud + "%";
            }
            return weatherInfo + "\n" + DailyWeather() + "\n" + WeatherDay();

        } catch (Exception e) {
            return "姬姬没有找到" + city + "的天气呢，要不换一个试试";
        }
    }

    // 获取第二天的天气状况
    public String WeatherDay() {

        String URL = "https://devapi.qweather.com/v7/weather/3d?location=" + id
                + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
        String jsonStr = HttpUtil.get(URL);
        JSONObject object = JSONObject.fromObject(jsonStr);

        // 获得名为"daily"的String
        String daily = object.getString("daily");
        JSONArray jsonArray;
        jsonArray = new JSONArray(daily);

        String nextDay = jsonArray.getJSONObject(1).getString("fxDate"); // 日期
        String sunrise = jsonArray.getJSONObject(1).getString("sunrise"); // 日出时间
        String sunset = jsonArray.getJSONObject(1).getString("sunset"); // 日落时间
        String tempMax = jsonArray.getJSONObject(1).getString("tempMax"); // 最高温度
        String tempMin = jsonArray.getJSONObject(1).getString("tempMin"); // 最低温度
        String textDay = jsonArray.getJSONObject(1).getString("textDay"); // 日间天气

        String textNight = jsonArray.getJSONObject(1).getString("textNight"); // 日间天气
        return "--------" + nextDay + "-------\n日出时间:" + sunrise + "  日落时间:" + sunset
                + "\n最高气温:" + tempMax + " °C   最低气温:" + tempMin + " °C\n日间天气:" + textDay
                + "\n夜间天气:" + textNight;
    }

    // 获取每日天气建议
    public String DailyWeather() {
        String URL = "https://devapi.qweather.com/v7/indices/1d?type=1,2&location=" + id
                + "&key=1b48b4a69c8a4f7cb17674cbf4cea29b";
        String jsonStr = HttpUtil.get(URL);
        JSONObject object = JSONObject.fromObject(jsonStr);

        // 获得名为"daily"的String
        String daily = object.getString("daily");
        JSONArray jsonArray;
        jsonArray = new JSONArray(daily);

        String text = jsonArray.getJSONObject(0).getString("text"); // 日期
        return "姬姬温馨提示：" + text;
    }

}

