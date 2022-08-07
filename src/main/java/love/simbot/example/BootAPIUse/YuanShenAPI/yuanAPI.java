package love.simbot.example.BootAPIUse.YuanShenAPI;

import cn.hutool.http.HttpUtil;
import net.sf.json.JSONObject;
import org.json.JSONArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zeng
 * @date 2022/8/6 18:17
 * @user 86188
 */
public class yuanAPI {


    /**
     * 获取解析后的真实URL并加入参数
     *
     * @param key   输入相应关键词获取参数
     * @param url   输入的未经过解析的URL
     * @param times 运行的次数
     * @param endId 最后一条信息的id，用于获取下一页
     * @return 返回解析后的URL
     */
    public static String getUrl(String key, String url, int times, String endId) {
        // 中文正则，去除链接中的中文
        String regexChinese = "[\u4e00-\u9fa5]";
        // 获取无中文的链接
        String noChineseUrl = url.replaceAll(regexChinese, "");
        // 从"#"处分割链接去除链接中的[#/log]并获取分割后的链接
        String splitUrl1 = noChineseUrl.split("#")[0];
        // 从[?]处分割链接以拼接到接口链接上
        String splitUrl2 = splitUrl1.split("\\?")[1];
        // 含参链接
        String newUrl = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?" + splitUrl2 + "&gacha_type=301&page=1&size=20&end_id=0";
        // 通过正则获取关键字
        Pattern pattern2 = Pattern.compile("([\\s\\S]*)" + key + "=([^&]*)" + "([\\s\\S]*)" + "end_id=");
        Matcher matcher2 = pattern2.matcher(newUrl);
        if (matcher2.find()) {

            // 返回拼接后的api链接
            return matcher2.group(1) + key + "=" + times + matcher2.group(3) + "end_id=" + endId;
        }
        return "";
    }

    public static String getGachaInfo(String url) throws Exception {
        Robot r = new Robot();

        // 3星个数
        int threeStar = 0;
        // 4星个数
        int fourStar = 0;
        // 5星个数
        int fiveStar = 0;
        // 总抽数
        int count = 0;

        // 本页最后一条数据的id
        String endId = "0";

        // 大保底次数
        int guaranteedCount = 0;

        // 至五星为止的次数
        int fiveGachaCount = 0;

        // 五星角色列表
        ArrayList<String> fivePeople = new ArrayList<>();
        // 四星角色列表
        ArrayList<String> fourPeople = new ArrayList<>();

        // 五星角色与对应抽数
        ArrayList<String> fivePeopleCount = new ArrayList<>();

        // 发送的五星列表
        StringBuilder mapMsg = new StringBuilder();

        for (int i = 1; i <= 9999; i++) {
            fiveGachaCount++;
            // 接口URL地址
            String urls = yuanAPI.getUrl("page", url, i, endId);

            // 请求json数据
            String jsonStr = HttpUtil.get(urls);
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");

            // 创建list数组
            String list = data.getString("list");
            JSONArray jsonArray;
            jsonArray = new JSONArray(list);
            // 数组长度
            int length = jsonArray.length();
            // 总抽数
            count += length;

            // 当数组长度为0时(即没有抽卡记录时)跳出循环
            if (length == 0) {
                break;
            }

            // 获取当前页最后一个数据的id以进行翻页
            endId = jsonArray.getJSONObject(length - 1).getString("id");


            // 对当前页20个数据进行遍历
            for (int j = 0; j < length; j++) {
                fiveGachaCount += 1;
                // 抽卡等级
                String rankType = jsonArray.getJSONObject(j).getString("rank_type");
                // 抽到的物品名
                String name = jsonArray.getJSONObject(j).getString("name");


                switch (rankType) {
                    case "3":
                        // 3星
                        threeStar += 1;
                        break;
                    case "4":
                        // 4星
                        fourStar += 1;
                        fourPeople.add(name);
                        break;
                    case "5":
                        if (fiveStar == 0) {
                            mapMsg.append("已垫").append(": ").append(fiveGachaCount).append(" 发\n\n");
                        }

                        // 5星
                        fiveStar += 1;
                        fivePeople.add(name);
                        if ("刻晴".equals(name) || "迪卢克".equals(name) || "七七".equals(name) || "琴".equals(name) || "莫娜".equals(name)) {
                            guaranteedCount += 1;

                            // 将歪的角色的抽数与姓名加入list
                            fivePeopleCount.add(String.valueOf(fiveGachaCount));
                            fivePeopleCount.add(name + "(歪)");

                        } else {

                            // 没歪的角色姓名与抽数
                            fivePeopleCount.add(String.valueOf(fiveGachaCount));
                            fivePeopleCount.add(name);

                        }
                        // 将五星计数归零
                        fiveGachaCount = 0;

                    default:
                }

            }
            // 延迟1秒避免被ban
            r.delay(1000);
        }

        // 取出ArrayList中存储的数据进行拼接
        for (int m = 0; m < fivePeopleCount.size() - 2; m++) {
            // 跳过奇数个防止重复
            if (m % 2 != 1) {
                System.out.println(fivePeopleCount.size());
                mapMsg.append(fivePeopleCount.get(m + 1)).append(": ").append(fivePeopleCount.get(m + 2)).append(" 发\n\n");
            }
        }
        // 当五星个数为偶数时需要额外补上最后一组数据
        if ((fivePeopleCount.size()) % 2 == 0) {
            mapMsg.append(fivePeopleCount.get(fivePeopleCount.size() - 1)).append(": ").append(fivePeopleCount.size()).append(" 发");
        }

        return "五星: " + fiveStar + " 次\n四星: " + fourStar + " 次\n三星: " + threeStar + " 次\n总计 " + count + " 抽\n\n" + averageGaCha(count, fiveStar) + "\n\n五星角色 :\n" + fivePeople + "\n\n四星武器&角色 :\n" + fourPeople + "\n\n大保底次数: " + guaranteedCount + " 次\n\n" + mapMsg;
    }

    public static String averageGaCha(int all, int five) {
        float averageFive = (float) all / five;
        float averageFiveCost = (float) (all / five) * 160;
        return "平均 " + averageFive + " 抽一次五星\n每个五星花费 " + averageFiveCost + " 原石";
    }
}

