package tw.yu.shoppingmall.seckill.service;

import tw.yu.shoppingmall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
public interface SeckillService {
    void uploadSeckillSkuLatest3Day();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);

    String kill(String killId, String key, Integer num);
}
