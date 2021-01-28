package tw.yu.shoppingmall.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.yu.common.exception.ExceptionCode;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.R;
import tw.yu.shoppingmall.member.entity.MemberEntity;
import tw.yu.shoppingmall.member.exception.PhoneExistException;
import tw.yu.shoppingmall.member.exception.UserNameExistException;
import tw.yu.shoppingmall.member.feign.CouponInfoFeign;
import tw.yu.shoppingmall.member.service.MemberService;
import tw.yu.shoppingmall.member.vo.MemberLoginVo;
import tw.yu.shoppingmall.member.vo.MemberRegisterVo;
import tw.yu.shoppingmall.member.vo.SocialUser;

import java.util.Arrays;
import java.util.Map;


/**
 * 會員
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:21:34
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponInfoFeign couponInfoFeign;

    @RequestMapping("/coupons")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("Nick");

        R memberCoupons = couponInfoFeign.memberCoupons();


        return R.ok().put("member", memberEntity)
                .put("coupons", memberCoupons.get("coupons"));

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo){
        MemberEntity memberEntity = memberService.login(vo);
        if(memberEntity != null){
            return R.ok().setData(memberEntity);
        }else {
            return R.error(ExceptionCode.LOGINACTT_PASSWORD_ERROR.getCode(), ExceptionCode.LOGINACTT_PASSWORD_ERROR.getMsg());
        }
    }


    @PostMapping("/oauth2/login")
    public R login(@RequestBody SocialUser socialUser){

        MemberEntity memberEntity = memberService.login(socialUser);
        if(memberEntity != null){
            return R.ok().setData(memberEntity);
        }else {
            return R.error(ExceptionCode.SOCIALUSER_LOGIN_ERROR.getCode(), ExceptionCode.SOCIALUSER_LOGIN_ERROR.getMsg());
        }
    }


    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo memberRegisterVo){
        try {
            memberService.register(memberRegisterVo);
        } catch (PhoneExistException e) {
            return R.error(ExceptionCode.PHONE_EXIST_EXCEPTION.getCode(), ExceptionCode.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UserNameExistException e) {
            return R.error(ExceptionCode.USER_EXIST_EXCEPTION.getCode(), ExceptionCode.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }
}
