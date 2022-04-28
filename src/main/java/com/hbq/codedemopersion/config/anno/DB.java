package com.hbq.codedemopersion.config.anno;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;
/**
 * @Author: huibq
 * @Date: 2022/4/28 9:36
 * @Description: DB
 */
@Target({ElementType.FIELD,ElementType.CONSTRUCTOR}) //声明应用在属性上
@Retention(RetentionPolicy.RUNTIME) //运行期生效
@Documented //Java Doc
@Component
public @interface DB {
    String value() default "";
}