package com.igeek.exceptions;

public class ProductOperationException extends RuntimeException {


    private static final long serialVersionUID = -4434634833465239189L;

    /**
     * 店铺操作异常
     * @param msg
     */
    public ProductOperationException(String msg){
        super(msg);
    }
}
