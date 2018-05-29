package io.github.forezp.netty.rpc.core.annotation.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;


/**
 * @author fangzhipeng
 * create 2018-05-27
 **/
public class PropertyContainsCondition implements Condition {

    String key;

    public PropertyContainsCondition(String key) {
        this.key = key;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String value = context.getEnvironment().getProperty( key );
        return StringUtils.isEmpty( value ) ? false : true;
    }
}
