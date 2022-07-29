package love.simbot.example.BootAPIUse.OtherAPI;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.BootAPIUse.API;
import love.simbot.example.core.listener.ClassBox.Constant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zeng
 * @date 2022/7/27 14:15
 * @user 86188
 */
@Beans
public class OtherApiUse extends Constant {

    /**
     * 调用API中的方法
     */
    API api = new API();

    /**
     * 青年大学习模块
     * 在收到@时调用青年大学习Api进行回复
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = "青年大学习", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void youthStudy(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID3)) {
            sender.sendGroupMsg(groupMsg, api.YouthStudy());

        }
    }

    /**
     * 检测直播模块
     * 在检测到关键词和命令后调用天气API来显示天气
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "检测直播", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void BLive(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID3)) {
            sender.sendGroupMsg(groupMsg, api.bLive(BiUpUid));
        }
    }

    /**
     * 可达鸭模块
     * 在检测到关键词和命令后调用天气API来显示天气
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "可达鸭 {{left}} {{right}}", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void KeDaYa(GroupMsg groupMsg, MsgSender msgSender,
                       @FilterValue("left") String left, @FilterValue("right") String right) throws IOException {


        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID3)) {
            kedaya ke = new kedaya();
            System.out.println(this.getClass().getResourceAsStream("/image/kedaya.gif"));
            OutputStream out = new FileOutputStream("C:\\Users\\86188\\Desktop\\simbot-mirai-demo1\\src\\main\\resources\\image\\" + 1 + ".gif");
            out.write(ke.makeImage(left, right));

            CatCodeUtil util = CatCodeUtil.INSTANCE;
            String url = "C:\\Users\\86188\\Desktop\\simbot-mirai-demo1\\src\\main\\resources\\image\\1.gif ";
            String img = util.toCat("image", true, "file=" + url);

            sender.sendGroupMsg(groupMsg, img);
        }
    }


}
