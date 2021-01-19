package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品評價
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 17:59:16
 */
@Data
@TableName("product_spu_comment")
public class SpuCommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * spu_id
     */
    private Long spuId;
    /**
     * 商品名字
     */
    private String spuName;
    /**
     * 會員暱稱
     */
    private String memberNickName;
    /**
     * 星級
     */
    private Integer star;
    /**
     * 會員ip
     */
    private String memberIp;
    /**
     * 創建時間
     */
    private Date createTime;
    /**
     * 顯示狀態[0-不顯示，1-顯示]
     */
    private Integer showStatus;
    /**
     * 購買時屬性組合
     */
    private String spuAttributes;
    /**
     * 點贊數
     */
    private Integer likesCount;
    /**
     * 回複數
     */
    private Integer replyCount;
    /**
     * 評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]
     */
    private String resources;
    /**
     * 內容
     */
    private String content;
    /**
     * 用戶頭像
     */
    private String memberIcon;
    /**
     * 評論類型[0 -對商品的直接評論，1 -對評論的回复]
     */
    private Integer commentType;

}
