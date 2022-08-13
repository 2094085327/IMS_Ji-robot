package love.simbot.example.BootAPIUse.YuanShenAPI;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.core.Util.CatUtil;
import love.simbot.example.core.listener.ClassBox.Constant;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * @author zeng
 * @date 2022/8/2 9:13
 * @user 86188
 */
@Beans
public class YuanShenApiUse extends Constant {
    public static ExecutorService THREAD_POOL;

    @OnGroup
    @Filter(value = "原神抽卡分析", matchType = MatchType.CONTAINS, trim = true)
    public void gaChaLog(GroupMsg groupMsg, MsgSender msgSender) throws Exception {
        // 项目路径
        File file = new File(System.getProperty("user.dir"));


        GroupInfo groupInfo = groupMsg.getGroupInfo();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();
        if (groupBanId != 1) {

            String url = YuanApi.toUrl(groupMsg.getMsg());
            String urlCheckType = YuanApi.checkApi(url);
            if (!"OK".equals(urlCheckType)) {
                assert urlCheckType != null;
                msgSender.SENDER.sendGroupMsg(groupMsg, urlCheckType);
            } else {
                msgSender.SENDER.sendGroupMsg(groupMsg, "请耐心等待，正在分析中,大致需要30秒至一分钟(视抽卡数决定)");
/**
 CompletableFuture<String> feature1 = CompletableFuture.supplyAsync(() -> {
 try {
 yuanAPI.getGachaRoleInfo(url);
 } catch (Exception e) {
 e.printStackTrace();
 }
 return "abc";
 });
 CompletableFuture<String> feature2 = CompletableFuture.supplyAsync(() -> {
 try {
 yuanAPI.getGachaArmsInfo(url);
 } catch (Exception e) {
 e.printStackTrace();
 }
 return "def";
 });
 CompletableFuture<String> feature3 = CompletableFuture.supplyAsync(() -> {
 try {
 yuanAPI.getGachaPermanentInfo(url);
 } catch (Exception e) {
 e.printStackTrace();
 }
 return "hig";
 });

 CompletableFuture<Void> totalFeature = CompletableFuture.allOf(feature1, feature2,feature3);
 totalFeature.join();
 String str1 = feature1.get();
 String str2 = feature2.get();
 String str3 = feature3.get();
 List<String> stringList = Stream.of(feature1, feature2, feature3).map(CompletableFuture::join).collect(Collectors.toList());
 System.out.println(stringList);
 System.out.println(str1 + str2 + str3);
 */

                YuanApi.getGachaRoleInfo(url);
                msgSender.SENDER.sendGroupMsg(groupMsg, "--角色池分析完成--");

                YuanApi.getGachaArmsInfo(url);
                msgSender.SENDER.sendGroupMsg(groupMsg, "--武器池分析完成--");

                YuanApi.getGachaPermanentInfo(url);
                msgSender.SENDER.sendGroupMsg(groupMsg, "--常驻池分析完成--");

                YuanApi.allData1();

                YuanApi.fincount = 0;
                YuanApi.finFiveStar = 0;
                YuanApi.finProbability = 0;

                picture.allPictureMake();

                msgSender.SENDER.sendGroupMsg(groupMsg, CatUtil.getImage(file + "\\src\\main\\resources\\yuanImage\\finally.png").toString());

            }
        }
    }
}

