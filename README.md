# My Gateway

My Gateway 是一个基于Java开发的现代化API网关框架，支持插件化扩展和异步非阻塞处理。

## 项目结构

```
my-gateway/
├── gateway-common          # 公共模块，包含通用工具类、常量和异常定义
├── gateway-plugin-api      # 插件API模块，定义插件接口和核心组件
├── gateway-core            # 核心模块（待实现）
```

## 模块说明

### gateway-common

公共基础模块，包含以下内容：

- 常量定义：如HTTP头信息常量
- 异常体系：统一的异常处理框架
- 工具类：JSON工具类等

### gateway-plugin-api

插件API模块，定义了网关的核心接口和组件：

- `GatewayPlugin`: 网关插件基础接口
- `SyncGatewayPlugin`: 同步插件接口
- `AsyncGatewayPlugin`: 异步插件接口
- `GatewayFilter`: 网关过滤器接口
- `GatewayContext`: 网关上下文接口
- `GatewayRequest`: 网关请求接口
- `GatewayResponse`: 网关响应接口
- `ExecutionPhase`: 插件执行阶段枚举
- `PluginResult`: 插件执行结果类

### gateway-core

网关核心模块，负责协调各组件工作（尚未实现）。

## 核心特性

### 1. 插件化架构

网关采用插件化设计，支持通过实现不同的插件接口来扩展功能。

### 2. 执行阶段

插件可以在以下不同阶段执行：

- `BEFORE_ROUTING`: 路由之前
- `AFTER_ROUTING`: 路由之后
- `BEFORE_UPSTREAM`: 上游调用之前
- `AFTER_UPSTREAM`: 上游调用之后
- `ON_ERROR`: 发生错误时

### 3. 异步非阻塞处理

支持同步和异步两种插件执行方式，充分利用现代JVM的并发处理能力。

### 4. 过滤器链

提供过滤器链机制，允许按顺序处理请求和响应。

## 快速开始

### 环境要求

- Java 25或更高版本
- Maven 3.6+

### 构建项目

```bash
mvn clean install
```

## 使用示例

### 创建自定义插件

```java
public class CustomPlugin implements SyncGatewayPlugin {
    
    @Override
    public String getId() {
        return "custom-plugin";
    }
    
    @Override
    public String getName() {
        return "Custom Plugin";
    }
    
    @Override
    public String getVersion() {
        return "1.0.0";
    }
    
    @Override
    public Set<ExecutionPhase> getSupportedPhases() {
        return Set.of(ExecutionPhase.BEFORE_ROUTING);
    }
    
    @Override
    public void init(PluginConfig config) throws GatewayException {
        // 初始化插件
    }
    
    @Override
    public PluginResult executeSync(GatewayContext context) throws Exception {
        // 插件业务逻辑
        return PluginResult.interrupt();
    }
    
    @Override
    public void destroy() {
        // 销毁插件资源
    }
}
```

## 开发计划

1. 实现gateway-core模块
2. 添加配置管理功能
3. 实现路由分发机制
4. 提供完整的HTTP客户端支持
5. 添加监控和指标收集功能
6. 完善单元测试覆盖

## 许可证

本项目采用Apache License 2.0许可证。