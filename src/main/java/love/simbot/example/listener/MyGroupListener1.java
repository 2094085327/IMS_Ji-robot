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
import java.util.concurrent.TimeUnit;

/**
 * @author zeng
 * @date 2022/6/22 17:23
 * @user 86188
 */
@Beans
public class MyGroupListener1 {

    // 获取当前时间
    public TimeTranslate time = new TimeTranslate();


    // 注入得到一个消息构建器工厂。
    @Depend
    private MessageContentBuilderFactory messageBuilderFactory;

    public String cat1 = "[CAT:at,all=true]";// @全体成员的猫猫码
    public API api = new API();// 调用API接口的类
    public GeoAPI geoAPI = new GeoAPI();// 调用API接口的类


    @OnGroup
    public void Group(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;

        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();// 群成员信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();// 群信息
        String PersonId = accountInfo.getAccountCode();// 获取群成员ID
        String GroupId = groupInfo.getGroupCode();// 获取群号

        // 根据不同群号向不同人发送消息
        switch (GroupId) {
            case "1019170385":
                if (PersonId.equals("2094085327")) {
                    break;
                }

            case "140469072":
                if (PersonId.equals("2094085327")) {
                    break;
                }

            case "1043409458":
                if (groupMsg.getMsg().equals("这群里的机器人，难道只会复读？") ||
                        groupMsg.getMsg().equals("复读、复读、除了复读你们还会什么？")) {
                    sender.sendGroupMsg(groupMsg, "无量姬还会看好康的");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                    //sender.sendGroupMsg(groupMsg, "[CAT:at,code=192444746]"+"看看美女");
                }
                if (groupMsg.getMsg().equals("除了复读，你们一无是处！")) {
                    sender.sendGroupMsg(groupMsg, "除了复读，无量姬还会看好康的");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                if (groupMsg.getMsg().equals("我才不要和一群复读机呆在一起，\uD83E\uDD2C") ||
                        groupMsg.getMsg().equals("我好难过，居然和一群只会复读的机器人呆在一起，呜呜呜~~~~~")) {
                    sender.sendGroupMsg(groupMsg, "那就和涩图在一起吧！");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }
                if (groupMsg.getMsg().equals("群里的衰败，和你们的复读有很大关系。")) {
                    sender.sendGroupMsg(groupMsg, "就是说，肯定是涩图看少了，所以...");
                    sender.sendGroupMsg(groupMsg, "来点好康的");
                }

                //sender.sendGroupMsg(groupMsg, "[CAT:at,code=192444746]"+"看看美女");
        }
        // 获取时间
        String format1 = time.tt();

        // 在控制台输出信息
        String GroupMsg = "[" + format1 + "]" + "用户[" + accountInfo.getAccountNickname() + "/"
                + accountInfo.getAccountCode() + "]在群[" + groupInfo.getGroupCode()
                + "][" + groupInfo.getGroupName() + "]发送了信息：" + groupMsg.getMsg();
        System.out.println(GroupMsg);

        // 将信息存入日志
        Writing writing = new Writing();
        writing.write(GroupMsg + "\n");
    }

    // 解除禁言模块
    @OnGroup
    // @Filter() 注解为消息过滤器
    @Filter(atBot = true, value = "禁言解除", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void RemoveBan(GroupMsg groupMsg, MsgSender msgSender) {
        Setter setter = msgSender.SETTER;

        // 获取群信息-群号
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String GroupId = groupInfo.getGroupCode();

        if (GroupId.equals("1019170385")) {
            setter.setGroupBan("1019170385", "1591461730", 0, TimeUnit.MINUTES);
            setter.setGroupBan("1019170385", "2807802317", 0, TimeUnit.MINUTES);
            setter.setGroupBan("1019170385", "2057000856", 0, TimeUnit.MINUTES);
        }

        if (GroupId.equals("695525945")) {
            setter.setGroupBan("695525945", "1624158591", 0, TimeUnit.MINUTES);
        }
    }

    //请求@全体成员模块
    @OnGroup
    @Filter(value = "请求@全体成员", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void AtAll(GroupMsg groupMsg, MsgSender msgSender) {
        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String GroupId = groupInfo.getGroupCode();

        if (GroupId.equals("1019170385")) {
            sender.sendGroupMsg("1019170385", cat1);
        }

        if (GroupId.equals("695525945")) {
            sender.sendGroupMsg("695525945", cat1);
        }
    }

    //@bot禁言模块
    @OnGroup
    // 通过正则表达是匹配禁言时间
    @Filter(atBot = true, value = "禁言*{{time,\\d+}}分钟", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void AtBot(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("time") String time) {
        Setter setter = msgSender.SETTER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String GroupId = groupInfo.getGroupCode();

        if (GroupId.equals("1019170385")) {
            if (time.equals("5")) {
                setter.setGroupBan("1019170385", "2057000856", 5, TimeUnit.MINUTES);

            } else {
                setter.setGroupBan("1019170385", "1591461730", Long.parseLong(time), TimeUnit.MINUTES);
                setter.setGroupBan("1019170385", "2807802317", Long.parseLong(time), TimeUnit.MINUTES);
            }
        }
        if (GroupId.equals("695525945")) {
            setter.setGroupBan("695525945", "1624158591", Long.parseLong(time), TimeUnit.MINUTES);
        }
    }

    // 人工智能回复模块
    @OnGroup
    // 在收到@时调用人工智能Api进行回复
    @Filter(atBot = true)
    public void Chat(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        String msg = groupMsg.getMsg();
        if (!msg.contains("天气") && !msg.contains("青年大学习") && !msg.contains("禁言")
                && !msg.contains("@全体成员") && !msg.contains("来点好康的")
                && !msg.contains("看看动漫") && !msg.contains("来点原神")) {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals("637384877")) {
                sender.sendGroupMsg(groupMsg, api.result(groupMsg.getText()));
            }
        }
    }

    // 青年大学习模块
    @OnGroup
    // 在收到@时调用青年大学习Api进行回复
    @Filter(atBot = true, value = "青年大学习", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void YouthStudy(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals("637384877")) {
            sender.sendGroupMsg(groupMsg, api.YouthStudy());

        }
    }

    // 天气查询模块
    @OnGroup
    // 在收到@时调用青年大学习Api进行回复
    @Filter(value = "{{city}}天气", matchType = MatchType.REGEX_MATCHES, trim = true)
    @Filter(value = "/tq{{city}}", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void YouthStudy(GroupMsg groupMsg, MsgSender msgSender, @FilterValue("city") String city) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();
        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals("637384877")) {
            if (city == null) {
                sender.sendGroupMsg(groupMsg, "天气查询失败哦~ 请输入正确的城市~");
            } else {
                sender.sendGroupMsg(groupMsg, geoAPI.WeatherInfo(city));
                geoAPI.adm1 = null;
                geoAPI.adm2 = null;
                geoAPI.id = null;
            }
        }
    }

    // 二刺螈模块 
    @OnGroup
    // 在收到@时调用P站Api进行链接发送
    @Filter(value = "来点好康的", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void Picture(GroupMsg groupMsg, MsgSender msgSender) {

        Sender sender = msgSender.SENDER;

        GroupInfo groupInfo = groupMsg.getGroupInfo();

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String url = api.TwoDimensional();
        String msg = util.toCat("image", true, "file=" + url);

        // 将群号为“637384877”的群排除在人工智能答复模块外
        if (!groupInfo.getGroupCode().equals("637384877")) {
            sender.sendGroupMsg(groupMsg, url);
            sender.sendGroupMsg(groupMsg, msg);

        }
    }

    // 二刺螈模块2
    @OnGroup
    @Filter(atBot = true, value = "看看动漫", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void Picture2(GroupMsg groupMsg, MsgSender msgSender) {
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file=" + "https://www.dmoe.cc/random.php");
        //String msg=util.toCat("image", true, "file="+ "https://imgapi.xl0408.top");
        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        try {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals("637384877")) {

                sender.sendGroupMsg(groupMsg, msg);
            }
        } catch (Exception e) {
            sender.sendGroupMsg(groupMsg, "超时了哦~");
        }
    }


    // 原神图片api
    @OnGroup
    @Filter(atBot = true, value = "来点原神", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void YuanShen(GroupMsg groupMsg, MsgSender msgSender) {
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + "https://api.dujin.org/pic/yuanshen/");
        //String msg=util.toCat("image", true, "file="+ "https://imgapi.xl0408.top");
        Sender sender = msgSender.SENDER;
        GroupInfo groupInfo = groupMsg.getGroupInfo();
        try {
            // 将群号为“637384877”的群排除在人工智能答复模块外
            if (!groupInfo.getGroupCode().equals("637384877")) {

                sender.sendGroupMsg(groupMsg, msg);
            }
        } catch (Exception e) {
            sender.sendGroupMsg(groupMsg, "超时了哦~");
        }
    }

    // 群加入申请
    @OnGroupAddRequest
    public void onRequest(GroupAddRequest groupAddRequest, MsgSender msgSender, GroupMsg groupMsg) {

        //入群者信息
        AccountInfo accountInfo = groupAddRequest.getAccountInfo();
        GroupInfo groupInfo = groupMsg.getGroupInfo();

        // 获取时间
        String format1 = time.tt();

        // 将入群信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeRequest("[" + format1 + "][" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]申请加入群聊：[" + groupInfo.getGroupCode() + "/" + groupInfo.getGroupName() + "]" + "\n");

    }

    // 入群欢迎模块
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

        if (groupInfo.getGroupCode().equals("637384877")) {
            Timer timer = new Timer();//用于创建延时对话

            // 假设我们的迎新消息是这样的：
            //@xxx 欢迎入群！
            MessageContent msg = builder
                    // at当事人
                    .at(accountInfo)
                    // tips 通过 \n 换行
                    .text(" 欢迎加入！")
                    .build();

            // 延迟45秒发送消息
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    // sender.sendGroupMsg(groupInfo, msg);

                }
            };
            timer.schedule(timerTask, 45000);

        }

        if (groupInfo.getGroupCode().equals("695525945")) {

            MessageContent msg1 = builder
                    // at当事人
                    .at(accountInfo)
                    // tips 通过 \n 换行
                    .text(" 欢迎入群！")
                    .build();

            String cat2 = "[CAT:face,id=277]";// 通过猫猫码发送表情
            String msg2 = " 请看置顶消息！如果没有就当我没说";//消息
            String msg3 = "请及时修改昵称!";//消息


            // 发送消息
            sender.sendGroupMsg(groupInfo, msg1);
            sender.sendGroupMsg(groupInfo, msg2 + cat2);
            sender.sendGroupMsg(groupInfo, msg3);
        }

        if (groupInfo.getGroupCode().equals("1043409458")) {

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

    // 群成员减少监听
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

    // 好友增加事件
    @OnFriendIncrease
    public void friendIncrease(FriendIncrease friendIncrease) {
        AccountInfo accountInfo = friendIncrease.getAccountInfo();

        // 获取时间
        String format1 = time.tt();

        // 将人员信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.writeReduce("[" + format1 + "]你已同意[" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]成为好友" + "\n");

        System.out.println("--[" + format1 + "]--你已同意[" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]成为好友--" + "\n");
    }

    // 好友减少事件
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

    // 好友申请事件
    @OnFriendAddRequest
    public void friendRequest(FriendAddRequest friendAddRequest) {
        AccountInfo accountInfo = friendAddRequest.getAccountInfo();

        // 获取时间
        String format1 = time.tt();

        // 将人员信息存入日志
        PeopleChangeWrite peopleChangeWrite = new PeopleChangeWrite();
        peopleChangeWrite.friendRequest("[" + format1 + "][" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]申请成为你的好友" + "\n");

        System.out.println("--[" + format1 + "][" + accountInfo.getAccountNickname() + "--" + accountInfo.getAccountCode()
                + "]申请成为你的好友--" + "\n");

    }
}