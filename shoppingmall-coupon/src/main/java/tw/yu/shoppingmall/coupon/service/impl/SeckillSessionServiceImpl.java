package tw.yu.shoppingmall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.yu.common.utils.PageUtils;
import tw.yu.common.utils.Query;
import tw.yu.shoppingmall.coupon.dao.SeckillSessionDao;
import tw.yu.shoppingmall.coupon.entity.SeckillSessionEntity;
import tw.yu.shoppingmall.coupon.entity.SeckillSkuRelationEntity;
import tw.yu.shoppingmall.coupon.service.SeckillSessionService;
import tw.yu.shoppingmall.coupon.service.SeckillSkuRelationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {
    @Autowired
    private SeckillSkuRelationService skuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionEntity> getLate3DaySession() {
        List<SeckillSessionEntity> list = this.list(new QueryWrapper<SeckillSessionEntity>().between("start_time", startTime(), endTime()));
        if (list != null && list.size() > 0) {
            return list.stream()
                    .peek(session -> {
                        Long id = session.getId();
                        List<SeckillSkuRelationEntity> entities = skuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", id));
                        session.setRelationSkus(entities);
                    }).collect(Collectors.toList());
        }
        return null;
    }

    private String startTime() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String endTime() {
        LocalDate acquired = LocalDate.now().plusDays(2);
        return LocalDateTime.of(acquired, LocalTime.MAX)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}