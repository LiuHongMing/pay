package com.senyint.pay.rpc;

import com.senyint.pay.vo.TradeVO;

/**
 * RpcResult构造器类
 */
public class RpcResultBuilder {

    /**
     * 构造器实例对象
     */
    private static final RpcResultBuilder DEFAULT = new RpcResultBuilder();

    /**
     * 返回构造器实例对象
     *
     * @return
     */
    public static RpcResultBuilder instance() {
        return DEFAULT;
    }

    /**
     * RpcResult完整参数构造
     *
     * @param isSuccess
     * @param value
     * @param code
     * @param message
     * @param throwable
     * @param <T>
     * @return
     */
    public static <T> RpcResult build(boolean isSuccess, T value, String code, String message, Throwable throwable) {
        return new RpcResult(isSuccess, value, code, message, throwable);
    }

    /**
     * 构造成功结果
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> RpcResult success(T value) {
        return build(true, value, null, null, null);
    }

    /**
     * 构造失败结果
     *
     * @param code
     * @param message
     * @return
     */
    public static RpcResult failure(String code, String message) {
        return build(false, null, code, message, null);
    }

    /**
     * 构造携带栈信息的失败结果
     *
     * @param code
     * @param message
     * @return
     */
    public static RpcResult failure(String code, String message, Throwable throwable) {
        return build(false, null, code, message, throwable);
    }

    public static void main(String[] args) {
        RpcResult rpcResult = RpcResultBuilder.success(new TradeVO());
        System.out.println(rpcResult);
    }
}
