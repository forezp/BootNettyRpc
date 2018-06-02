package io.github.forezp.netty.rpc.core.common.constant;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 *         create 2018-05-25
 **/
public class ConfigConstants {

    public static final String NETTY_SERVSER_PORT = "netty.server.port";
    public static final String NETTY_SERVSER_NAME = "netty.server.name";
    public static final String NETTY_EUREKA_ENABLE = "netty.eureka.enable";
    public static final String NETTY_EUREKA_ENABLE_DEFAULT = "false";
    public static final String NETTY_LOADBALANCE_TYPE = "netty.loadbalance.type";
    public static final String LB_RANDOM = "random";
    public static final String LB_ROUNDROBIN = "roundrobin";

    public static final String SERVER_POOL_CORE_SIZE = "netty.server.pool.coresize";
    public static final String SERVER_POOL_CORE_SIZE_DEFAULT = "4";
    public static final String SERVER_POOL_MAX_SIZE = "netty.server.pool.maxsize";
    public static final String SERVER_POOL_MAX_SIZE_DEFAULT = "10";
    public static final String SERVER_POOL_QUEUE_TYPE = "netty.server.pool.queue.type";
    public static final String SERVER_POOL_QUEUE_TYPE_DEFAULT = "LinkedBlockingQueue";
    public static final String SERVER_POOL_QUEUE_SIZE = "netty.server.pool.queue.size";
    public static final String SERVER_POOL_QUEUE_SIZE_DEFAULT = "1024";
    public static final String SERVER_POOL_KEEPALIVE_TIME = "netty.server.pool.keepalive.time";
    public static final String SERVER_POOL_KEEPALIVE_TIME_DEFAULT = "1800000";
    public static final String SERVER_POOL_REJECT_TYPE = "netty.server.pool.reject.type";
    public static final String SERVER_POOL_REJECT_TYPE_DEFAULT = "DiscardedPolicyWithReport";

    public static final String CLIENT_POOL_CORE_SIZE = "netty.client.pool.coresize";
    public static final String CLIENT_POOL_CORE_SIZE_DEFAULT = "4";
    public static final String CLIENT_POOL_MAX_SIZE = "netty.client.pool.maxsize";
    public static final String CLIENT_POOL_MAX_SIZE_DEFAULT = "10";
    public static final String CLIENT_POOL_QUEUE_TYPE = "netty.client.pool.queue.type";
    public static final String CLIENT_POOL_QUEUE_TYPE_DEFAULT = "LinkedBlockingQueue";
    public static final String CLIENT_POOL_QUEUE_SIZE = "netty.client.pool.queue.size";
    public static final String CLIENT_POOL_QUEUE_SIZE_DEFAULT = "1024";
    public static final String CLIENT_POOL_KEEPALIVE_TIME = "netty.client.pool.keepalive.time";
    public static final String CLIENT_POOL_KEEPALIVE_TIME_DEFAULT = "1800000";
    public static final String CLIENT_POOL_REJECT_TYPE = "netty.client.pool.reject.type";
    public static final String CLIENT_POOL_REJECT_TYPE_DEFAULT = "DiscardedPolicyWithReport";

    public static final String NETTY_CLIENT_RENEW_INTERVAL = "netty.client.renew.interval";
    public static final String NETTY_CLIENT_RENEW_INTERVAL_DEFAULT = "30";//second

    public static final String MONITOR_TYPE = "monitor.type";
    public static final String MONITOR_TYPE_HTTP = "http";
    public static final String MONITOR_HTTP_URL = "monitor.url";
    public static final String MONITOR_TYPE_REDIS = "redis";

    public static final String HTTPCLIENT_CONNCT_TIMEOUT = "httpclient.connect.timeout";
    public static final String HTTPCLIENT_CONNCT_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_CONNCT_REQUEST_TIMEOUT = "httpclient.connect.request.timeout";
    public static final String HTTPCLIENT_CONNCT_REQUEST_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_SOCKET_TIMEOUT = "httpclient.socket.timeout";
    public static final String HTTPCLIENT_SOCKET_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_SEDBUFSIZE = "httpclient.send.bufsize";
    public static final String HTTPCLIENT_SEDBUFSIZE_DEFAULT = "65536";
    public static final String HTTPCLIENT_RCV_BUFSIZE = "httpclient.rcv.bufsize";
    public static final String HTTPCLIENT_RCV_BUFSIZE_DEFAULT = "65536";
    public static final String HTTPCLIENT_BACK_LOG_SIZE = "httpclient.back.logszie";
    public static final String HTTPCLIENT_BACK_LOG_SIZE_DEFAULT = "128";
    public static final String HTTPCLIENT_MAX_TOTAL = "httpclient.max.total";
    public static final String HTTPCLIENT_MAX_TOTAL_DEFAULT = "64";

    public static final String MONITOR_REDIS_EXPIRE="monotor.redis.expire";
    //-1永久
    public static final String MONITOR_REDIS_EXPIRE_DEFAULT="24";
    public static final String MONITOR_REDIS_EXPIRE_TIMEUNIT="monotor.redis.expire.timeunit";
    //hour second minute day
    public static final String MONITOR_REDIS_EXPIRE_TIMEUNIT_DEFAULT="hour";


}
