
/* 支付订单表|记录支付订单 */
DROP TABLE IF EXISTS pay_trade_order;
CREATE TABLE `pay_trade_order` (
  `id`                BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `gmt_create`        DATETIME NOT NULL          COMMENT '创建时间',
  `gmt_modified`      DATETIME NULL DEFAULT NULL COMMENT '最后修改时间',
  `version`           INT(10) UNSIGNED NOT NULL  COMMENT '版本号',
  `product_name`      VARCHAR(100)               COMMENT '商品名称',
  `merchant_order_no` VARCHAR(32) NOT NULL       COMMENT '商户订单号',
  `total_amount`      DECIMAL(11, 2) DEFAULT 0   COMMENT '订单金额',
  `order_from`        VARCHAR(30)                COMMENT '订单来源',
  `merchant_name`     VARCHAR(100)               COMMENT '商家名称',
  `merchant_no`       VARCHAR(32) NOT NULL       COMMENT '商户编号',
  `order_time`        DATETIME                   COMMENT '下单时间',
  `order_period`      SMALLINT                   COMMENT '订单有效期(单位分钟)',
  `expire_time`       DATETIME                   COMMENT '到期时间',
  `pay_way_code`      VARCHAR(50)                COMMENT '支付方式编号',
  `pay_way_name`      VARCHAR(100)               COMMENT '支付方式名称',
  `remark`            VARCHAR(200)               COMMENT '支付备注',
  `trade_no`          VARCHAR(64)                COMMENT '交易号',
  `trade_type`        VARCHAR(50)                COMMENT '交易类型：消费、充值、提现等',
  `trade_status`      VARCHAR(50)                COMMENT '交易状态',
  `pay_type_code`     VARCHAR(50)                COMMENT '支付类型编号',
  `pay_type_name`     VARCHAR(100)               COMMENT '支付类型名称',
  `notify_url`        VARCHAR(600)               COMMENT '后台异步通知url',

  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = COMPACT
  COMMENT = '支付订单表|记录支付订单';

# 指定索引长度
ALTER TABLE pay_trade_order ADD UNIQUE INDEX uk_merchant(merchant_order_no(20), merchant_no(20));
# ALTER TABLE pay_trade_order ADD COLUMN notify_url VARCHAR(600) NULL;

/* 支付记录表|记录支付流水 */
DROP TABLE IF EXISTS pay_trade_record;
CREATE TABLE `pay_trade_record` (
  `id`                     BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `gmt_create`             DATETIME NOT NULL           COMMENT '创建时间',
  `gmt_modified`           DATETIME                    COMMENT '最后修改时间',
  `version`                INT(10) UNSIGNED NOT NULL   COMMENT '版本号',
  `product_name`           VARCHAR(50)                 COMMENT '商品名称',
  `merchant_order_no`      VARCHAR(32) NOT NULL        COMMENT '商户订单号',
  `trade_no`               VARCHAR(64) NOT NULL        COMMENT '交易号',
  `merchant_name`          VARCHAR(300)                COMMENT '商家名称',
  `merchant_no`            VARCHAR(32) NOT NULL        COMMENT '商家编号',
  `payer_no`               VARCHAR(32)                 COMMENT '付款人编号',
  `payer_name`             VARCHAR(50)                 COMMENT '付款人名称',
  `payer_pay_amount`       DECIMAL(11, 2) DEFAULT 0    COMMENT '付款方支付金额',
  `payer_fee`              DECIMAL(11, 2) DEFAULT 0    COMMENT '付款方手续费',
  `payer_account_type`     VARCHAR(30)                 COMMENT '付款方账户类型',
  `receiver_no`            VARCHAR(32)                 COMMENT '收款人编号',
  `receiver_name`          VARCHAR(50)                 COMMENT '收款人名称',
  `receiver_pay_amount`    DECIMAL(11, 2) DEFAULT 0    COMMENT '收款方支付金额',
  `receiver_fee`           DECIMAL(11, 2) DEFAULT 0    COMMENT '收款方手续费',
  `receiver_account_type`  VARCHAR(30)                 COMMENT '收款方账户类型',
  `total_amount`           DECIMAL(11, 2) DEFAULT 0    COMMENT '订单金额',
  `pay_way_code`           VARCHAR(50)                 COMMENT '支付方式编号',
  `pay_way_name`           VARCHAR(100)                COMMENT '支付方式名称',
  `pay_success_time`       DATETIME                    COMMENT '支付成功时间',
  `complete_time`          DATETIME                    COMMENT '完成时间',
  `trade_type`             VARCHAR(30)                 COMMENT '交易业务类型：消费、充值等',
  `order_from`             VARCHAR(30)                 COMMENT '订单来源',
  `pay_type_code`          VARCHAR(50)                 COMMENT '支付类型编号',
  `pay_type_name`          VARCHAR(100)                COMMENT '支付类型名称',
  `remark`                 VARCHAR(1000)               COMMENT '支付备注',

  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = COMPACT
  COMMENT = '支付记录表|记录支付流水';

# 指定索引长度
ALTER TABLE pay_trade_record ADD UNIQUE INDEX uk_trade(trade_no(20));

/* 通知记录表|记录支付通知 */
DROP TABLE IF EXISTS pay_notify_record;
CREATE TABLE `pay_notify_record` (
  `id`                     BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `gmt_create`             DATETIME NOT NULL           COMMENT '创建时间',
  `gmt_modified`           DATETIME                    COMMENT '最后修改时间',
  `merchant_order_no`      VARCHAR(50) NOT NULL        COMMENT '商户订单编号',
  `merchant_no`            VARCHAR(50) NOT NULL        COMMENT '商户编号',
  `current_times`          INT NOT NULL                COMMENT '已通知次数',
  `max_times`              INT NOT NULL                COMMENT '最大通知次数',
  `notify_url`             VARCHAR(2000)               COMMENT '异步回调地址',
  `notify_type`            VARCHAR(30)                 COMMENT '通知类型',
  `status`                 VARCHAR(50) NOT NULL        COMMENT '100:成功 101:失败',

  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = COMPACT
  COMMENT = '通知记录表|记录支付通知';

# 指定索引长度
ALTER TABLE pay_notify_record ADD UNIQUE INDEX uk_notify_merchant(merchant_order_no(20));