package pig.chat.springboot.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pig.chat.springboot.common.Codes;
import pig.chat.springboot.common.Result;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    public Result<Object> serviceException(ServiceException e){
        return Result.error(e.code, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> GlobalException(Exception e){
        e.printStackTrace();
        return Result.error(Codes.RESPONSE_NOT_OK,Codes.SYSTEM_ERROR);
    }
}

