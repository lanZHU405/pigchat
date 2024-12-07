package pig.chat.springboot.exception;

import pig.chat.springboot.common.Codes;

public class ServiceException extends RuntimeException{

    public Integer code;

    public ServiceException(String msg){
        super(msg);
        this.code = Codes.RESPONSE_NOT_OK;
    }

    public ServiceException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

}
