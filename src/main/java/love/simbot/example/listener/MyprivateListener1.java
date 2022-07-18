package love.simbot.example.listener;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Listen;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.events.MessageGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.simbot.example.listener.ClassBox.API;
import love.simbot.example.listener.ClassBox.TimeTranslate;
import love.simbot.example.listener.ClassBox.Writing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zeng
 * @date 2022/6/22 11:49
 * @user 86188
 */
@Beans
public class MyprivateListener1 {
    public static ExecutorService THREAD_POOL;

    /**
     * 复读私聊消息
     *
     * @param msg       私聊消息获取
     * @param sender    构建发送器
     * @param msgSender 消息SENDER GETTER SETTER
     */
    @Listen(PrivateMsg.class)
    public void fudu(PrivateMsg msg, Sender sender, MsgSender msgSender) {

        // 创建线程池
        THREAD_POOL = new ThreadPoolExecutor(50, 50, 100000,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), r -> {
            Thread thread = new Thread(r);
            thread.setName(String.format("newThread%d", thread.getId()));
            return thread;
        });

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
                + "https://c2cpicdw.qpic.cn/offpic_new/2094085327//2094085327-746385872-8246287FAB0F39E42CB2EDEF38E9C700/0?term&#61;2");
        //将信息存入日志
        Writing writer = new Writing();
        writer.write(personMsg + "\n");

        //检测到特定私信内容进行特定回复
        if (msg.getMsg().equals("hi") || msg.getMsg().equals("你好")) {

            sender.sendPrivateMsg(msg, "嗨！");

        } else {

            THREAD_POOL.execute(() -> {
                sender.sendPrivateMsg(msg, api.result(msg.getText()));
                // 获取消息的flag
                MessageGet.MessageFlag<? extends MessageGet.MessageFlagContent>
                        flag = (MessageGet.MessageFlag<? extends MessageGet.MessageFlagContent>) sender.sendPrivateMsg(msg, img).get();

                System.out.println(flag);
                // 通过flag撤回消息
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                msgSender.SETTER.setMsgRecall(flag);
            });

        }
    }
}
