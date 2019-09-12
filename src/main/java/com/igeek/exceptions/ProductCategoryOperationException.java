package com.igeek.exceptions;

public class ProductCategoryOperationException extends RuntimeException {


    private static final long serialVersionUID = 5702900441862659449L;

    /**
     * 商品类别操作异常
     * @param msg
     */
    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
