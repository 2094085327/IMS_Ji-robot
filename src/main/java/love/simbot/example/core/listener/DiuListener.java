package love.simbot.example.core.listener;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.api.sender.Sender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.core.Util.CatUtil;
import love.simbot.example.core.listener.ClassBox.DiuProvider;

/**
 * @author zeng
 * @date 2022/7/25 17:43
 * @user 86188
 */
@Beans
public class DiuListener {

    /**
     * 互动模块-丢
     * sender.sendGroupMsg(groupMsg, Objects.requireNonNull(CatUtil.getAt(groupMsg.getMsg())));
     * 上方代码可以在不封装猫猫码类的时候实现获取id
     *
     * @param groupMsg  群消息
     * @param msgSender 消息发送
     */
    @OnGroup
    @Filter(value = "/丢", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void diu(GroupMsg groupMsg, MsgSender msgSender) {

        DiuProvider diuProvider = DiuProvider.DIU;
        Sender sender = msgSender.SENDER;

        // 通过猫猫码发送图片
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + diuProvider + CatUtil.getAt(groupMsg.getMsg()));
        sender.sendGroupMsg(groupMsg, msg);
    }

    /**
     * 互动模块-拍
     *
     * @param groupMsg  群消息
     * @param msgSender 消息发送
     */
    @OnGroup
    @Filter(value = "/拍", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void pai(GroupMsg groupMsg, MsgSender msgSender) {

        DiuProvider diuProvider = DiuProvider.PAI;
        Sender sender = msgSender.SENDER;

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + diuProvider + CatUtil.getAt(groupMsg.getMsg()));
        /*
         */


        sender.sendGroupMsg(groupMsg, msg);
    }

    /**
     * 互动模块-抓
     *
     * @param groupMsg  群消息
     * @param msgSender 消息发送
     */
    @OnGroup
    @Filter(value = "/抓", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void grab(GroupMsg groupMsg, MsgSender msgSender) {

        DiuProvider diuProvider = DiuProvider.GRAB;
        Sender sender = msgSender.SENDER;

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + diuProvider + CatUtil.getAt(groupMsg.getMsg()));
        sender.sendGroupMsg(groupMsg, msg);
    }

    /**
     * 互动模块-抱
     *
     * @param groupMsg  群消息
     * @param msgSender 消息发送
     */
    @OnGroup
    @Filter(value = "/抱", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void bao(GroupMsg groupMsg, MsgSender msgSender) {

        DiuProvider diuProvider = DiuProvider.BAO;
        Sender sender = msgSender.SENDER;
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + diuProvider + CatUtil.getAt(groupMsg.getMsg()));
        sender.sendGroupMsg(groupMsg, msg);
    }

    /**
     * 互动模块-锤
     *
     * @param groupMsg  群消息
     * @param msgSender 消息发送
     */
    @OnGroup
    @Filter(value = "/锤", matchType = MatchType.REGEX_MATCHES, trim = true)
    public void pound(GroupMsg groupMsg, MsgSender msgSender) {

        DiuProvider diuProvider = DiuProvider.POUND;
        Sender sender = msgSender.SENDER;
        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String msg = util.toCat("image", true, "file="
                + diuProvider + CatUtil.getAt(groupMsg.getMsg()));
        sender.sendGroupMsg(groupMsg, msg);
    }

}

