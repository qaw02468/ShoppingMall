package tw.yu.shoppingmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 首頁專題表【jd首頁下面很多專題，每個專題鏈接新的頁面，展示專題商品信息】
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:04:09
 */
@Data
@TableName("sell_home_subject")
public class HomeSubjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 專題名字
     */
    private String name;
    /**
     * 專題標題
     */
    private String title;
    /**
     * 專題副標題
     */
    private String subTitle;
    /**
     * 顯示狀態
     */
    private Integer status;
    /**
     * 詳情連接
     */
    private String url;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 專題圖片地址
     */
    private String img;

}
