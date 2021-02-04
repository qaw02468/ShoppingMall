package tw.yu.common.exception;

/**
 * @author - a89010531111@gmail.com
 */
public class NotStockException extends RuntimeException {

    private Long skuId;

    public NotStockException(String msg) {
        super(msg + "號商品沒有足夠的庫存了");
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}