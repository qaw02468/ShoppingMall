package tw.yu.shoppingmall.seckill.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author - a89010531111@gmail.com
 */
@Data
public class SeckillSessionsWithSkus {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Date createTime;
    private List<SeckillSkuRelationEntity> relationSkus;
}