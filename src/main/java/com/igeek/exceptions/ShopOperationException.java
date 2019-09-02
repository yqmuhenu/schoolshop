package com.igeek.exceptions;

public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = -9088163417314205293L;

    /**
     * 店铺操作异常
     * @param msg
     */
    public ShopOperationException(String msg){
        super(msg);
    }
}
