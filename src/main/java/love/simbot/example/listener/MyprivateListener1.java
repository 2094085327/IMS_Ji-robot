package love.simbot.example.listener;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.*;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MessageGet;
import love.forte.simbot.api.message.events.PrivateMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.listener.ClassBox.*;

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
public class MyprivateListener1 extends Constant {

    /**
     * 调用API接口的类
     */
    public API api = new API();

    /**
     * 调用天气地理API接口的类
     */
    public GeoAPI geoApi = new GeoAPI();

    public static ExecutorService THREAD_POOL;

    /**
     * 解除禁言模块
     * #@Filter() 注解为消息过滤器
     *
     * @param privateMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnPrivate
    @Filter(value = ".关机", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void setGroupStateClose(PrivateMsg privateMsg, MsgSender msgSender) {
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        String setUser = accountInfo.getAccountCode();
        if (setUser.equals(USERID1)) {
            BOOTSTATE = false;
            System.out.println("已关机");
            msgSender.SENDER.sendPrivateMsg(privateMsg, "姬姬关机了！");
        } else {
            msgSender.SENDER.sendPrivateMsg(privateMsg, "你没有姬姬的权限哦~");
        }
    }

    /**
     * 解除禁言模块
     * #@Filter() 注解为消息过滤器
     *
     * @param privateMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = ".开机", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void setGroupStateOpen(PrivateMsg privateMsg, MsgSender msgSender) {
        AccountInfo accountInfo = privateMsg.getAccountInfo();
        String setUser = accountInfo.getAccountCode();
        if (setUser.equals(USERID1)) {
            BOOTSTATE = true;
            System.out.println("已开机");
            msgSender.SENDER.sendPrivateMsg(privateMsg, "姬姬开机了！");
        } else {
            msgSender.SENDER.sendPrivateMsg(privateMsg, "你没有姬姬的权限哦~");
        }
    }


    /**
     * 监听私聊消息并存入日志
     *
     * @param msg       私聊消息获取
     * @param sender    构建发送器
     * @param msgSender 消息SENDER GETTER SETTER
     */
    @Listen(PrivateMsg.class)
    public void fudu(PrivateMsg msg, Sender sender, MsgSender msgSender) {


        // 创建线程池
        THREAD_POOL = new ThreadPoolExecutor(50, 50, 10000,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), r -> {
            Thread thread = new Thread(r);
            thread.setName(String.format("newThread%d", thread.getId()));
            return thread;
        });
        //获取私信人信息
        AccountInfo accountInfo = msg.getAccountInfo();
        //获取机器人信息
        BotInfo botInfo = msg.getBotInfo();

        //获取当前时间
        TimeTranslate time1 = new TimeTranslate();
        String format1 = time1.tt();


        //在控制台输出信息
        String personMsg = "[" + format1 + "]" + "用户[" + accountInfo.getAccountNickname()
                + "/" + accountInfo.getAccountCode() + "]给bot[" + botInfo.getBotName()
                + "]" + "发送了信息：" + msg.getMsg();
        System.out.println(personMsg);

        //将信息存入日志
        Writing writer = new Writing();
        writer.write(personMsg + "\n");
    }

    /**
     * 私聊消息回复与撤回
     *
     * @param msg       私聊消息获取
     * @param sender    构建发送器
     * @param msgSender 消息SENDER GETTER SETTER
     */
    @OnPrivate
    public void replay(PrivateMsg msg, Sender sender, MsgSender msgSender) {

        String msgs = msg.getMsg();

        // 创建线程池
        THREAD_POOL = new ThreadPoolExecutor(50, 50, 10000,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), r -> {
            Thread thread = new Thread(r);
            thread.setName(String.format("newThread%d", thread.getId()));
            return thread;
        });
        if (BOOTSTATE) {
            CatCodeUtil util = CatCodeUtil.INSTANCE;
            String img = util.toCat("image", true, "file="
                    + "https://c2cpicdw.qpic.cn/offpic_new/2094085327//2094085327-746385872-8246287FAB0F39E42CB2EDEF38E9C700/0?term&#61;2");


            if (!msgs.contains("天气") && !msgs.contains("青年大学习") && !msgs.contains("禁言")
                    && !msgs.contains("@全体成员") && !msgs.contains("来点好康的")
                    && !msgs.contains("看看动漫") && !msgs.contains("来点原神")) {
                //检测到特定私信内容进行特定回复
                if ("hi".equals(msg.getMsg()) || "你好".equals(msg.getMsg())) {

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
                            // 休眠10000ms
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 撤回操作
                        msgSender.SETTER.setMsgRecall(flag);
                    });
                }
            }
        }
    }

    /**
     * 天气查询模块
     * 在检测到关键词和命令后调用天气API来显示天气
     *
     * @param privateMsg 用于获取私聊消息，群成员信息等
     * @param msgSender  用于在群聊中发送消息
     * @param city       将通过正则匹配到的关键词城市传递给天气api
     */
    @OnPrivate
    @Filter(value = "{{city}}天气", matchType = MatchType.REGEX_MATCHES, trim = true)
    @Filter(value = "/tq{{city}}", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void weather(PrivateMsg privateMsg, MsgSender msgSender, @FilterValue("city") String city) {

        Sender sender = msgSender.SENDER;

        if (city == null) {
            sender.sendPrivateMsg(privateMsg, "天气查询失败哦~ 请输入正确的城市~");
        } else {
            sender.sendPrivateMsg(privateMsg, geoApi.WeatherInfo(city));
            geoApi.adm1 = null;
            geoApi.adm2 = null;
            geoApi.id = null;
        }

    }
}
