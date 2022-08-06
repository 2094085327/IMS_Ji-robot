package love.simbot.example.BootAPIUse.YuanShenAPI;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;

/**
 * @author zeng
 * @date 2022/8/2 9:13
 * @user 86188
 */
@Beans
public class YuanShenApiUse {

    @OnGroup
    @Filter(value = "原神抽卡分析", matchType = MatchType.CONTAINS, trim = true)
    public void gaChaLog(GroupMsg groupMsg, MsgSender msgSender) throws Exception {

        String url = groupMsg.getMsg();
        msgSender.SENDER.sendGroupMsg(groupMsg, "请耐心等待，正在分析中,大致需要30秒至一分钟(视抽卡数决定)");
        msgSender.SENDER.sendGroupMsg(groupMsg, yuanAPI.getGachaInfo(url));
    }
}

