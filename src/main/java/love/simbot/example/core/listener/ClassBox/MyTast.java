package love.simbot.example.core.listener.ClassBox;

import catcode.CatCodeUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.api.sender.BotSender;
import love.forte.simbot.bot.Bot;
import love.forte.simbot.bot.BotManager;
import love.forte.simbot.timer.Cron;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;
import love.simbot.example.BootAPIUse.API;

import java.util.concurrent.TimeUnit;

/**
 * @author zeng
 * @date 2022/6/25 0:03
 * @user 86188
 */

@Beans
@EnableTimeTask
public class MyTast extends Constant {
    public News news = new News();
    static int i = 1;

    /**
     * 调用每日一言APi
     */
    public API api = new API();

    TimeTranslate time2 = new TimeTranslate();

    /**
     * 构建机器人管理器
     */
    @Depend
    private BotManager manager;

    /**
     * 每日一言构建定时器
     * #@Fixed(value = 60, timeUnit = TimeUnit.MINUTES)
     */
    public void task() {

        Bot bot = manager.getBot("341677404");
        BotSender botSender = bot.getSender();


        botSender.SENDER.sendGroupMsg("1043409458", api.EverydaySentences());

    }

    @Cron(value = "0 32 20 * * ? *")
    public void img() {
        Bot bot = manager.getBot("341677404");
        BotSender botSender = bot.getSender();

        CatCodeUtil util = CatCodeUtil.INSTANCE;
        String img = util.toCat("image", true, "file="
                + "https://c2cpicdw.qpic.cn/offpic_new/2094085327//2094085327-1184240239-588C5FFE182E2972466B8B2403F76CBC/0?term&#61;2");

        botSender.SENDER.sendGroupMsg("1043409458", img);
    }

    //@Fixed(value = 30, timeUnit = TimeUnit.SECONDS)
    public void BLive() {
        Bot bot = manager.getBot("341677404");
        BotSender botSender = bot.getSender();
        if ("false".equals(BLIVESTATE)) {
            api.bLiveHelp(BiUpUid);
        }if ("true".equals(BLIVESTATE)){
            botSender.SENDER.sendGroupMsg("140469072", api.bLive(BiUpUid));
        }
    }

/**
 // 构建新闻定时器
 @Fixed(value = 30, timeUnit = TimeUnit.MINUTES)
 public void News() {
 Bot bot = manager.getBot("341677404");
 BotSender botSender = bot.getSender();
 CatCodeUtil util = CatCodeUtil.INSTANCE;
 int count = 0;
 while (i < news.length()) {
 i++;
 String msg = util.toCat("image", true, "file=" + news.image(i));

 botSender.SENDER.sendGroupMsg("1043409458", news.news(i) + "\n" + msg + news.URL(i));
 // botSender.SENDER.sendGroupMsg("1019170385", news.news(i) + "\n" + msg + news.URL(i));

 count += 1;

 if (count >= 3) {
 break;
 }

 }
 }*/

}