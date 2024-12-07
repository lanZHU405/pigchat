package pig.chat.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T object){
        Result<T> result = new Result<>();
        result.data = object;
        result.code = Codes.RESPONSE_OK;
        result.message = Codes.SUCCESS;
        return result;
    }

    public static <T> Result<T> success(T object,String message){
        Result<T> result = new Result<>();
        result.data = object;
        result.code = Codes.RESPONSE_OK;
        result.message = message;
        return result;
    }

    public static <T> Result<T> success(String message){
        Result<T> result = new Result<>();
        result.code = Codes.RESPONSE_OK;
        result.message = message;
        return result;
    }

    public static <T> Result<T> error(Integer code,String message){
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> Result<T> error(String message){
        Result<T> result = new Result<>();
        result.code = Codes.RESPONSE_NOT_OK;
        result.message = message;
        return result;
    }
}
