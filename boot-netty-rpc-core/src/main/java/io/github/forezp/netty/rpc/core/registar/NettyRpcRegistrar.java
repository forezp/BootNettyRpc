package io.github.forezp.netty.rpc.core.registar;

import io.github.forezp.netty.rpc.core.annotation.EnableNettyRpc;
import io.github.forezp.netty.rpc.core.annotation.RpcClient;
import org.aopalliance.intercept.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-24
 **/
public class NettyRpcRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {

    static {
        System.out.println( "  " );
        System.out.println( "╔═╗╔═╗   ╔╗" );
        System.out.println( "║║╚╝║║  ╔╝╚╗" );
        System.out.println( "║╔╗╔╗╠══╬╗╔╬═╦╦╗╔╗" );
        System.out.println( "║║║║║║╔╗║║║║╔╬╬╬╬╝" );
        System.out.println( "║║║║║║╔╗║║╚╣║║╠╬╬╗" );
        System.out.println( "╚╝╚╝╚╩╝╚╝╚═╩╝╚╩╝╚╝" );
        System.out.println( "Boot Netty Rpc .... " );
        System.out.println( "Author: Forezp. Email: miles02@163.com   " );
        System.out.println( "  " );
    }

    Logger LOG = LoggerFactory.getLogger( NettyRpcRegistrar.class );

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private Environment environment;

    public NettyRpcRegistrar() {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        logPackageScan( metadata );
        registerRpcClients( metadata, registry );
    }

    private void logPackageScan(AnnotationMetadata metadata) {
        Map<String, Object> defaultAttrs = metadata.getAnnotationAttributes( EnableNettyRpc.class.getName(), true );
        if (defaultAttrs != null && defaultAttrs.size() > 0) {
            LOG.info( "netty server package scan: " + buildPackages( (String[]) defaultAttrs.get( "basePackages" ) ) );
        }
    }

    private String buildPackages(String[] basePackages) {
        if (basePackages == null || basePackages.length == 0) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : basePackages) {
            stringBuilder.append( s ).append( "," );
        }
        stringBuilder.substring( 0, stringBuilder.length() - 2 );
        return stringBuilder.toString();
    }

    public void registerRpcClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader( this.resourceLoader );
        Set<String> basePackages;
        Map<String, Object> attrs = metadata.getAnnotationAttributes( EnableNettyRpc.class.getName() );
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter( RpcClient.class );
        final Class<?>[] clients = attrs == null ? null : (Class<?>[]) attrs.get( "clients" );
        if (clients == null || clients.length == 0) {
            scanner.addIncludeFilter( annotationTypeFilter );
            basePackages = getBasePackages( metadata );
        } else {
            final Set<String> clientClasses = new HashSet<>();
            basePackages = new HashSet<>();
            for (Class<?> clazz : clients) {
                basePackages.add( ClassUtils.getPackageName( clazz ) );
                clientClasses.add( clazz.getCanonicalName() );
            }
            AbstractClassTestingTypeFilter filter = new AbstractClassTestingTypeFilter() {
                @Override
                protected boolean match(ClassMetadata metadata) {
                    String cleaned = metadata.getClassName().replaceAll( "\\$", "." );
                    return clientClasses.contains( cleaned );
                }
            };
            scanner.addIncludeFilter( new AllTypeFilter( Arrays.asList( filter, annotationTypeFilter ) ) );
        }

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner
                    .findCandidateComponents( basePackage );
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    // verify annotated class is an interface
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                    Assert.isTrue( annotationMetadata.isInterface(),
                            "@RpcClient can only be specified on an interface" );
                    Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes( RpcClient.class.getCanonicalName() );
                    registerRpcClient( registry, annotationMetadata, attributes );
                }
            }
        }
    }

    private void registerRpcClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition( RpcClientEntity.class );
        definition.addPropertyValue( "name", attributes.get( "name" ) );
        definition.addPropertyValue( "isSyn", attributes.get( "isSyn" ) );
        definition.addPropertyValue( "host", attributes.get( "host" ) );
        definition.addPropertyValue( "port", attributes.get( "port" ) );
        definition.addPropertyValue( "rpcClz", attributes.get( "rpcClz" ) );
        definition.addPropertyValue( "traceIdIndex", attributes.get( "traceIdIndex" ) );
        definition.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_BY_NAME );

        try {
            definition.addPropertyValue( "interfaze", Class.forName( className ) );
        } catch (ClassNotFoundException e) {
            LOG.error( "Get interface for name error", e );
        }
        // definition.setAutowireMode( AbstractBeanDefinition.AUTOWIRE_BY_TYPE );
        String alias = className;
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setPrimary( false );
        definition.addPropertyValue( "interceptor", getInterceptor( beanDefinition.getPropertyValues() ) );

        BeanDefinitionHolder holder = new BeanDefinitionHolder( beanDefinition, className,
                new String[]{alias} );
        LOG.info( "registerRpcClient:" + className );
        BeanDefinitionReaderUtils.registerBeanDefinition( holder, registry );

    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {

        return new ClassPathScanningCandidateComponentProvider( false, this.environment ) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {

                    if (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().getInterfaceNames().length == 1
                            && Annotation.class.getName().equals( beanDefinition.getMetadata().getInterfaceNames()[0] )) {
                        try {
                            Class<?> target = ClassUtils.forName( beanDefinition.getMetadata().getClassName(), NettyRpcRegistrar.this.classLoader );
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            this.logger.error( "Could not load target class: " + beanDefinition.getMetadata().getClassName(), ex );

                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes( EnableNettyRpc.class.getCanonicalName() );

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get( "basePackages" )) {
            if (StringUtils.hasText( pkg )) {
                basePackages.add( pkg );
            }
        }

        if (basePackages.isEmpty()) {
            basePackages.add( ClassUtils.getPackageName( importingClassMetadata.getClassName() ) );
        }
        return basePackages;
    }

    private String resolve(String value) {
        if (StringUtils.hasText( value )) {
            return this.environment.resolvePlaceholders( value );
        }
        return value;
    }

    private static class AllTypeFilter implements TypeFilter {

        private final List<TypeFilter> delegates;

        public AllTypeFilter(List<TypeFilter> delegates) {
            Assert.notNull( delegates );
            this.delegates = delegates;
        }

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

            for (TypeFilter filter : this.delegates) {
                if (!filter.match( metadataReader, metadataReaderFactory )) {
                    return false;
                }
            }
            return true;
        }
    }

    protected MethodInterceptor getInterceptor(MutablePropertyValues annotationValues) {
        return new NettyRpcInterceptor( annotationValues );
    }
}
