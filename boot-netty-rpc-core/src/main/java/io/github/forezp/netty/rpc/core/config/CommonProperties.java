package io.github.forezp.netty.rpc.core.config;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 *         create 2018-05-28
 **/
public class CommonProperties {


    private String eurekaEnable;
    private String loadBalanceType;

    private Integer serverPoolCoreSize;
    private Integer serverPoolMaxSize;
    private String serverPoolQunueType;
    private Integer serverPoolQunueSize;
    private Long serverPoolKeepAliveTime;
    private String serverPoolRejectType;


    private Integer clientPoolCoreSize;
    private Integer clientPoolMaxSize;
    private String clientPoolQunueType;
    private Integer clientPoolQunueSize;
    private Long clientPoolKeepAliveTime;
    private String clientPoolRejectType;

    private String nettyClientRenewInterval;

    private String monitorUrl;
    private String httpConnectTimeout;
    private String httpConnectRequestTimeout;
    private String httpSocketTimeout;
    private String httpSendBufSize;
    private String httpRcvBufSize;
    private String httpBackLogSize;
    private String httpMaxTotal;

    private String monitorRedisExpire;
    private String monitorRedisExpireTimeUnit;

    public static final int CPUS = Math.max(2, Runtime.getRuntime().availableProcessors());


    public String getEurekaEnable() {
        return eurekaEnable;
    }

    public void setEurekaEnable(String eurekaEnable) {
        this.eurekaEnable = eurekaEnable;
    }

    public Integer getServerPoolCoreSize() {
        return serverPoolCoreSize;
    }

    public void setServerPoolCoreSize(Integer serverPoolCoreSize) {
        this.serverPoolCoreSize = serverPoolCoreSize;
    }

    public Integer getServerPoolMaxSize() {
        return serverPoolMaxSize;
    }

    public void setServerPoolMaxSize(Integer serverPoolMaxSize) {
        this.serverPoolMaxSize = serverPoolMaxSize;
    }

    public String getServerPoolQunueType() {
        return serverPoolQunueType;
    }

    public void setServerPoolQunueType(String serverPoolQunueType) {
        this.serverPoolQunueType = serverPoolQunueType;
    }

    public Integer getServerPoolQunueSize() {
        return serverPoolQunueSize;
    }

    public void setServerPoolQunueSize(Integer serverPoolQunueSize) {
        this.serverPoolQunueSize = serverPoolQunueSize;
    }

    public Long getServerPoolKeepAliveTime() {
        return serverPoolKeepAliveTime;
    }

    public void setServerPoolKeepAliveTime(Long serverPoolKeepAliveTime) {
        this.serverPoolKeepAliveTime = serverPoolKeepAliveTime;
    }

    public String getServerPoolRejectType() {
        return serverPoolRejectType;
    }

    public void setServerPoolRejectType(String serverPoolRejectType) {
        this.serverPoolRejectType = serverPoolRejectType;
    }

    public Integer getClientPoolCoreSize() {
        return clientPoolCoreSize;
    }

    public void setClientPoolCoreSize(Integer clientPoolCoreSize) {
        this.clientPoolCoreSize = clientPoolCoreSize;
    }

    public Integer getClientPoolMaxSize() {
        return clientPoolMaxSize;
    }

    public void setClientPoolMaxSize(Integer clientPoolMaxSize) {
        this.clientPoolMaxSize = clientPoolMaxSize;
    }

    public String getClientPoolQunueType() {
        return clientPoolQunueType;
    }

    public void setClientPoolQunueType(String clientPoolQunueType) {
        this.clientPoolQunueType = clientPoolQunueType;
    }

    public Integer getClientPoolQunueSize() {
        return clientPoolQunueSize;
    }

    public void setClientPoolQunueSize(Integer clientPoolQunueSize) {
        this.clientPoolQunueSize = clientPoolQunueSize;
    }

    public Long getClientPoolKeepAliveTime() {
        return clientPoolKeepAliveTime;
    }

    public void setClientPoolKeepAliveTime(Long clientPoolKeepAliveTime) {
        this.clientPoolKeepAliveTime = clientPoolKeepAliveTime;
    }

    public String getClientPoolRejectType() {
        return clientPoolRejectType;
    }

    public void setClientPoolRejectType(String clientPoolRejectType) {
        this.clientPoolRejectType = clientPoolRejectType;
    }

    public String getLoadBalanceType() {
        return loadBalanceType;
    }

    public void setLoadBalanceType(String loadBalanceType) {
        this.loadBalanceType = loadBalanceType;
    }

    public String getNettyClientRenewInterval() {
        return nettyClientRenewInterval;
    }

    public void setNettyClientRenewInterval(String nettyClientRenewInterval) {
        this.nettyClientRenewInterval = nettyClientRenewInterval;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String getHttpConnectTimeout() {
        return httpConnectTimeout;
    }

    public void setHttpConnectTimeout(String httpConnectTimeout) {
        this.httpConnectTimeout = httpConnectTimeout;
    }

    public String getHttpConnectRequestTimeout() {
        return httpConnectRequestTimeout;
    }

    public void setHttpConnectRequestTimeout(String httpConnectRequestTimeout) {
        this.httpConnectRequestTimeout = httpConnectRequestTimeout;
    }

    public String getHttpSocketTimeout() {
        return httpSocketTimeout;
    }

    public void setHttpSocketTimeout(String httpSocketTimeout) {
        this.httpSocketTimeout = httpSocketTimeout;
    }

    public String getHttpSendBufSize() {
        return httpSendBufSize;
    }

    public void setHttpSendBufSize(String httpSendBufSize) {
        this.httpSendBufSize = httpSendBufSize;
    }

    public String getHttpRcvBufSize() {
        return httpRcvBufSize;
    }

    public void setHttpRcvBufSize(String httpRcvBufSize) {
        this.httpRcvBufSize = httpRcvBufSize;
    }

    public String getHttpBackLogSize() {
        return httpBackLogSize;
    }

    public void setHttpBackLogSize(String httpBackLogSize) {
        this.httpBackLogSize = httpBackLogSize;
    }

    public String getHttpMaxTotal() {
        return httpMaxTotal;
    }

    public void setHttpMaxTotal(String httpMaxTotal) {
        this.httpMaxTotal = httpMaxTotal;
    }

    public String getMonitorRedisExpire() {
        return monitorRedisExpire;
    }

    public void setMonitorRedisExpire(String monitorRedisExpire) {
        this.monitorRedisExpire = monitorRedisExpire;
    }

    public String getMonitorRedisExpireTimeUnit() {
        return monitorRedisExpireTimeUnit;
    }

    public void setMonitorRedisExpireTimeUnit(String monitorRedisExpireTimeUnit) {
        this.monitorRedisExpireTimeUnit = monitorRedisExpireTimeUnit;
    }
}
