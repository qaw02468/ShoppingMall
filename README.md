# Shoppingmall

## 項目介紹
此項目為電商平台，用來練習前後端分離以及技術上的運用，有些小Bug尚未解決
目前擁有登入服務、購物車服務、優惠服務、用戶服務、訂單服務、商品服務、搜尋服務以及秒殺服務。
</br>
![](https://upload.cc/i1/2021/02/10/cVG8ra.png)

## 技術選用

技術|說明
-|-
SpringBoot|容器+MVC框架
SpringCloud|微服務框架
MybatisPlus|ORM框架
Nacos|配置中心和註冊中心
Minio|對象儲存
人人開源|後台管理框架
OpenFeign|服務遠程調用
Elasticsearch|搜尋引擎
RabbitMQ|消息隊列
Nginx|反向代理伺服器
Google登入|能使用Google進行註冊
Docker|應用部屬容器
Lombok|簡化對象封裝工具
MySQL|資料庫
Redis|分布式緩存
GateWay|網關
Thymeleaf|模板引擎，前後端分離，不使用JSP
Zipkin|分布式數據追蹤

## 服務結構
服務|說明
-|-
renren-fast|後台框架
shoppingmall-auth-server|登入服務
shoppingmall-cart|購物車服務
shoppingmall-common|通用工具包
shoppingmall-coupon|優惠服務
shoppingmall-gateway|網關
shoppingmall-member|用戶服務
shoppingmall-order|訂單服務
shoppingmall-product|商品服務
shoppingmall-search|搜尋服務
shoppingmall-seckill|秒殺服務
shoppingmall-third-service|第三方服務(對象儲存Minio)
shoppingmall-ware|倉儲服務
