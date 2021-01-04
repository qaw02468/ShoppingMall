package tw.yu.shoppingmall.product.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.yu.common.exception.ExceptionCode;
import tw.yu.common.utils.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author - a89010531111@gmail.com
 */

@Slf4j
@RestControllerAdvice(basePackages = "tw.yu.shoppingmall.product.controller")
public class ProductControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e) {
        log.error("數據校驗出現問題", e.getMessage(), e.getClass());

        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach((error) -> {
            map.put(error.getField(), error.getDefaultMessage());
        });

        return R.error(ExceptionCode.VAILD_EXCEPTION.getCode(), ExceptionCode.VAILD_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e) {

        return R.error(ExceptionCode.UNKNOW_EXCEPTION.getCode(), ExceptionCode.UNKNOW_EXCEPTION.getMsg());

    }
}
