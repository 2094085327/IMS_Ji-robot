package love.simbot.example.core.listener;

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
import love.forte.simbot.api.message.results.GroupMemberInfo;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.api.sender.Setter;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.BootAPIUse.API;
import love.simbot.example.core.Util.CatUtil;
import love.simbot.example.core.listener.ClassBox.Constant;
import love.simbot.example.core.listener.ClassBox.PeopleChangeWrite;
import love.simbot.example.core.listener.ClassBox.TimeTranslate;
import love.simbot.example.core.listener.ClassBox.Writing;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
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
public class GroupListener extends Constant {

    /**
     * 构建机器人管理器
     */
    @Depend
    private BotManager manager;


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
            case "1019170385":
                if (USERID1.equals(personId)) {
                    break;
                }

            case "140469072":
                if (USERID1.equals(personId)) {
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
     * 关机模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "/help", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void help(GroupMsg groupMsg, MsgSender msgSender) {

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();
        if (groupBanId != 1) {
            String helps = "[无量姬的指令]\n" +
                    "一、含参指令\n" +
                    "1.【天气查询】 : /tq 城市\n(别名:XX天气)\n\n" +
                    "2.【地理查询】 : /dl 城市\n(别名:XX地理)\n\n" +
                    "3.【群友互动】: 后接@人\n " +
                    "   ①/丢   ②/拍   ③/抓\n" +
                    "    ④/抱   ⑤/锤\n\n" +
                    "4.【抽卡分析】 : 原神抽卡分析 复制到的抽卡链接\n\n" +
                    "5.【可达鸭】 : 可达鸭 左侧文字 右侧文字\n\n" +
                    "二、无参指令\n" +
                    "1.【青年大学习】:\n查看当期青年大学习问题与答案\n\n" +
                    "2.【来点好康的】:\n阿姬的珍藏品~\n\n" +
                    "3.【来点原神】:\n阿姬的原神图片\n\n" +
                    "3.【看看动漫】:\n阿姬是个二刺螈";
            msgSender.SENDER.sendGroupMsg(groupMsg, helps);
        }
    }

    /**
     * 关机模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = ".关机", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void setGroupStateClose(GroupMsg groupMsg, MsgSender msgSender) {
        AccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();

        String setUser = accountInfo.getAccountCode();
        if (groupBanId != 1) {
            if (setUser.equals(USERID1)) {
                BOOTSTATE = false;
                System.out.println("已关机");
                msgSender.SENDER.sendGroupMsg(groupMsg, "姬姬关机了！");
            } else {
                msgSender.SENDER.sendGroupMsg(groupMsg, "你没有姬姬的权限哦~");
            }
        }
    }

    /**
     * 开机模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(atBot = true, value = ".开机", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void setGroupStateOpen(GroupMsg groupMsg, MsgSender msgSender) {
        AccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String setUser = accountInfo.getAccountCode();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();
        if (groupBanId != 1) {
            if (setUser.equals(USERID1)) {
                BOOTSTATE = true;
                System.out.println("已开机");
                msgSender.SENDER.sendGroupMsg(groupMsg, "姬姬开机了！");
            } else {
                msgSender.SENDER.sendGroupMsg(groupMsg, "你没有姬姬的权限哦~");
            }
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

        AccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String groupId = groupInfo.getGroupCode();

        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();

        // 判断bot是否为管理员
        GroupMemberInfo groupMemberInfo = msgSender.GETTER.getMemberInfo(groupId, BOOTID1);

        // @的人
        String atPeople = "[CAT:at,code=" + accountInfo.getAccountCode() + "]";
        if (groupBanId != 1) {
            if (groupMemberInfo.getPermission().isAdmin() || groupMemberInfo.getPermission().isOwner()) {
                if (USERID1.equals(accountInfo.getAccountCode())) {
                    sender.sendGroupMsg(groupId, cat1);
                } else {
                    sender.sendGroupMsg(groupMsg, atPeople + "你没有权限@全体成员哦");
                }
            } else {
                sender.sendGroupMsg(groupMsg, atPeople + "阿姬没有拿到权限！" + face);
            }
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
    @Filter(value = "禁言*{{time,\\d+}}分钟", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void atBot(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("time") String time) {
        Setter setter = msgSender.SETTER;
        Sender sender = msgSender.SENDER;

        AccountInfo accountInfo = groupMsg.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();

        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();

        String groupId = groupInfo.getGroupCode();

        // 通过猫猫码获取被@的人
        String people = CatUtil.getAt(groupMsg.getMsg());

        // 判断bot是否为管理员
        GroupMemberInfo groupMemberInfo = msgSender.GETTER.getMemberInfo(groupId, BOOTID1);

        // @的人
        String atPeople = "[CAT:at,code=" + accountInfo.getAccountCode() + "]";

        if (groupBanId != 1) {
            if (groupMemberInfo.getPermission().isAdmin() || groupMemberInfo.getPermission().isOwner()) {
                if (USERID1.equals(accountInfo.getAccountCode())) {

                    assert people != null;
                    setter.setGroupBan(groupId, people, Long.parseLong(time), TimeUnit.MINUTES);
                    // setter.setGroupWholeBan(GROUPID1,true); 群禁言
                } else {
                    sender.sendGroupMsg(groupMsg, atPeople + "你没有权限禁言哦");
                }
            } else {
                sender.sendGroupMsg(groupMsg, atPeople + "阿姬没有拿到权限！" + face);
            }
        }
    }

    /**
     * 解除禁言模块
     * #@Filter() 注解为消息过滤器
     *
     * @param groupMsg  用于获取群聊消息，群成员信息等
     * @param msgSender 用于在群聊中发送消息
     */
    @OnGroup
    @Filter(value = "解除禁言", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void removeBan(GroupMsg groupMsg, MsgSender msgSender) {
        Setter setter = msgSender.SETTER;
        Sender sender = msgSender.SENDER;

        // 获取群信息-群号-个人信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        AccountInfo accountInfo = groupMsg.getAccountInfo();
        String groupId = groupInfo.getGroupCode();

        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();

        // 通过猫猫码获取被@的人
        String people = CatUtil.getAt(groupMsg.getMsg());

        // 判断bot是否为管理员
        GroupMemberInfo groupMemberInfo = msgSender.GETTER.getMemberInfo(groupId, BOOTID1);

        // @的人
        String atPeople = "[CAT:at,code=" + accountInfo.getAccountCode() + "]";

        if (groupBanId != 1) {
            if (groupMemberInfo.getPermission().isAdmin() || groupMemberInfo.getPermission().isOwner()) {
                if (USERID1.equals(accountInfo.getAccountCode())) {

                    // 将群成员设置为解除禁言的状态
                    assert people != null;
                    setter.setGroupBan(groupId, people, 0, TimeUnit.MINUTES);
                } else {
                    setter.setGroupBan(groupId, atPeople, 0, TimeUnit.MINUTES);
                    sender.sendGroupMsg(groupId, atPeople + "你没有权限解除禁言哦~");
                }
            } else {
                sender.sendGroupMsg(groupMsg, atPeople + "阿姬没有拿到权限！" + face);
            }
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
        AccountInfo accountInfo = groupMsg.getAccountInfo();
        String msg = groupMsg.getMsg();

        // 将数组通过流的形式遍历并计数有效的指令个数
        int listSize = (int) Arrays.stream(list).filter(msg::contains).count();
        int groupBanId = (int) Arrays.stream(groupBanIdList).filter(groupInfo.getGroupCode()::contains).count();

        // 当输入的msg通过contains匹配到相应指令时listSize值为1，无相应指令时为0，在无指令时调用API
        if (listSize != 1) {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (groupBanId != 1 && BOOTSTATE) {
                String reMsg = api.result(groupMsg.getText());
                sender.sendGroupMsg(groupMsg, reMsg);

                CatCodeUtil util = CatCodeUtil.INSTANCE;
                // 获取项目路径
                File file = new File(System.getProperty("user.dir"));
                System.out.println(file);
                String voice = util.toCat("voice", true, "file="
                        + api.record(reMsg));
                sender.sendGroupMsg(groupMsg, voice);
            }
            // 当被Bot在被屏蔽的群组中被@时将消息转发至User
            if (groupBanId == 1) {

                msgSender.SENDER.sendPrivateMsg(USERID1, "[" + accountInfo.getAccountCode()
                        + "-" + accountInfo.getAccountNickname() + "]正在屏蔽群组["
                        + groupInfo.getGroupCode() + "-" + groupInfo.getGroupName() + "]@Bot");
                msgSender.SENDER.sendPrivateMsg(USERID1, "消息内容为:" + groupMsg.getMsg());
            }
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
        String msg = "[" + format1 + "][" + accountInfo.getAccountNickname()
                + "--" + accountInfo.getAccountCode() + "]申请加入群聊：[" + groupInfo.getGroupCode()
                + "/" + groupInfo.getGroupName() + "]\n";
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeRequest(msg);

        if (groupInfo.getGroupCode().equals(GROUPID1) || groupInfo.getGroupCode().equals(GROUPID2) ||
                groupInfo.getGroupCode().equals(GROUPID3) || groupInfo.getGroupCode().equals(GROUPID4)) {
            msgSender.SENDER.sendPrivateMsg("2094085327", msg);
        }
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
        String msgs = "[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]加入群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]\n";
        peopleChangeWrite.writeIncrease(msgs);
        if (groupInfo.getGroupCode().equals(GROUPID1) || groupInfo.getGroupCode().equals(GROUPID2) ||
                groupInfo.getGroupCode().equals(GROUPID3)) {
            msgSender.SENDER.sendPrivateMsg("2094085327", msgs);
        }

        // 线程池
        if (groupInfo.getGroupCode().equals(GROUPID3)) {
            THREAD_POOL = new ThreadPoolExecutor(50, 50, 3,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), r -> {
                Thread thread = new Thread(r);
                thread.setName(String.format("newThread%d", thread.getId()));
                return thread;
            });

            // 创建延时消息
            THREAD_POOL.execute(() -> {
                try {
                    // 线程休眠45秒
                    Thread.sleep(45000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
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

        if (groupInfo.getGroupCode().equals(GROUPID5)) {

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
    public void memberReduce(GroupMemberReduce groupMemberReduce, MsgSender msgSender) {

        //退群者信息
        AccountInfo accountInfo = groupMemberReduce.getAccountInfo();
        GroupInfo groupInfo = groupMemberReduce.getGroupInfo();

        // 判断退群方式和获取操作员
        // 主动退群
        if (groupMemberReduce.getReduceType().equals(GroupMemberReduce.Type.LEAVE)) {
            msgSender.SENDER.sendPrivateMsg("2094085327", "[" + groupInfo.getGroupCode() + "-" + groupInfo.getGroupName() + "]["
                    + accountInfo.getAccountCode() + "-" + accountInfo.getAccountNickname() + "]是自愿退群的呢，走的" +
                    "很安详~");
        }
        // 被踢出群聊
        else {
            msgSender.SENDER.sendPrivateMsg("2094085327", "[" + groupInfo.getGroupCode() + "-" + groupInfo.getGroupName()
                    + "][" + accountInfo.getAccountCode() + "-" + accountInfo.getAccountNickname() + "]是被["
                    + Objects.requireNonNull(groupMemberReduce.getOperatorInfo()).getOperatorCode() + "-"
                    + Objects.requireNonNull(groupMemberReduce.getOperatorInfo()).getOperatorRemarkOrNickname() + "]踢出群聊的呢，走的很不甘心~");
        }

        // 获取时间
        String format1 = time.tt();

        // 将入群信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        String msg = "[" + format1 + "][" + accountInfo.getAccountCode() + "--" + accountInfo.getAccountNickname()
                + "]退出群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]" + "\n";

        peopleChangeWrite.writeReduce(msg);

        // 在控制台输出退群信息
        System.out.println(msg);
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