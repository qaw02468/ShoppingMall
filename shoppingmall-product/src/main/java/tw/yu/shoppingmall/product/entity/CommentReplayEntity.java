package tw.yu.shoppingmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品評價回復關系
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 21:14:49
 */
@Data
@TableName("product_comment_replay")
public class CommentReplayEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 評論id
     */
    private Long commentId;
    /**
     * 回复id
     */
    private Long replyId;

}
