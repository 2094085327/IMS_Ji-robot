package love.simbot.example.listener;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.Sender;
import love.simbot.example.listener.ClassBox.API;
import love.simbot.example.listener.ClassBox.TimeTranslate;
import love.simbot.example.listener.ClassBox.Writing;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zeng
 * @date 2022/6/22 11:49
 * @user 86188
 */
@Beans
public class MyprivateListener1 {
    //复读私聊消息
    @Listen(PrivateMsg.class)
    public void fudu(PrivateMsg msg, Sender sender) {
        Timer timer = new Timer();//用于创建延时对话

        AccountInfo accountInfo = msg.getAccountInfo();//获取私信人信息
        BotInfo botInfo = msg.getBotInfo();//获取机器人信息

        //获取当前时间
        TimeTranslate time1 = new TimeTranslate();
        String format1 = time1.tt();

        API api = new API();


        //在控制台输出信息
        String personMsg = "[" + format1 + "]" + "用户[" + accountInfo.getAccountNickname()
                + "/" + accountInfo.getAccountCode() + "]给bot[" + botInfo.getBotName()
                + "]" + "发送了信息：" + msg.getMsg();
        System.out.println(personMsg);

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String img = util.toCat("image", true, "file="
                + "https://c2cpicdw.qpic.cn/offpic_new/2094085327//2094085327-1184240239-588C5FFE182E2972466B8B2403F76CBC/0?term&#61;2");

        //将信息存入日志
        Writing writer = new Writing();
        writer.write(personMsg + "\n");

        //检测到特定私信内容进行特定回复
        if (msg.getMsg().equals("hi") || msg.getMsg().equals("你好")) {

            sender.sendPrivateMsg(msg, "嗨！");

        } else {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    sender.sendPrivateMsg(msg, api.result(msg.getText()));
                    sender.sendPrivateMsg(msg, img);
                }
            };
            timer.schedule(timerTask, 3000);
        }
    }
}
