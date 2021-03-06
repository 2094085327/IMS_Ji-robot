package love.simbot.example.BootAPIUse;

import cn.hutool.http.HttpUtil;
import love.simbot.example.core.listener.ClassBox.Constant;
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
public class API extends Constant {

    public Map<String, Object> params = new HashMap<>();

    /**
     * 人工智能APi
     *
     * @param message 接收传递来的消息
     * @return 当能够正常处理时返回AI的自动回复，否则当catch到异常时返回猫猫码狗头
     */
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
            //从字符串转成java代码
            JSONObject jsonObj = JSONObject.fromObject(jsonStr);
            JSONObject data = jsonObj.getJSONObject("data");
            JSONObject info = data.getJSONObject("info");
            return info.getString("text");
        } catch (Exception e) {

            return "[CAT:face,id=277]";
        }
    }

    /**
     * 每日一言APi-通过正则匹配获取HTML格式的信息
     *
     * @return 返回从api获得的每日一言
     */
    public String EverydaySentences() {

        // 因为不需要推送参数，所以直接使用get方法
        String jsonStr = HttpUtil.get("https://v.api.aa1.cn/api/yiyan/index.php");

        // 匹配标签<p>
        String reg = ".*?(<(p>)(.*)</\\2).*";
        Pattern pattern = Pattern.compile(reg);
        // 指定要匹配的字符串
        Matcher matcher = pattern.matcher(jsonStr);
        if (matcher.find()) {
            System.out.println(matcher.group(3));
            return matcher.group(3);
        } else {

            return "没什么想说的呢~";
        }
    }

    /**
     * 青年大学习API
     *
     * @return 返回当期青年大学习的题目与答案
     */
    public String YouthStudy() {
        return HttpUtil.createGet("https://api.klizi.cn/API/other/youth.php").execute().body();
    }

    /**
     * 二刺螈图片(P站图片，随机选择是否发送涩图)
     *
     * @return 返回获取到的P站图片url
     */

    public String twoDimensional() {
        int random = (int) (Math.random() * 3);
        String url = "";
        switch (random) {
            case 0:
                url = "https://api.lolicon.app/setu/v2?tag=萝莉|少女&tag=白丝|黑丝";
                break;
            case 1:
            case 2:
                url = "https://api.lolicon.app/setu/v2?tag=原神&tag=白丝|黑丝";
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

    /**
     * B站直播检测
     *
     * @param uid 输入up主的uid来检测齐直播间状态
     * @return 返回直播状态，直播信息等
     */
    public String bLive(String uid) {
        try {
            String url = "https://api.live.bilibili.com/xlive/web-room/v1/index/getRoomBaseInfo?uids=" +
                    uid + "&req_biz=video";
            String jsonStr = HttpUtil.get(url);
            JSONObject object = JSONObject.fromObject(jsonStr);
            JSONObject data = object.getJSONObject("data");
            JSONObject byUids = data.getJSONObject("by_uids");
            JSONObject id = byUids.getJSONObject(uid);
            String liveStatus = id.getString("live_status");
            String roomId = id.getString("room_id");
            String title = id.getString("title");
            String liveTime = id.getString("live_time");
            String msg = "";
            if ("1".equals(liveStatus)) {
                msg += "直播间正在直播中\n";
                BLIVESTATE = "false";
                SendTwice = "false";
                System.out.println(data);
                msg += "房间号:" + roomId + "\n房间名:" + title + "\n直播开始时间:" + liveTime;
                return msg;
            } else {
                BLIVESTATE = "false";
                return "";
            }
        } catch (Exception e) {
            BLIVESTATE = "false";
            return "没有在直播";
        }

    }

    /**
     * B站直播检测
     *
     * @param uid 输入up主的uid来检测齐直播间状态
     */
    public void bLiveHelp(String uid) {
        try {
            String url = "https://api.live.bilibili.com/xlive/web-room/v1/index/getRoomBaseInfo?uids="
                    + uid + "&req_biz=video";
            String jsonStr = HttpUtil.get(url);
            JSONObject object = JSONObject.fromObject(jsonStr);
            JSONObject data = object.getJSONObject("data");
            JSONObject byUids = data.getJSONObject("by_uids");
            JSONObject id = byUids.getJSONObject(uid);
            String liveStatus = id.getString("live_status");

            if ("0".equals(liveStatus)) {
                BLIVESTATE = "true";
            } else {
                BLIVESTATE = "false";
            }
        } catch (Exception e) {

            BLIVESTATE = "false";

        }

    }
}