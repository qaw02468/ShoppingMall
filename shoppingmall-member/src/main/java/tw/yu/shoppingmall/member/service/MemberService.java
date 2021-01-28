package tw.yu.shoppingmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tw.yu.common.utils.PageUtils;
import tw.yu.shoppingmall.member.entity.MemberEntity;
import tw.yu.shoppingmall.member.exception.PhoneExistException;
import tw.yu.shoppingmall.member.exception.UserNameExistException;
import tw.yu.shoppingmall.member.vo.MemberLoginVo;
import tw.yu.shoppingmall.member.vo.MemberRegisterVo;
import tw.yu.shoppingmall.member.vo.SocialUser;

import java.util.Map;

/**
 * 會員
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo memberRegisterVo);

    void checkPhone(String phone) throws PhoneExistException;

    void checkUserName(String userName) throws UserNameExistException;

    MemberEntity login(MemberLoginVo vo);

    MemberEntity login(SocialUser socialUser);
}

