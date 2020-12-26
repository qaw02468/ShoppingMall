package tw.yu.shoppingmall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 倉庫信息
 *
 * @author a8901053
 * @email a89010531111@gmail.com
 * @date 2020-12-25 22:56:33
 */
@Data
@TableName("ware_info")
public class InfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 倉庫名
     */
    private String name;
    /**
     * 倉庫地址
     */
    private String address;
    /**
     * 區域編碼
     */
    private String areacode;

}
