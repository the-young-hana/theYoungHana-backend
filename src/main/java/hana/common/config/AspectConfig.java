package hana.common.config;

import hana.common.annotation.TypeInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@TypeInfo(name = "AspectConfig", description = "AOP 설정 클래스")
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {}
