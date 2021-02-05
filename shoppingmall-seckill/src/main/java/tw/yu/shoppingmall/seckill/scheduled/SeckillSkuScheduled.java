package tw.yu.shoppingmall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tw.yu.shoppingmall.seckill.service.SeckillService;

import java.util.concurrent.TimeUnit;

/**
 * @author - a89010531111@gmail.com
 */
@Slf4j
@Service
public class SeckillSkuScheduled {
    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedissonClient redissonClient;

    private final String upload_lock = "seckill:upload:lock";

    @Scheduled(cron = "0 0 0-8 * * ?")
    public void uploadSeckillSkuLatest3Day() {
        RLock lock = redissonClient.getLock(upload_lock);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            seckillService.uploadSeckillSkuLatest3Day();
        } finally {
            lock.unlock();
        }
    }
}
