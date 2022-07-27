package love.simbot.example.core.listener.ClassBox;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.OnPrivateMsgRecall;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.events.MsgGet;

/**
 * @author zeng
 * @date 2022/6/23 15:32
 * @user 86188
 */
@Beans
public class ReCall {
    @OnPrivateMsgRecall
    public void privateRecall(MsgGet msg) {
        AccountInfo accountInfo = msg.getAccountInfo();
        //获取机器人信息
        BotInfo botInfo = msg.getBotInfo();

        //获取当前时间
        TimeTranslate time1 = new TimeTranslate();
        String format1 = time1.tt();

        //在控制台输出信息
        String personMsg = "[" + format1 + "]" + "用户[" + accountInfo.getAccountNickname()
                + "/" + accountInfo.getAccountCode() + "]time了给Bot[" + botInfo.getBotName()
                + "]" + "发送的信息";
        System.out.println(personMsg);

        //将信息存入日志
        Writing writer = new Writing();
        writer.write(personMsg + "\n");
    }

/**    @OnGroupMsgRecall
//    public void GroupRecall(GroupMsg groupMsg) {
//        GroupAccountInfo accountInfo2 = groupMsg.getAccountInfo();
//        GroupInfo groupInfo = groupMsg.getGroupInfo();
//        String GroupId = groupInfo.getGroupCode();
//
//        //获取当前时间
//        TimeTranslate time1 = new TimeTranslate();
//        String format1 = time1.tt();
//
//        //在控制台输出信息
//        String Group = "[" + format1 + "]用户[" + accountInfo2.getAccountNickname() + "/" + accountInfo2.getAccountCode() + "]撤回了在群[" + GroupId + "/" + groupInfo.getGroupName() + "]发送的信息" + groupMsg.getMsg();
//        System.out.println(Group);
//
//        //将信息存入日志
//        Writing writer = new Writing();
//        writer.write(Group + "\n");
//    }
 */
}
