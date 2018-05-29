package io.github.forezp.netty.rpc.core.annotation.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;


/**
 * @author fangzhipeng
 * create 2018-05-27
 **/
public class PropertyEqualsCondition implements Condition {
    private String key;
    private String value;

    public PropertyEqualsCondition(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String value = context.getEnvironment().getProperty( key );
        if (StringUtils.isEmpty( value )) {
            return false;
        }
        if (value.equals( this.value )) {
            return true;
        }
        return false;
    }
}
