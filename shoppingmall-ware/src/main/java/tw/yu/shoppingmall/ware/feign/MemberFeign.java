package tw.yu.shoppingmall.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.yu.common.utils.R;

/**
 * @author - a89010531111@gmail.com
 */
@FeignClient("shoppingmall-member")
public interface MemberFeign {
    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R addrInfo(@PathVariable("id") Long id);
}
