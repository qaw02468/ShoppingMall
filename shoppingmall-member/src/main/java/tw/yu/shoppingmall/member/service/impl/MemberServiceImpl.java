package tw.yu.shoppingmall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.member.dao.MemberDao;
import tw.yu.shoppingmall.member.dao.MemberLevelDao;
import tw.yu.shoppingmall.member.entity.MemberEntity;
import tw.yu.shoppingmall.member.entity.MemberLevelEntity;
import tw.yu.shoppingmall.member.exception.PhoneExistException;
import tw.yu.shoppingmall.member.exception.UserNameExistException;
import tw.yu.shoppingmall.member.service.MemberService;
import tw.yu.shoppingmall.member.vo.MemberLoginVo;
import tw.yu.shoppingmall.member.vo.MemberRegisterVo;
import tw.yu.shoppingmall.member.vo.SocialUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo memberRegisterVo) {
        MemberEntity memberEntity = new MemberEntity();

        MemberLevelEntity memberLevelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(memberLevelEntity.getId());

        checkPhone(memberRegisterVo.getPhone());
        checkUserName(memberRegisterVo.getUserName());

        memberEntity.setMobile(memberRegisterVo.getPhone());
        memberEntity.setUsername(memberRegisterVo.getUserName());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        memberEntity.setPassword(bCryptPasswordEncoder.encode(memberRegisterVo.getPassword()));

        memberEntity.setCity("台北");
        memberEntity.setCreateTime(new Date());
        memberEntity.setStatus(0);
        memberEntity.setNickname(memberRegisterVo.getUserName());
        memberEntity.setBirth(new Date());
        memberEntity.setEmail("xxx@gmail.com");
        memberEntity.setGender(1);
        memberEntity.setJob("Java");

        baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhone(String phone) throws PhoneExistException {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone)) > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUserName(String userName) throws UserNameExistException {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName)) > 0) {
            throw new UserNameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String account = vo.getAccount();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        MemberEntity entity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", account)
                .or().eq("mobile", account));
        if (entity == null) {
            return null;
        } else {
            boolean matches = bCryptPasswordEncoder.matches(vo.getPassword(), entity.getPassword());
            if (matches) {
                entity.setPassword(null);
                return entity;
            } else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity login(SocialUser socialUser) {
        String id = socialUser.getId();
        MemberDao dao = this.baseMapper;
        MemberEntity entity = dao.selectOne(new QueryWrapper<MemberEntity>().eq("social_id", id));

        MemberEntity memberEntity = new MemberEntity();
        if (entity != null) {
            return entity;
        } else {
            memberEntity.setNickname(socialUser.getName());
            memberEntity.setUsername(socialUser.getName());
            memberEntity.setCity(socialUser.getLocale());
            memberEntity.setJob("自媒體");
            memberEntity.setEmail(socialUser.getEmail());

            memberEntity.setStatus(0);
            memberEntity.setCreateTime(new Date());
            memberEntity.setBirth(new Date());
            memberEntity.setLevelId(1L);
            memberEntity.setSocialId(socialUser.getId());

            dao.insert(memberEntity);
            memberEntity.setPassword(null);
            return memberEntity;
        }
    }
}