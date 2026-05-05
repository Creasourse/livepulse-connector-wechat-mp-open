-- 微信服务号连接器 PostgreSQL 表结构
-- 版本: 2.0
-- 说明: 支持多账号管理、用户/素材/消息同步、Webhook 事件订阅

-- ============================================
-- 清理已存在的表（按依赖关系顺序）
-- ============================================
DROP TABLE IF EXISTS wechat_mp_news CASCADE;
DROP TABLE IF EXISTS wechat_mp_message CASCADE;
DROP TABLE IF EXISTS wechat_mp_material CASCADE;
DROP TABLE IF EXISTS wechat_mp_user CASCADE;
DROP TABLE IF EXISTS wechat_mp_menu CASCADE;
DROP TABLE IF EXISTS wechat_mp_tag CASCADE;
DROP TABLE IF EXISTS wechat_mp_webhook_log CASCADE;
DROP TABLE IF EXISTS wechat_mp_sync_log CASCADE;
DROP TABLE IF EXISTS wechat_mp_account CASCADE;

-- ============================================
-- 1. 微信服务号账户配置表
-- ============================================
CREATE TABLE wechat_mp_account (
    id BIGSERIAL PRIMARY KEY,
    account_id VARCHAR(255) NOT NULL,
    account_name VARCHAR(500),
    app_id VARCHAR(255) NOT NULL,
    app_secret VARCHAR(500) NOT NULL,
    token VARCHAR(500),
    encoding_aes_key VARCHAR(500),
    account_type VARCHAR(50),
    service_type_info VARCHAR(100),
    verify_type_info VARCHAR(100),
    business_info VARCHAR(200),
    principal_name VARCHAR(200),
    signature VARCHAR(500),
    enabled BOOLEAN DEFAULT TRUE,
    sync_status VARCHAR(50) DEFAULT 'pending',
    last_user_sync_time TIMESTAMP,
    last_material_sync_time TIMESTAMP,
    last_message_sync_time TIMESTAMP,
    webhook_enabled BOOLEAN DEFAULT FALSE,
    webhook_url VARCHAR(500),
    last_error_message TEXT,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(100),
    update_by VARCHAR(100),
    CONSTRAINT uk_wechat_mp_account_id UNIQUE (account_id)
);

-- 表注释
COMMENT ON TABLE wechat_mp_account IS '微信服务号账户配置表';
COMMENT ON COLUMN wechat_mp_account.account_id IS '账户唯一标识';
COMMENT ON COLUMN wechat_mp_account.account_name IS '公众号名称';
COMMENT ON COLUMN wechat_mp_account.app_id IS '应用 ID';
COMMENT ON COLUMN wechat_mp_account.app_secret IS '应用密钥';
COMMENT ON COLUMN wechat_mp_account.token IS '令牌';
COMMENT ON COLUMN wechat_mp_account.encoding_aes_key IS '消息加密密钥';
COMMENT ON COLUMN wechat_mp_account.account_type IS '账号类型: SERVICE/SUBSCRIPTION';
COMMENT ON COLUMN wechat_mp_account.service_type_info IS '服务类型信息';
COMMENT ON COLUMN wechat_mp_account.verify_type_info IS '认证类型信息';
COMMENT ON COLUMN wechat_mp_account.business_info IS '功能介绍';
COMMENT ON COLUMN wechat_mp_account.principal_name IS '主体名称';
COMMENT ON COLUMN wechat_mp_account.signature IS '签名';
COMMENT ON COLUMN wechat_mp_account.enabled IS '是否启用';
COMMENT ON COLUMN wechat_mp_account.sync_status IS '同步状态: pending/syncing/success/failed';
COMMENT ON COLUMN wechat_mp_account.last_user_sync_time IS '最后用户同步时间';
COMMENT ON COLUMN wechat_mp_account.last_material_sync_time IS '最后素材同步时间';
COMMENT ON COLUMN wechat_mp_account.last_message_sync_time IS '最后消息同步时间';
COMMENT ON COLUMN wechat_mp_account.webhook_enabled IS '是否启用 Webhook';
COMMENT ON COLUMN wechat_mp_account.webhook_url IS 'Webhook 回调 URL';
COMMENT ON COLUMN wechat_mp_account.last_error_message IS '最后错误信息';
COMMENT ON COLUMN wechat_mp_account.retry_count IS '重试次数';
COMMENT ON COLUMN wechat_mp_account.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_account.update_time IS '更新时间';
COMMENT ON COLUMN wechat_mp_account.create_by IS '创建人';
COMMENT ON COLUMN wechat_mp_account.update_by IS '更新人';

-- ============================================
-- 2. 微信服务号用户表
-- ============================================
CREATE TABLE wechat_mp_user (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    openid VARCHAR(255) NOT NULL,
    nickname VARCHAR(500),
    sex INT DEFAULT 0,
    language VARCHAR(50),
    city VARCHAR(100),
    province VARCHAR(100),
    country VARCHAR(100),
    head_img_url VARCHAR(1000),
    subscribe_time TIMESTAMP,
    unsubscribe_time TIMESTAMP,
    subscribe_status INT DEFAULT 1,
    group_id BIGINT,
    remark VARCHAR(500),
    user_tags TEXT,
    qr_scene_str VARCHAR(255),
    created_time_wechat TIMESTAMP,
    updated_time_wechat TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_user_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_wechat_mp_openid UNIQUE (account_id, openid)
);

CREATE INDEX idx_wechat_mp_user_account_id ON wechat_mp_user(account_id);
CREATE INDEX idx_wechat_mp_user_subscribe_status ON wechat_mp_user(subscribe_status);
CREATE INDEX idx_wechat_mp_user_subscribe_time ON wechat_mp_user(subscribe_time);
CREATE INDEX idx_wechat_mp_user_nickname ON wechat_mp_user(nickname);

-- 表注释
COMMENT ON TABLE wechat_mp_user IS '微信服务号用户表';
COMMENT ON COLUMN wechat_mp_user.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_user.openid IS '用户 OpenID';
COMMENT ON COLUMN wechat_mp_user.nickname IS '昵称';
COMMENT ON COLUMN wechat_mp_user.sex IS '性别: 0=未知, 1=男, 2=女';
COMMENT ON COLUMN wechat_mp_user.language IS '语言';
COMMENT ON COLUMN wechat_mp_user.city IS '城市';
COMMENT ON COLUMN wechat_mp_user.province IS '省份';
COMMENT ON COLUMN wechat_mp_user.country IS '国家';
COMMENT ON COLUMN wechat_mp_user.head_img_url IS '头像 URL';
COMMENT ON COLUMN wechat_mp_user.subscribe_time IS '关注时间';
COMMENT ON COLUMN wechat_mp_user.unsubscribe_time IS '取消关注时间';
COMMENT ON COLUMN wechat_mp_user.subscribe_status IS '关注状态: 0=未关注, 1=已关注';
COMMENT ON COLUMN wechat_mp_user.group_id IS '用户分组 ID';
COMMENT ON COLUMN wechat_mp_user.remark IS '备注名';
COMMENT ON COLUMN wechat_mp_user.user_tags IS '用户标签 (JSON)';
COMMENT ON COLUMN wechat_mp_user.qr_scene_str IS '二维码场景值';
COMMENT ON COLUMN wechat_mp_user.created_time_wechat IS '微信创建时间';
COMMENT ON COLUMN wechat_mp_user.updated_time_wechat IS '微信更新时间';
COMMENT ON COLUMN wechat_mp_user.processed IS '是否已处理';
COMMENT ON COLUMN wechat_mp_user.processed_time IS '处理时间';
COMMENT ON COLUMN wechat_mp_user.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_user.update_time IS '更新时间';

-- ============================================
-- 3. 微信服务号素材表
-- ============================================
CREATE TABLE wechat_mp_material (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    media_id VARCHAR(500) NOT NULL,
    material_type VARCHAR(50) NOT NULL,
    title VARCHAR(500),
    description TEXT,
    name VARCHAR(500),
    url VARCHAR(1000),
    thumb_media_id VARCHAR(500),
    thumb_url VARCHAR(1000),
    media_type VARCHAR(50),
    created_time_wechat TIMESTAMP,
    updated_time_wechat TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_material_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_wechat_mp_media_id UNIQUE (account_id, media_id)
);

CREATE INDEX idx_wechat_mp_material_account_id ON wechat_mp_material(account_id);
CREATE INDEX idx_wechat_mp_material_type ON wechat_mp_material(material_type);
CREATE INDEX idx_wechat_mp_material_create_time ON wechat_mp_material(created_time_wechat);

-- 表注释
COMMENT ON TABLE wechat_mp_material IS '微信服务号素材表';
COMMENT ON COLUMN wechat_mp_material.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_material.media_id IS '素材媒体 ID';
COMMENT ON COLUMN wechat_mp_material.material_type IS '素材类型: IMAGE/VOICE/VIDEO/THUMB/news';
COMMENT ON COLUMN wechat_mp_material.title IS '素材标题';
COMMENT ON COLUMN wechat_mp_material.description IS '素材描述';
COMMENT ON COLUMN wechat_mp_material.name IS '素材名称';
COMMENT ON COLUMN wechat_mp_material.url IS '素材 URL';
COMMENT ON COLUMN wechat_mp_material.thumb_media_id IS '缩略图媒体 ID';
COMMENT ON COLUMN wechat_mp_material.thumb_url IS '缩略图 URL';
COMMENT ON COLUMN wechat_mp_material.media_type IS '媒体类型';
COMMENT ON COLUMN wechat_mp_material.created_time_wechat IS '微信创建时间';
COMMENT ON COLUMN wechat_mp_material.updated_time_wechat IS '微信更新时间';
COMMENT ON COLUMN wechat_mp_material.processed IS '是否已处理';
COMMENT ON COLUMN wechat_mp_material.processed_time IS '处理时间';
COMMENT ON COLUMN wechat_mp_material.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_material.update_time IS '更新时间';

-- ============================================
-- 4. 微信服务号消息表
-- ============================================
CREATE TABLE wechat_mp_message (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    msg_id VARCHAR(255) NOT NULL,
    msg_type VARCHAR(50) NOT NULL,
    openid VARCHAR(255) NOT NULL,
    content TEXT,
    media_id VARCHAR(500),
    msg_data JSONB,
    create_time_wechat TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_message_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_wechat_mp_msg_id UNIQUE (account_id, msg_id)
);

CREATE INDEX idx_wechat_mp_message_account_id ON wechat_mp_message(account_id);
CREATE INDEX idx_wechat_mp_message_openid ON wechat_mp_message(openid);
CREATE INDEX idx_wechat_mp_message_type ON wechat_mp_message(msg_type);
CREATE INDEX idx_wechat_mp_message_create_time ON wechat_mp_message(create_time_wechat);

-- 表注释
COMMENT ON TABLE wechat_mp_message IS '微信服务号消息表';
COMMENT ON COLUMN wechat_mp_message.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_message.msg_id IS '消息 ID';
COMMENT ON COLUMN wechat_mp_message.msg_type IS '消息类型: text/image/voice/video/news/event';
COMMENT ON COLUMN wechat_mp_message.openid IS '用户 OpenID';
COMMENT ON COLUMN wechat_mp_message.content IS '消息内容';
COMMENT ON COLUMN wechat_mp_message.media_id IS '媒体 ID';
COMMENT ON COLUMN wechat_mp_message.msg_data IS '消息数据 (JSON)';
COMMENT ON COLUMN wechat_mp_message.create_time_wechat IS '微信创建时间';
COMMENT ON COLUMN wechat_mp_message.processed IS '是否已处理';
COMMENT ON COLUMN wechat_mp_message.processed_time IS '处理时间';
COMMENT ON COLUMN wechat_mp_message.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_message.update_time IS '更新时间';

-- ============================================
-- 5. 微信服务号标签表
-- ============================================
CREATE TABLE wechat_mp_tag (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    tag_name VARCHAR(100) NOT NULL,
    user_count INT DEFAULT 0,
    created_time_wechat TIMESTAMP,
    updated_time_wechat TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_tag_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_wechat_mp_tag_id UNIQUE (account_id, tag_id)
);

CREATE INDEX idx_wechat_mp_tag_account_id ON wechat_mp_tag(account_id);
CREATE INDEX idx_wechat_mp_tag_name ON wechat_mp_tag(tag_name);

-- 表注释
COMMENT ON TABLE wechat_mp_tag IS '微信服务号标签表';
COMMENT ON COLUMN wechat_mp_tag.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_tag.tag_id IS '标签 ID';
COMMENT ON COLUMN wechat_mp_tag.tag_name IS '标签名称';
COMMENT ON COLUMN wechat_mp_tag.user_count IS '用户数量';
COMMENT ON COLUMN wechat_mp_tag.created_time_wechat IS '微信创建时间';
COMMENT ON COLUMN wechat_mp_tag.updated_time_wechat IS '微信更新时间';
COMMENT ON COLUMN wechat_mp_tag.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_tag.update_time IS '更新时间';

-- ============================================
-- 6. 微信服务号菜单表
-- ============================================
CREATE TABLE wechat_mp_menu (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    menu_id BIGINT,
    parent_id BIGINT,
    menu_type VARCHAR(50),
    menu_key VARCHAR(100),
    name VARCHAR(100),
    value VARCHAR(500),
    menu_content TEXT,
    sort_order INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_menu_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE
);

CREATE INDEX idx_wechat_mp_menu_account_id ON wechat_mp_menu(account_id);
CREATE INDEX idx_wechat_mp_menu_parent_id ON wechat_mp_menu(parent_id);
CREATE INDEX idx_wechat_mp_menu_sort_order ON wechat_mp_menu(sort_order);

-- 表注释
COMMENT ON TABLE wechat_mp_menu IS '微信服务号菜单表';
COMMENT ON COLUMN wechat_mp_menu.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_menu.menu_id IS '菜单 ID';
COMMENT ON COLUMN wechat_mp_menu.parent_id IS '父菜单 ID';
COMMENT ON COLUMN wechat_mp_menu.menu_type IS '菜单类型: click/view/media_id/scancode_waitmsg';
COMMENT ON COLUMN wechat_mp_menu.menu_key IS '菜单 KEY';
COMMENT ON COLUMN wechat_mp_menu.name IS '菜单名称';
COMMENT ON COLUMN wechat_mp_menu.value IS '菜单值';
COMMENT ON COLUMN wechat_mp_menu.menu_content IS '菜单内容 (JSON)';
COMMENT ON COLUMN wechat_mp_menu.sort_order IS '排序';
COMMENT ON COLUMN wechat_mp_menu.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_menu.update_time IS '更新时间';

-- ============================================
-- 7. 微信服务号图文消息表
-- ============================================
CREATE TABLE wechat_mp_news (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    news_id VARCHAR(500) NOT NULL,
    title VARCHAR(500),
    author VARCHAR(200),
    digest VARCHAR(1000),
    content TEXT,
    content_source_url VARCHAR(1000),
    thumb_media_id VARCHAR(500),
    thumb_url VARCHAR(1000),
    show_cover_pic INT DEFAULT 0,
    need_open_comment INT DEFAULT 0,
    only_fans_can_comment INT DEFAULT 0,
    created_time_wechat TIMESTAMP,
    updated_time_wechat TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    processed_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_news_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE,
    CONSTRAINT uk_wechat_mp_news_id UNIQUE (account_id, news_id)
);

CREATE INDEX idx_wechat_mp_news_account_id ON wechat_mp_news(account_id);
CREATE INDEX idx_wechat_mp_news_create_time ON wechat_mp_news(created_time_wechat);

-- 表注释
COMMENT ON TABLE wechat_mp_news IS '微信服务号图文消息表';
COMMENT ON COLUMN wechat_mp_news.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_news.news_id IS '图文消息 ID';
COMMENT ON COLUMN wechat_mp_news.title IS '标题';
COMMENT ON COLUMN wechat_mp_news.author IS '作者';
COMMENT ON COLUMN wechat_mp_news.digest IS '摘要';
COMMENT ON COLUMN wechat_mp_news.content IS '正文内容';
COMMENT ON COLUMN wechat_mp_news.content_source_url IS '原文链接';
COMMENT ON COLUMN wechat_mp_news.thumb_media_id IS '封面图媒体 ID';
COMMENT ON COLUMN wechat_mp_news.thumb_url IS '封面图 URL';
COMMENT ON COLUMN wechat_mp_news.show_cover_pic IS '是否显示封面: 0=不显示, 1=显示';
COMMENT ON COLUMN wechat_mp_news.need_open_comment IS '是否打开评论: 0=不打开, 1=打开';
COMMENT ON COLUMN wechat_mp_news.only_fans_can_comment IS '是否只有粉丝可以评论: 0=所有人, 1=粉丝';
COMMENT ON COLUMN wechat_mp_news.created_time_wechat IS '微信创建时间';
COMMENT ON COLUMN wechat_mp_news.updated_time_wechat IS '微信更新时间';
COMMENT ON COLUMN wechat_mp_news.processed IS '是否已处理';
COMMENT ON COLUMN wechat_mp_news.processed_time IS '处理时间';
COMMENT ON COLUMN wechat_mp_news.create_time IS '创建时间';
COMMENT ON COLUMN wechat_mp_news.update_time IS '更新时间';

-- ============================================
-- 8. 微信服务号 Webhook 日志表
-- ============================================
CREATE TABLE wechat_mp_webhook_log (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT,
    event_type VARCHAR(255) NOT NULL,
    event_id BIGINT,
    resource_type VARCHAR(100),
    resource_id BIGINT,
    payload JSONB,
    headers JSONB,
    processed_status VARCHAR(50) DEFAULT 'pending',
    error_message TEXT,
    received_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_time TIMESTAMP,
    retry_count INT DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_webhook_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE SET NULL
);

CREATE INDEX idx_wechat_mp_webhook_account_id ON wechat_mp_webhook_log(account_id);
CREATE INDEX idx_wechat_mp_webhook_event_type ON wechat_mp_webhook_log(event_type);
CREATE INDEX idx_wechat_mp_webhook_status ON wechat_mp_webhook_log(processed_status);
CREATE INDEX idx_wechat_mp_webhook_resource ON wechat_mp_webhook_log(resource_type, resource_id);
CREATE INDEX idx_wechat_mp_webhook_received_time ON wechat_mp_webhook_log(received_time);

-- 表注释
COMMENT ON TABLE wechat_mp_webhook_log IS '微信服务号 Webhook 事件日志表';
COMMENT ON COLUMN wechat_mp_webhook_log.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_webhook_log.event_type IS '事件类型: subscribe/unsubscribe/SCAN/LOCATION/CLICK';
COMMENT ON COLUMN wechat_mp_webhook_log.event_id IS '事件 ID';
COMMENT ON COLUMN wechat_mp_webhook_log.resource_type IS '资源类型: user/message/material/tag/menu';
COMMENT ON COLUMN wechat_mp_webhook_log.resource_id IS '资源 ID';
COMMENT ON COLUMN wechat_mp_webhook_log.payload IS 'Webhook 负载';
COMMENT ON COLUMN wechat_mp_webhook_log.headers IS '请求头';
COMMENT ON COLUMN wechat_mp_webhook_log.processed_status IS '处理状态: pending/success/failed';
COMMENT ON COLUMN wechat_mp_webhook_log.error_message IS '错误信息';
COMMENT ON COLUMN wechat_mp_webhook_log.received_time IS '接收时间';
COMMENT ON COLUMN wechat_mp_webhook_log.processed_time IS '处理时间';
COMMENT ON COLUMN wechat_mp_webhook_log.retry_count IS '重试次数';
COMMENT ON COLUMN wechat_mp_webhook_log.create_time IS '创建时间';

-- ============================================
-- 9. 微信服务号同步日志表
-- ============================================
CREATE TABLE wechat_mp_sync_log (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    sync_type VARCHAR(50) NOT NULL,
    sync_method VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    sync_status VARCHAR(50) DEFAULT 'running',
    total_count INT DEFAULT 0,
    success_count INT DEFAULT 0,
    failure_count INT DEFAULT 0,
    error_message TEXT,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    duration BIGINT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wechat_mp_sync_account FOREIGN KEY (account_id) REFERENCES wechat_mp_account(id) ON DELETE CASCADE
);

CREATE INDEX idx_wechat_mp_sync_account_id ON wechat_mp_sync_log(account_id);
CREATE INDEX idx_wechat_mp_sync_type ON wechat_mp_sync_log(sync_type);
CREATE INDEX idx_wechat_mp_sync_status ON wechat_mp_sync_log(sync_status);
CREATE INDEX idx_wechat_mp_sync_start_time ON wechat_mp_sync_log(start_time);

-- 表注释
COMMENT ON TABLE wechat_mp_sync_log IS '微信服务号同步日志表';
COMMENT ON COLUMN wechat_mp_sync_log.account_id IS '关联的账户 ID';
COMMENT ON COLUMN wechat_mp_sync_log.sync_type IS '同步类型: user/material/message/tag/menu/full';
COMMENT ON COLUMN wechat_mp_sync_log.sync_method IS '同步方式: scheduled/manual/webhook';
COMMENT ON COLUMN wechat_mp_sync_log.start_date IS '开始日期';
COMMENT ON COLUMN wechat_mp_sync_log.end_date IS '结束日期';
COMMENT ON COLUMN wechat_mp_sync_log.sync_status IS '同步状态: running/success/failed';
COMMENT ON COLUMN wechat_mp_sync_log.total_count IS '总记录数';
COMMENT ON COLUMN wechat_mp_sync_log.success_count IS '成功数量';
COMMENT ON COLUMN wechat_mp_sync_log.failure_count IS '失败数量';
COMMENT ON COLUMN wechat_mp_sync_log.error_message IS '错误信息';
COMMENT ON COLUMN wechat_mp_sync_log.start_time IS '开始时间';
COMMENT ON COLUMN wechat_mp_sync_log.end_time IS '结束时间';
COMMENT ON COLUMN wechat_mp_sync_log.duration IS '耗时（毫秒）';
COMMENT ON COLUMN wechat_mp_sync_log.create_time IS '创建时间';

-- ============================================
-- 初始化数据
-- ============================================

-- 创建示例账户配置（开发环境）
-- INSERT INTO wechat_mp_account (account_id, account_name, app_id, app_secret, enabled, create_by)
-- VALUES ('wechat_mp_123456789', '测试公众号', 'your-app-id-here', 'your-app-secret-here', TRUE, 'system');
