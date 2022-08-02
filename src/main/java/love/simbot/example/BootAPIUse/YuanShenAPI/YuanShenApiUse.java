package love.simbot.example.BootAPIUse.YuanShenAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author zeng
 * @date 2022/8/2 9:13
 * @user 86188
 */
public class YuanShenApiUse {
    static String url = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=e432d80a79eb2a8953331fe921c7b6dcf25aa808&timestamp=1657669515&lang=zh-cn&device_type=mobile&ext=%7b%22loc%22%3a%7b%22x%22%3a1638.1728515625%2c%22y%22%3a197.10806274414063%2c%22z%22%3a-2664.91259765625%7d%2c%22platform%22%3a%22Android%22%7d&game_version=CNRELAndroid2.8.0_R9182063_S8883030_D9206111&plat_type=android&region=cn_gf01&authkey=fXfbN602ZA3b8mIB4hN4lS%2fXeymurHnpDYZxVP9mHfP1lfHKQbpypjH4qeFs2b6arlsg1%2bURMHoOReEcqQJZD8rVYeBSgZv4RQJ8DGHm%2fKKFeIaYd3bnWNfXII%2faavGYGrM%2b%2fp6yTEhly6qV%2fYwhzcqpUcYh8I%2bPomU9MizNSPm5B9SNMQ9JVfgCh3KZUo%2bs%2b5u9FrUUSiBsTO%2b%2bxjyntv6gPjew8cW%2fZLBC2l0SskYEDlqSZ%2fURu9d9XowELc2AcFLxYMzJA0pKqJijKWtIWNP1D1d2zHYcxRWBl2JsGT%2bGeJxgmUfUpDUd7fm%2b8hC3CV4BtWcHWNmr3KWFnDhGZZuMb18rzgALe3CPENRAmtOidwqp0rbGnFZqG%2fY9omTh6EEVZmIQpsF0RHahLSI73HyqOCoUOnM0HKU%2bRHzrFKqicLn2KvURlrDPC3Am3wN8qIzHn3sMzTn%2fnr%2bMCzH6idXvfeo7dSvSntJBkYESY5LEaEoKrszd8LBWGVDK6uJdtNJ9KidrZGoRPBbXTwZY3AaX%2bSyju7bKcx5%2faVGxiPHzI0HUZKRieg42%2fnMIndTukBlrOCueoa%2fIw9r2VAx%2bRqCWg2sAtk4e4mbdGqAG1uIbaWaPEkQvNi031VIjqdSaXXfnKyEVlKSH35XE6azROqAzbvbLn%2bxTMzEFsp1hExZ2IXm76L7C6yGARx0EITY0vOVKcuMmqvXW94jIxzVrxRx9STNkm47ibdkR%2fYxxLF3XBvR%2fVW5%2fTD%2fdRkqzAiM740p8GlwsCQ1B2EMIYvnIJDpjccYbpX%2b6RsgwWQ31M1iigZKbf83e9Btc0bmcxRM0ZX0cXoq4nmp3SHMtQZe%2bkLVLxBPtRMGmPJs%2f4oG4izqoRWrabgI2q3s%2fQeqbOigXciZW4M%2b1yikuZQMQOPZhkBygTjiIgHZBYJrRWo%2fa1WCBZPD5%2fwubzdO1wx6QXdJCKx3IAddnuFWEA23F9L45V52qZw3hTIQ%2fXD4VwFRe%2fdaWaMAd7KkT36S3Ix%2fQRJe8lYeCMwZuvJRabmPX2uoJM7xpU6OP5kwjsRsNCrSdHJAcy9hzSgnKZGyJfInY2XYO%2blPptqfWJcNHdogq0bmISZSo99hC4yicPwTGac9TfvrGVaPkfZ6l09r%2bUL3S4nKiFEwMt0ROBFFoJoEyG4aYtnrzkJ%2fk4KTRywEUn25Guv536%2b6kR0sF5eE6GwnW5Q8jhEdloPxS4A8nLUnYBJIOl%2f%2fAlzRcemK16dvf%2bTl2yR0Pm8uq7pPbfE7mzTcU6SBtasI%2ftePPOo%2bOnihT7GPSs18U6gv3SZN8J3HwTYvf1d%2bQjkPZ4LZUYptseoi5dhuNSIeHHV4MTQ9hS79q4B4KyQ%3d%3d&game_biz=hk4e_cn&gacha_type=301&page=2&size=20&end_id=1659434760010553527";


    String url2 = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=e432d80a79eb2a8953331fe921c7b6dcf25aa808&timestamp=1657669515&lang=zh-cn&device_type=mobile&ext=%7b%22loc%22%3a%7b%22x%22%3a1638.1728515625%2c%22y%22%3a197.10806274414063%2c%22z%22%3a-2664.91259765625%7d%2c%22platform%22%3a%22Android%22%7d&game_version=CNRELAndroid2.8.0_R9182063_S8883030_D9206111&plat_type=android&region=cn_gf01&authkey=fXfbN602ZA3b8mIB4hN4lS%2fXeymurHnpDYZxVP9mHfP1lfHKQbpypjH4qeFs2b6arlsg1%2bURMHoOReEcqQJZD8rVYeBSgZv4RQJ8DGHm%2fKKFeIaYd3bnWNfXII%2faavGYGrM%2b%2fp6yTEhly6qV%2fYwhzcqpUcYh8I%2bPomU9MizNSPm5B9SNMQ9JVfgCh3KZUo%2bs%2b5u9FrUUSiBsTO%2b%2bxjyntv6gPjew8cW%2fZLBC2l0SskYEDlqSZ%2fURu9d9XowELc2AcFLxYMzJA0pKqJijKWtIWNP1D1d2zHYcxRWBl2JsGT%2bGeJxgmUfUpDUd7fm%2b8hC3CV4BtWcHWNmr3KWFnDhGZZuMb18rzgALe3CPENRAmtOidwqp0rbGnFZqG%2fY9omTh6EEVZmIQpsF0RHahLSI73HyqOCoUOnM0HKU%2bRHzrFKqicLn2KvURlrDPC3Am3wN8qIzHn3sMzTn%2fnr%2bMCzH6idXvfeo7dSvSntJBkYESY5LEaEoKrszd8LBWGVDK6uJdtNJ9KidrZGoRPBbXTwZY3AaX%2bSyju7bKcx5%2faVGxiPHzI0HUZKRieg42%2fnMIndTukBlrOCueoa%2fIw9r2VAx%2bRqCWg2sAtk4e4mbdGqAG1uIbaWaPEkQvNi031VIjqdSaXXfnKyEVlKSH35XE6azROqAzbvbLn%2bxTMzEFsp1hExZ2IXm76L7C6yGARx0EITY0vOVKcuMmqvXW94jIxzVrxRx9STNkm47ibdkR%2fYxxLF3XBvR%2fVW5%2fTD%2fdRkqzAiM740p8GlwsCQ1B2EMIYvnIJDpjccYbpX%2b6RsgwWQ31M1iigZKbf83e9Btc0bmcxRM0ZX0cXoq4nmp3SHMtQZe%2bkLVLxBPtRMGmPJs%2f4oG4izqoRWrabgI2q3s%2fQeqbOigXciZW4M%2b1yikuZQMQOPZhkBygTjiIgHZBYJrRWo%2fa1WCBZPD5%2fwubzdO1wx6QXdJCKx3IAddnuFWEA23F9L45V52qZw3hTIQ%2fXD4VwFRe%2fdaWaMAd7KkT36S3Ix%2fQRJe8lYeCMwZuvJRabmPX2uoJM7xpU6OP5kwjsRsNCrSdHJAcy9hzSgnKZGyJfInY2XYO%2blPptqfWJcNHdogq0bmISZSo99hC4yicPwTGac9TfvrGVaPkfZ6l09r%2bUL3S4nKiFEwMt0ROBFFoJoEyG4aYtnrzkJ%2fk4KTRywEUn25Guv536%2b6kR0sF5eE6GwnW5Q8jhEdloPxS4A8nLUnYBJIOl%2f%2fAlzRcemK16dvf%2bTl2yR0Pm8uq7pPbfE7mzTcU6SBtasI%2ftePPOo%2bOnihT7GPSs18U6gv3SZN8J3HwTYvf1d%2bQjkPZ4LZUYptseoi5dhuNSIeHHV4MTQ9hS79q4B4KyQ%3d%3d&game_biz=hk4e_cn&gacha_type=301&page=2&size=6&end_id=1659434760010553527";
    String uuu = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=e432d80a79eb2a8953331fe921c7b6dcf25aa808&timestamp=1657669515&lang=zh-cn&device_type=mobile&ext=%7b%22loc%22%3a%7b%22x%22%3a1638.1728515625%2c%22y%22%3a197.10806274414063%2c%22z%22%3a-2664.91259765625%7d%2c%22platform%22%3a%22Android%22%7d&game_version=CNRELAndroid2.8.0_R9182063_S8883030_D9206111&plat_type=android&region=cn_gf01&authkey=fXfbN602ZA3b8mIB4hN4lS%2fXeymurHnpDYZxVP9mHfP1lfHKQbpypjH4qeFs2b6arlsg1%2bURMHoOReEcqQJZD8rVYeBSgZv4RQJ8DGHm%2fKKFeIaYd3bnWNfXII%2faavGYGrM%2b%2fp6yTEhly6qV%2fYwhzcqpUcYh8I%2bPomU9MizNSPm5B9SNMQ9JVfgCh3KZUo%2bs%2b5u9FrUUSiBsTO%2b%2bxjyntv6gPjew8cW%2fZLBC2l0SskYEDlqSZ%2fURu9d9XowELc2AcFLxYMzJA0pKqJijKWtIWNP1D1d2zHYcxRWBl2JsGT%2bGeJxgmUfUpDUd7fm%2b8hC3CV4BtWcHWNmr3KWFnDhGZZuMb18rzgALe3CPENRAmtOidwqp0rbGnFZqG%2fY9omTh6EEVZmIQpsF0RHahLSI73HyqOCoUOnM0HKU%2bRHzrFKqicLn2KvURlrDPC3Am3wN8qIzHn3sMzTn%2fnr%2bMCzH6idXvfeo7dSvSntJBkYESY5LEaEoKrszd8LBWGVDK6uJdtNJ9KidrZGoRPBbXTwZY3AaX%2bSyju7bKcx5%2faVGxiPHzI0HUZKRieg42%2fnMIndTukBlrOCueoa%2fIw9r2VAx%2bRqCWg2sAtk4e4mbdGqAG1uIbaWaPEkQvNi031VIjqdSaXXfnKyEVlKSH35XE6azROqAzbvbLn%2bxTMzEFsp1hExZ2IXm76L7C6yGARx0EITY0vOVKcuMmqvXW94jIxzVrxRx9STNkm47ibdkR%2fYxxLF3XBvR%2fVW5%2fTD%2fdRkqzAiM740p8GlwsCQ1B2EMIYvnIJDpjccYbpX%2b6RsgwWQ31M1iigZKbf83e9Btc0bmcxRM0ZX0cXoq4nmp3SHMtQZe%2bkLVLxBPtRMGmPJs%2f4oG4izqoRWrabgI2q3s%2fQeqbOigXciZW4M%2b1yikuZQMQOPZhkBygTjiIgHZBYJrRWo%2fa1WCBZPD5%2fwubzdO1wx6QXdJCKx3IAddnuFWEA23F9L45V52qZw3hTIQ%2fXD4VwFRe%2fdaWaMAd7KkT36S3Ix%2fQRJe8lYeCMwZuvJRabmPX2uoJM7xpU6OP5kwjsRsNCrSdHJAcy9hzSgnKZGyJfInY2XYO%2blPptqfWJcNHdogq0bmISZSo99hC4yicPwTGac9TfvrGVaPkfZ6l09r%2bUL3S4nKiFEwMt0ROBFFoJoEyG4aYtnrzkJ%2fk4KTRywEUn25Guv536%2b6kR0sF5eE6GwnW5Q8jhEdloPxS4A8nLUnYBJIOl%2f%2fAlzRcemK16dvf%2bTl2yR0Pm8uq7pPbfE7mzTcU6SBtasI%2ftePPOo%2bOnihT7GPSs18U6gv3SZN8J3HwTYvf1d%2bQjkPZ4LZUYptseoi5dhuNSIeHHV4MTQ9hS79q4B4KyQ%3d%3d&game_biz=hk4e_cn&gacha_type=301&&page=1&size=20&begin_id=1659434760010553427";

    //public  static  String getApi(String url)
    String uuuu = "https://hk4e-api.mihoyo.com/event/gacha_info/api/getGachaLog?authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=d7d9d26fd678245ee04bec46b4bab7a8f5359c90&timestamp=1657669968&lang=zh-cn&device_type=mobile&ext={\"loc\":{\"x\":1638.23681640625,\"y\":197.108642578125,\"z\":-2664.937744140625},\"platform\":\"Android\"}&game_version=CNRELAndroid2.8.0_R9182063_S8883030_D9206111&plat_type=android&region=cn_gf01&authkey=fXfbN602ZA3b8mIB4hN4lS/XeymurHnpDYZxVP9mHfP1lfHKQbpypjH4qeFs2b6arlsg1+URMHoOReEcqQJZD8rVYeBSgZv4RQJ8DGHm/KKFeIaYd3bnWNfXII/aavGYGrM+/p6yTEhly6qV/YwhzcqpUcYh8I+PomU9MizNSPm5B9SNMQ9JVfgCh3KZUo+s+5u9FrUUSiBsTO++xjyntv6gPjew8cW/ZLBC2l0SskYEDlqSZ/URu9d9XowELc2AcFLxYMzJA0pKqJijKWtIWNP1D1d2zHYcxRWBl2JsGT+GeJxgmUfUpDUd7fm+8hC3CV4BtWcHWNmr3KWFnDhGZZuMb18rzgALe3CPENRAmtOidwqp0rbGnFZqG/Y9omTh6EEVZmIQpsF0RHahLSI73HyqOCoUOnM0HKU+RHzrFKqicLn2KvURlrDPC3Am3wN8qIzHn3sMzTn/nr+MCzH6idXvfeo7dSvSntJBkYESY5LEaEoKrszd8LBWGVDK6uJdtNJ9KidrZGoRPBbXTwZY3AaX+Syju7bKcx5/aVGxiPHzI0HUZKRieg42/nMIndTukBlrOCueoa/Iw9r2VAx+RqCWg2sAtk4e4mbdGqAG1uIbaWaPEkQvNi031VIjqdSaXXfnKyEVlKSH35XE6azROqAzbvbLn+xTMzEFsp1hExZ2IXm76L7C6yGARx0EITY0vOVKcuMmqvXW94jIxzVrxRx9STNkm47ibdkR/YxxLF3XBvR/VW5/TD/dRkqzAiM740p8GlwsCQ1B2EMIYvnIJDpjccYbpX+6RsgwWQ31M1iigZKbf83e9Btc0bmcxRM0ZX0cXoq4nmp3SHMtQZe+kLVLxBPtRMGmPJs/4oG4izqoRWrabgI2q3s/QeqbOigXciZW4M+1yikuZQMQOPZhkBygTjiIgHZBYJrRWo/a1WCBZPD5/wubzdO1wx6QXdJCKx3IAddnuFWEA23F9L45V52qZw3hTIQ/XD4VwFRe/daWaMAd7KkT36S3Ix/QRJe8pyfJvAvkiILFG/EzdK5Ok04e4T6BrLy3tmpscf6CaLimbDevv5Y7bf2eMKq8VLUnQkVz1MQsz8ADXRcjmjnyhgkTFY1gEmYIV7oI/lMcG6qRLIifiDO5fut0OY/MbE5htwInhYW12cpu9R0pRJrGRBhxGGQrH3MoAUAUzDfO69ElDw541JycIsXG4erC4x5oFEFw/sWUG3fJ4sFEeT7tAVxZutBjotw3S11zSP6nlUL9+qbw3GTGQiQ792totLE0RVeyeKghtzjyM+eDPWE1HgpkgGRZfD6+87EM3SF8DBaOuxCXtsEwXkpIMragYV26OS+VYRlzitapvsO4G47GOA==&game_biz=hk4e_cn&gacha_type=301&page=4&size=6&end_id=1659434760007566027";

    public static String toApi(String url) throws MalformedURLException {

        //System.out.println(Arrays.toString(splitUrl.split("%")));

        URL url1 = new URL(url);
        //System.out.println(url1.getQuery());//参数
        String urls1 = url1.getQuery();
        String splitUrl = urls1.replace('&', '?');

        // System.out.println(Arrays.toString(splitUrl.split("\\?")));

        return Arrays.toString(splitUrl.split("%"));
    }

    public static void main(String[] args) throws MalformedURLException {
        //toApi(url);
        for (int i = 1; i <= 5; i++) {
            System.out.println(Param.getParam("size", url, i));
        }
    }
}