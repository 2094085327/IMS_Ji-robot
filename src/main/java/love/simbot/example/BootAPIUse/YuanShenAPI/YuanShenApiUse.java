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

/**
 * @author zeng
 * @date 2022/8/2 9:13
 * @user 86188
 */
@Beans
public class YuanShenApiUse extends Constant {

    @OnGroup
    @Filter(value = "原神抽卡分析", matchType = MatchType.CONTAINS, trim = true)
    public void gaChaLog(GroupMsg groupMsg, MsgSender msgSender) throws Exception {
        // 项目路径
        File file = new File(System.getProperty("user.dir"));

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();
        if (groupBanId != 1) {

            //String url = groupMsg.getMsg();
            String url = yuanAPI.toUrl(groupMsg.getMsg());
            String urlCheckType = yuanAPI.checkApi(url);
            if (!"OK".equals(urlCheckType)) {
                assert urlCheckType != null;
                msgSender.SENDER.sendGroupMsg(groupMsg, urlCheckType);
            } else {
                msgSender.SENDER.sendGroupMsg(groupMsg, "请耐心等待，正在分析中,大致需要30秒至一分钟(视抽卡数决定)");
                yuanAPI.getGachaInfo(url);
                msgSender.SENDER.sendGroupMsg(groupMsg, CatUtil.getImage(file + "\\src\\main\\resources\\yuanImage\\finally.jpg").toString());
            }


        }
    }
}

