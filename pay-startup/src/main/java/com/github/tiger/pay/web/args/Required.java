package com.github.tiger.pay.web.args;

/**
 * 类名：Required
 * 功能：公共必填参数类
 * 详细：设置接口调用的必填参数
 *
 * @author liuhongming
 */
public class Required {

    /**
     * APP标识
     */
    private String appId;

    /**
     * 签名
     */
    private String sign;

    /**
     * 请求的时间戳
     */
    private String timestamp;

    /**
     * 版本号
     */
    private String version;

    public Required() {
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Required{" +
                "appId='" + appId + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
