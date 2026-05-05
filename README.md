# WeChat MP Connector

微信服务号连接器，用于同步用户、素材和消息数据。

## 版本信息

- **版本**: v2.0
- **描述**: 同步微信服务号用户、素材和消息数据，支持多账号管理和实时 Webhook 事件订阅
- **分类**: Messaging
- **端口**: 23014

## 功能特性

- 多账号管理
- 用户数据同步
- 素材数据同步
- 消息数据同步
- 标签管理
- 菜单管理
- 图文消息管理
- 定时同步任务
- Webhook 事件订阅
- RESTful API 接口
- Swagger 文档支持

## 数据模型

### 核心表

- `wechat_mp_account` - 账户配置表
- `wechat_mp_user` - 用户表
- `wechat_mp_material` - 素材表
- `wechat_mp_message` - 消息表
- `wechat_mp_tag` - 标签表
- `wechat_mp_menu` - 菜单表
- `wechat_mp_news` - 图文消息表
- `wechat_mp_webhook_log` - Webhook 日志表
- `wechat_mp_sync_log` - 同步日志表

## 快速开始

### 1. 数据库初始化

```bash
psql -U wechat_user -d livepulse_wechat_mp -f docs/sql/wechat_mp_schema.sql
```

### 2. 配置 Nacos

在 Nacos 中创建以下配置：

- `postgresql-config.yml` - 数据库配置
- `kafka-config.yml` - Kafka 配置

### 3. 启动应用

```bash
java -jar livepulse-connector-wechat-mp-open.jar \
  --spring.profiles.active=prod \
  --NACOS_SERVER_ADDR=nacos-host:8848
```

### 4. 访问 API 文档

```
http://localhost:23014/swagger-ui.html
```

## API 端点

### 账户管理

- `POST /wechat-mp/account` - 添加账户配置
- `PUT /wechat-mp/account/{id}` - 更新账户配置
- `DELETE /wechat-mp/account/{id}` - 删除账户配置
- `GET /wechat-mp/account/{id}` - 获取账户配置
- `GET /wechat-mp/account` - 分页查询账户列表
- `POST /wechat-mp/account/{id}/enable` - 启用账户
- `POST /wechat-mp/account/{id}/disable` - 禁用账户
- `POST /wechat-mp/account/{id}/enable-webhook` - 启用 Webhook
- `POST /wechat-mp/account/{id}/disable-webhook` - 禁用 Webhook

### 用户管理

- `GET /wechat-mp/user/{id}` - 获取用户详情
- `GET /wechat-mp/user` - 分页查询用户列表
- `POST /wechat-mp/user/{id}/process` - 标记为已处理

### 素材管理

- `GET /wechat-mp/material/{id}` - 获取素材详情
- `GET /wechat-mp/material` - 分页查询素材列表
- `POST /wechat-mp/material/{id}/process` - 标记为已处理

### 消息管理

- `GET /wechat-mp/message/{id}` - 获取消息详情
- `GET /wechat-mp/message` - 分页查询消息列表
- `POST /wechat-mp/message/{id}/process` - 标记为已处理

### 标签管理

- `GET /wechat-mp/tag/{id}` - 获取标签详情
- `GET /wechat-mp/tag` - 分页查询标签列表

### 菜单管理

- `GET /wechat-mp/menu/{id}` - 获取菜单详情
- `GET /wechat-mp/menu` - 分页查询菜单列表

### 图文消息管理

- `GET /wechat-mp/news/{id}` - 获取图文消息详情
- `GET /wechat-mp/news` - 分页查询图文消息列表
- `POST /wechat-mp/news/{id}/process` - 标记为已处理

### 日志查询

- `GET /wechat-mp/webhook-log/{id}` - 获取 Webhook 日志详情
- `GET /wechat-mp/webhook-log` - 分页查询 Webhook 日志列表
- `GET /wechat-mp/sync-log/{id}` - 获取同步日志详情
- `GET /wechat-mp/sync-log` - 分页查询同步日志列表

## 定时任务

### 用户同步

- **Cron**: `0 0 */4 * * ?`
- **说明**: 每4小时同步最近30天数据

### 素材同步

- **Cron**: `0 0 2 * * ?`
- **说明**: 每天凌晨2点同步最近30天数据

### 消息同步

- **Cron**: `0 0 */6 * * ?`
- **说明**: 每6小时同步最近30天数据

## Docker 部署

```bash
# 构建镜像
docker build -t livepulse-connector-wechat-mp:2.0 .

# 启动容器
docker-compose up -d

# 查看日志
docker-compose logs -f
```

## 配置说明

### bootstrap.yml

```yaml
server:
  port: 23014

spring:
  application:
    name: wechat-mp-open-connector-server

wechat:
  mp:
    api:
      version: 2.0
      timeout: 30
    sync:
      batch-size: 100
      max-retries: 3
```

## 环境要求

- Java 17
- Maven 3.8+
- PostgreSQL 15+
- Nacos 2.5.1+

## 文档

详细部署文档请参考：[部署指南.md](部署指南.md)

## 许可证

Copyright © 2025 Livepulse
