# 敦行智能教学助手

## 1.项目目标

## 2.项目功能

## 3.项目技术栈

### 3.1 服务端技术栈

本项目为微服务项目，分别为网关、认证服务、教学大纲服务...

总体框架采用 JDK17 + SpringBoot3.2 + SpringCloud2023 + SpringCloudAlibaba2022

数据库相关技术 MySQL8.0 + Redis6.2 + MongoDB2.5 + ElasticSearch7.17.9

数据库持久层技术 MyBites + MyBites-Plus + ShardingSphere-JDBC

消息队列 RabbitMQ + kafka

注册中心 nacos

远程调用 Feign

日志收集 ELK Stack(Elasticsearch,Logstash,Kibana)

其他 SpringAI + Seata

技术架构图
![截屏2025-06-20 18 18 30](https://github.com/user-attachments/assets/554e7889-c09f-4820-9076-619de6e13831)


#### 3.1.1数据库总体架构设计

##### 3.1.1.1 数据库分库分表架构设计

```mermaid
graph TB
    subgraph 应用层
        A[应用服务] --> B[ShardingSphere-JDBC]
    end

    subgraph 数据层
        B --> C[分库规则: student_grade % 4]
        C --> D[物理库: DB0]
        C --> E[物理库: DB1]
        C --> F[物理库: DB2]
        C --> G[物理库: DB3]
        D --> H[分表规则: college_id % 2]
        H --> I[DB0.t_order_0]
        H --> J[DB0.t_order_1]
        E --> K[分表规则: college_id % 2]
        K --> L[DB1.t_order_0]
        K --> M[DB1.t_order_1]
        F --> N[分表规则: college_id % 2]
        N --> O[DB2.t_order_0]
        N --> P[DB2.t_order_1]
        G --> Q[分表规则: college_id % 2]
        Q --> R[DB3.t_order_0]
        Q --> S[DB3.t_order_1]
    end
```

##### 3.1.1.2 数据库读写分离集群架构设计

```mermaid
graph TB
    subgraph DB0集群
        A[DB0-Master] --> B[DB0-Slave1]
        A --> C[DB0-Slave2]
    end

    subgraph DB1集群
        D[DB1-Master] --> E[DB1-Slave1]
        D --> F[DB1-Slave2]
    end

    subgraph 路由层
        G[ShardingSphere] -->|写请求| A
        G -->|读请求| B
        G -->|读请求| C
        G -->|写请求| D
        G -->|读请求| E
        G -->|读请求| F
    end

    subgraph 图例
        H[主库]:::master
        I[从库]:::slave
    end
```

##### 3.1.1.3 数据库缓存设计

缓存架构：

```mermaid
graph TB
    subgraph 应用层
        A[应用服务]
    end

    subgraph Redis集群["Redis哨兵集群（1主2从3哨兵）"]
        Master[Redis-Master] -->|主从复制| Slave1[Redis-Slave1]
        Master -->|主从复制| Slave2[Redis-Slave2]
        Sentinel1[Sentinel] -.-> Master
        Sentinel1 -.-> Slave1
        Sentinel1 -.-> Slave2
        Sentinel2[Sentinel] -.-> Master
        Sentinel2 -.-> Slave1
        Sentinel2 -.-> Slave2
        Sentinel3[Sentinel] -.-> Master
        Sentinel3 -.-> Slave1
        Sentinel3 -.-> Slave2
    end

    subgraph 读写控制
        A -->|写请求| Master
        A -->|读请求| Proxy[读写分离代理]
        Proxy --> Slave1
        Proxy --> Slave2
    end

    subgraph 故障转移流程
        style 故障转移流程 stroke-dasharray: 5
        Sentinel1 -->|选举| NewMaster[新Master]
        Sentinel2 -->|选举| NewMaster
        Sentinel3 -->|选举| NewMaster
    end
```

读写流程说明：

```mermaid
sequenceDiagram
    participant 应用服务
    participant 客户端库
    participant RedisMaster
    participant RedisSlave1
    participant RedisSlave2
    participant Sentinel集群
    应用服务 ->> 客户端库: 发起写请求(SET key value)
    客户端库 ->> RedisMaster: 执行写操作
    RedisMaster -->> 客户端库: 返回成功
    客户端库 -->> 应用服务: 返回成功
    RedisMaster ->> RedisSlave1: 异步复制数据
    RedisMaster ->> RedisSlave2: 异步复制数据
    Note right of RedisMaster: 主从延迟通常在毫秒级
    应用服务 ->> 客户端库: 发起读请求(GET key)
    客户端库 ->> RedisSlave1: 尝试读取
    alt 从节点正常
        RedisSlave1 -->> 客户端库: 返回数据
    else 从节点故障
        客户端库 ->> RedisSlave2: 自动重试
        RedisSlave2 -->> 客户端库: 返回数据
    end
    客户端库 -->> 应用服务: 返回数据
    loop 哨兵监控
        Sentinel集群 ->> RedisMaster: 每秒PING
    end
    alt 主节点故障
        RedisMaster x --x Sentinel集群: 超时未响应
        Sentinel集群 -->> Sentinel集群: 选举哨兵Leader
        Sentinel集群 ->> RedisSlave1: 提升为新Master
        RedisSlave1 -->> Sentinel集群: 确认角色切换
        Sentinel集群 ->> RedisSlave2: 指向新Master同步
        客户端库 ->> Sentinel集群: 获取新拓扑
        Sentinel集群 -->> 客户端库: 返回新Master地址
    end
```

故障转移说明：

```mermaid
sequenceDiagram
    participant 客户端
    participant Sentinel1
    participant Sentinel2
    participant Sentinel3
    participant RedisMaster
    participant RedisSlave
%% 故障检测阶段
    loop 健康检查
        Sentinel1 ->> RedisMaster: 每1秒PING
        Sentinel2 ->> RedisMaster: 每1秒PING
        Sentinel3 ->> RedisMaster: 每1秒PING
    end

    alt 主节点故障
        RedisMaster -x Sentinel1: 连续5秒无响应(SDOWN)
        Sentinel1 ->> Sentinel2: 确认故障(ODOWN)
        Sentinel1 ->> Sentinel3: 确认故障(ODOWN)
    %% 选举阶段
        activate Sentinel1
        activate Sentinel2
        activate Sentinel3
        Sentinel1 ->> Sentinel1: 发起选举(Raft协议)
        Sentinel2 -->> Sentinel1: 投票响应
        Sentinel3 -->> Sentinel1: 投票响应
        deactivate Sentinel2
        deactivate Sentinel3
        Note right of Sentinel1: 获得多数票成为Leader
    %% 故障转移阶段
        Sentinel1 ->> RedisSlave: SLAVEOF no one
        RedisSlave -->> Sentinel1: 确认角色切换
        Sentinel1 ->> RedisSlave: CONFIG REWRITE
        RedisSlave -->> Sentinel1: 确认配置更新
    %% 通知阶段
        Sentinel1 ->> Sentinel2: 更新拓扑
        Sentinel1 ->> Sentinel3: 更新拓扑
        Sentinel1 ->> 客户端: +switch-master事件
        deactivate Sentinel1
    %% 客户端响应
        客户端 ->> Sentinel集群: 获取新拓扑
        Sentinel集群 -->> 客户端: 返回新Master地址
        客户端 ->> RedisSlave: 后续请求(原Slave已变Master)
    end
```

##### 3.1.1.4 分布式事务方案设计
```mermaid
graph TB
    subgraph 事务协调
        A[Seata] --> B[TC]
        B --> C[TM]
        B --> D[RM]
    end
    subgraph 执行流程
        E[Begin] --> F[分支注册]
        F --> G[状态上报]
        G --> H[全局提交/回滚]
    end
```

##### 3.1.1.5 数据库监控指标设计
```mermaid
pie
    title 监控指标分类
    "分片均衡" : 35
    "查询性能" : 25
    "事务成功率" : 20
    "主从延迟" : 15
    "连接池状态" : 5
```

##### 3.1.1.6 数据库表设计


#### 3.1.2日志收集流程

```mermaid
graph LR
    A[微服务] -->|Filebeat采集| B(Logstash)
    B -->|结构化处理| C[Elasticsearch]
    C --> D[Kibana可视化]
```
