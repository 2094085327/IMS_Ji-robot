package love.simbot.example.listener;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.*;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.*;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.listener.ClassBox.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zeng
 * @date 2022/6/22 17:23
 * @user 86188
 */
@Beans
public class MyGroupListener1 implements Constant {

    String personId1 = "2094085327";

    public static ExecutorService THREAD_POOL;

    /**
     * 获取当前时间
     */
    public TimeTranslate time = new TimeTranslate();


    /**
     * 注入得到一个消息构建器工厂
     */
    @Depend
    private MessageContentBuilderFactory messageBuilderFactory;

    /**
     * #@全体成员的猫猫码
     */
    public String cat1 = "[CAT:at,all=true]";

    /**
     * 调用API接口的类
     */
    public API api = new API();

    /**
     * 调用API接口的类
     */
    public GeoAPI geoApi = new GeoAPI();


    @OnGroup
    public void group(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;

        // 群成员信息
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        // 群信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 获取群成员ID
        String personId = accountInfo.getAccountCode();
        // 获取群号
        String groupId = groupInfo.getGroupCode();
        // 根据不同群号向不同人发送消息
        switch (groupId) {
            case GROUPID1:
                if (personId1.equals(personId)) {
                    break;
                }

            case "140469072":
                if (personId1.equals(personId)) {
                    break;
                }

            case "1043409458":
                if (SENTENCE1.equals(groupMsg.getMsg()) ||
                        SENTENCE5.equals(groupMsg.getMsg())) {
                    sender.sendGroupMsg(groupMsg, "无量姬还会看好康的");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                if (SENTENCE2.equals(groupMsg.getMsg())) {
                    sender.sendGroupMsg(groupMsg, "除了复读，无量姬还会看好康的");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                if (SENTENCE3.equals(groupMsg.getMsg()) ||
                        "我好难过，居然和一群只会复读的机器人呆在一起，呜呜呜~~~~~".equals(groupMsg.getMsg())) {
                    sender.sendGroupMsg(groupMsg, "那就和涩图在一起吧！");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                if (SENTENCE4.equals(groupMsg.getMsg())) {
                    sender.sendGroupMsg(groupMsg, "就是说，肯定是涩图看少了，所以...");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                break;
            default:

        }
        // 获取时间
        String format1 = time.tt();

        // 在控制台输出信息
        String groupMsgPutOut = "[" + format1 + "]" + "用户[" + accountInfo.getAccountNickname() + "/"
                + accountInfo.getAccountCode() + "]在群[" + groupInfo.getGroupCode()
                + "][" + groupInfo.getGroupName() + "]发送了信息：" + groupMsg.getMsg();
        System.out.println(groupMsgPutOut);

        // 将信息存入日志
        Writing writing = new Writing();
        writing.write(groupMsgPutOut + "\n");
    }

    /**
     * 解除禁言模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = "禁言解除", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void removeBan(GroupMsg groupMsg, MsgSender msgSender) {
        Setter setter = msgSender.SETTER;

        // 获取群信息-群号
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String groupId = groupInfo.getGroupCode();

        if (groupId.equals(GROUPID1)) {
            setter.setGroupBan(GROUPID1, "1591461730", 0, TimeUnit.MINUTES);
            setter.setGroupBan(GROUPID1, "2807802317", 0, TimeUnit.MINUTES);
            setter.setGroupBan(GROUPID1, "2057000856", 0, TimeUnit.MINUTES);
        }

        if (groupId.equals(GROUPID2)) {
            setter.setGroupBan(GROUPID2, "1624158591", 0, TimeUnit.MINUTES);
        }
    }

    /**
     * 请求@全体成员模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "请求@全体成员", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void atAll(GroupMsg groupMsg, MsgSender msgSender) {
        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String groupId = groupInfo.getGroupCode();

        if (groupId.equals(GROUPID1)) {
            sender.sendGroupMsg("1019170385", cat1);
        }

        if (groupId.equals(GROUPID2)) {
            sender.sendGroupMsg("695525945", cat1);
        }
    }

    /**
     * #@bot禁言模块
     * 通过正则表达是匹配禁言时间
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = "禁言*{{time,\\d+}}分钟", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void atBot(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("time") String time) {
        Setter setter = msgSender.SETTER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String groupId = groupInfo.getGroupCode();

        if (groupId.equals(GROUPID1)) {
            String banTime1 = "5";
            if (banTime1.equals(time)) {
                setter.setGroupBan(GROUPID1, "2057000856", 5, TimeUnit.MINUTES);

            } else {
                setter.setGroupBan(GROUPID1, "1591461730", Long.parseLong(time), TimeUnit.MINUTES);
                setter.setGroupBan(GROUPID1, "2807802317", Long.parseLong(time), TimeUnit.MINUTES);
            }
        }
        if (groupId.equals(GROUPID2)) {
            setter.setGroupBan("695525945", "1624158591", Long.parseLong(time), TimeUnit.MINUTES);
        }
    }


    /**
     * 人工智能回复模块
     * 在收到@时调用人工智能Api进行回复
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true)
    public void chat(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String msg = groupMsg.getMsg();
        if (!msg.contains("天气") && !msg.contains("青年大学习") && !msg.contains("禁言")
                && !msg.contains("@全体成员") && !msg.contains("来点好康的")
                && !msg.contains("看看动漫") && !msg.contains("来点原神")) {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals(GROUPID2)) {
                sender.sendGroupMsg(groupMsg, api.result(groupMsg.getText()));
            }
        }
    }

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
        if (!groupInfo.getGroupCode().equals(GROUPID2)) {
            sender.sendGroupMsg(groupMsg, api.YouthStudy());

        }
    }

    /**
     * 天气查询模块
     * 在检测到关键词和命令后调用天气API来显示天气
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     * @param city      将通过正则匹配到的关键词城市传递给天气api
     */
    @OnGroup
    @Filter(value = "{{city}}天气", matchType = MatchType.REGEX_MATCHES, trim = true)
    @Filter(value = "/tq{{city}}", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void weather(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("city") String city) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID2)) {
            if (city == null) {
                sender.sendGroupMsg(groupMsg, "天气查询失败哦~ 请输入正确的城市~");
            } else {
                sender.sendGroupMsg(groupMsg, geoApi.WeatherInfo(city));
                geoApi.adm1 = null;
                geoApi.adm2 = null;
                geoApi.id = null;
            }
        }
    }

    /**
     * 城市地理信息查询模块
     * 在检测到关键词和命令后调用地理API来显示天气
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     * @param city      将通过正则匹配到的关键词城市传递给地理api
     */
    @OnGroup
    @Filter(value = "{{city}}地理", matchType = MatchType.REGEX_MATCHES, trim = true)
    @Filter(value = "/dl{{city}}", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void geoCity(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("city") String city) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID2)) {
            if (city == null) {
                sender.sendGroupMsg(groupMsg, "城市查询失败哦~ 请输入正确的城市~");
            } else {
                sender.sendGroupMsg(groupMsg, geoApi.CityInfo(city));
                geoApi.adm1 = null;
                geoApi.adm2 = null;
                geoApi.id = null;
            }
        }
    }

    /**
     * 二刺螈模块
     * 在收到@时调用P站Api进行链接发送
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "来点好康的", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void picture(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;
        Setter setter = msgSender.SETTER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String url = api.TwoDimensional();
        String img = util.toCat("image", true, "file=" + url);

        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals(GROUPID2)) {
            sender.sendGroupMsg(groupMsg, url);


            MessageGet.MessageFlag<? extends MessageGet.MessageFlagContent>
                    flag = (MessageGet.MessageFlag<? extends MessageGet.MessageFlagContent>) msgSender.SENDER.sendGroupMsg(groupMsg, img).get();

            System.out.println(flag);


            //用于创建延时对话
            Timer timer = new Timer();

            // 延迟45秒发送消息
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    // 通过flag撤回消息
                    setter.setMsgRecall(flag);
                }
            };
            timer.schedule(timerTask, 45000);

        }
    }

    /**
     * 二刺螈模块2
     * 在收到@时调用动漫网址来发送图片
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = "看看动漫", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void picture2(GroupMsg groupMsg, MsgSender msgSender) {
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + "https://www.dmoe.cc/random.php");
        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        try {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals(GROUPID2)) {

                sender.sendGroupMsg(groupMsg, msg);
            }
        } catch (Exception e) {
            sender.sendGroupMsg(groupMsg, "超时了哦~");
        }
    }

    /**
     * 原神图片api
     * 在收到@时调用原神图片api来发送图片
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = "来点原神", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void yuanShen(GroupMsg groupMsg, MsgSender msgSender) {
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + "https://api.dujin.org/pic/yuanshen/");

        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        try {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals(GROUPID2)) {

                sender.sendGroupMsg(groupMsg, msg);
            }
        } catch (Exception e) {
            sender.sendGroupMsg(groupMsg, "超时了哦~");
        }
    }

    /**
     * 群加入申请
     *
     * @param groupAddRequest 用于获取群加入申请的请求
     * @param msgSender       用于在群聊中发送消息
     * @param groupMsg        用于获取群聊消息，群成员信息等
     */
    @OnGroupAddRequest
    public void onRequest(GroupAddRequest groupAddRequest, MsgSender msgSender, GroupMsg groupMsg) {

        //入群者信息
        AccountInfo accountInfo = groupAddRequest.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();

        // 获取时间
        String format1 = time.tt();

        // 将入群信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeRequest("[" + format1 + "][" + accountInfo.getAccountNickname()
                + "--" + accountInfo.getAccountCode() + "]申请加入群聊：[" + groupInfo.getGroupCode()
                + "/" + groupInfo.getGroupName() + "]" + "\n");
    }

    /**
     * 入群欢迎模块
     *
     * @param groupMemberIncrease 群成员增加信息
     * @param msgSender           用于在群聊中发送消息
     */
    @OnGroupMemberIncrease
    public void memberIncrease(GroupMemberIncrease groupMemberIncrease, MsgSender msgSender) {

        // 得到一个消息构建器。
        MessageContentBuilder builder = messageBuilderFactory.getMessageContentBuilder();

        //入群者信息
        AccountInfo accountInfo = groupMemberIncrease.getAccountInfo();
        GroupInfo groupInfo = groupMemberIncrease.getGroupInfo();

        Sender sender = msgSender.SENDER;

        // 获取时间
        String format1 = time.tt();

        // 将入群信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeIncrease("[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]加入群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]" + "\n");

        if (groupInfo.getGroupCode().equals(GROUPID2)) {
            THREAD_POOL = new ThreadPoolExecutor(50, 50, 3,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), r -> {
                Thread thread = new Thread(r);
                thread.setName(String.format("newThread%d", thread.getId()));
                return thread;
            });
            THREAD_POOL.execute(() -> {
            });

            //用于创建延时对话
            Timer timer = new Timer();

            // 延迟45秒发送消息
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                }
            };
            timer.schedule(timerTask, 45000);

        }

        if (groupInfo.getGroupCode().equals(GROUPID2)) {

            MessageContent msg1 = builder
                    // at当事人
                    .at(accountInfo)
                    // tips 通过 \n 换行
                    .text(" 欢迎入群！")
                    .build();
            // 通过猫猫码发送表情
            String cat2 = "[CAT:face,id=277]";
            //消息
            String msg2 = " 请看置顶消息！如果没有就当我没说";
            //消息
            String msg3 = "请及时修改昵称!";


            // 发送消息
            sender.sendGroupMsg(groupInfo, msg1);
            sender.sendGroupMsg(groupInfo, msg2 + cat2);
            sender.sendGroupMsg(groupInfo, msg3);
        }

        if (groupInfo.getGroupCode().equals(GROUPID1)) {

            MessageContent msg = builder
                    // at当事人
                    .at(accountInfo)
                    // tips 通过 \n 换行
                    .text(" 欢迎加入!")
                    .build();

            // 发送消息
            String msg2 = "新人爆照！";
            sender.sendGroupMsg(groupInfo, msg);
            sender.sendGroupMsg(groupInfo, msg2);

        }
        System.out.println("--[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]加入群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]--" + "\n");

    }

    /**
     * 群成员减少事件监听
     *
     * @param groupMemberReduce 群成员减少信息
     */
    @OnGroupMemberReduce
    public void memberReduce(GroupMemberReduce groupMemberReduce) {

        //入群者信息
        AccountInfo accountInfo = groupMemberReduce.getAccountInfo();
        GroupInfo groupInfo = groupMemberReduce.getGroupInfo();

        // 获取时间
        String format1 = time.tt();

        // 将入群信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeReduce("[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]退出群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]" + "\n");

        System.out.println("--[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]退出群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]--" + "\n");
    }

    /**
     * 好友增加事件
     *
     * @param friendIncrease 好友增加信息
     */
    @OnFriendIncrease
    public void friendIncrease(FriendIncrease friendIncrease) {
        AccountInfo accountInfo = friendIncrease.getAccountInfo();

        // 获取时间
        String format1 = time.tt();

        // 将人员信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeReduce("[" + format1 + "]你已同意["
                + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]成为好友" + "\n");

        System.out.println("--[" + format1 + "]--你已同意[" + accountInfo.getAccountNickname()
                + "--" + accountInfo.getAccountCode()
                + "]成为好友--" + "\n");
    }

    /**
     * 好友减少事件
     *
     * @param friendReduce 好友减少信息
     */
    @OnFriendReduce
    public void friendReduce(FriendReduce friendReduce) {
        AccountInfo accountInfo = friendReduce.getAccountInfo();

        // 获取时间
        String format1 = time.tt();

        // 将人员信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeRequest("[" + format1 + "][" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]离开了你" + "\n");

        System.out.println("--[" + format1 + "][" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]离开了你--" + "\n");

    }

    /**
     * 好友申请事件
     *
     * @param friendAddRequest 好友增加信息
     */
    @OnFriendAddRequest
    public void friendRequest(FriendAddRequest friendAddRequest) {
        AccountInfo accountInfo = friendAddRequest.getAccountInfo();

        // 获取时间
        String format1 = time.tt();

        // 将人员信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.friendRequest("[" + format1 + "]["
                + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]申请成为你的好友" + "\n");

        System.out.println("--[" + format1 + "][" + accountInfo.getAccountNickname()
                + "--" + accountInfo.getAccountCode()
                + "]申请成为你的好友--" + "\n");

    }
}