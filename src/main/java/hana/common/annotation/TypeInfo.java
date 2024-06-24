package hana.common.annotation;

import java.lang.annotation.*;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@TypeInfo(name = "TypeInfo", description = "TypeInfo 어노테이션 인터페이스")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TypeInfo {
    @AliasFor(annotation = Component.class)
    String value() default "";

    String name();

    String description();
}
