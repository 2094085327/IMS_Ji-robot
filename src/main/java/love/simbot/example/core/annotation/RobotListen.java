package love.simbot.example.core.annotation;

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;
import org.intellij.lang.annotations.JdkConstants;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

/**
 * @author zeng
 * @date 2022/7/19 20:58
 * @user 86188
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = {})
public @interface RobotListen {
    /**
     * 描述信息
     */
    String desc = " ";

    /**
     * 执行监听器所需的权限
     */

}
