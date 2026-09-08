# tlias 智能学习辅助系统

JavaWeb 阶段学习项目。基于 SpringBoot + MyBatis 实现的培训机构后台管理系统，涵盖部门、员工、班级、学员的管理与数据统计。

> 学习项目，用于巩固 JavaWeb 阶段知识（SpringBoot、MyBatis、RESTful 接口、分页、文件上传、全局异常处理）。

## 技术栈

| 类别 | 技术 | 版本 |
|---|---|---|
| JDK | Java | 17 |
| 框架 | SpringBoot | 4.0.7 |
| 持久层 | MyBatis | 4.0.1 |
| 分页 | PageHelper | 1.4.7 |
| 数据库 | MySQL | 8.x |
| 文件存储 | 阿里云 OSS SDK | v2 (0.6.0) |
| 工具 | Lombok | — |
| 构建 | Maven | — |

## 已实现功能

### 部门管理
- 部门列表、新增、修改、删除、根据 ID 查询
- 删除前的关联校验

### 员工管理
- 员工分页查询（支持姓名、性别、入职时间等条件组合查询）
- 新增员工（同时保存员工基本信息和工作经历，主子表关联）
- 修改员工信息
- 批量删除员工
- 员工操作日志记录

### 班级管理
- 班级分页查询、新增、修改、删除、根据 ID 查询

### 学员管理
- 学员分页查询、新增、修改、删除、违纪处理

### 数据统计
- 员工职位分布统计
- 员工性别分布统计
- 学员学历分布统计

### 文件上传
- 基于阿里云 OSS 的图片上传
- 采用配置类 + 环境变量方式读取密钥，避免硬编码

### 基础能力
- 统一返回结果封装（`Result`）
- 全局异常处理（`GlobalExceptionHandler` + 自定义业务异常）
- 参数校验与错误提示

## 接口清单

| 模块 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 部门 | GET | `/depts` | 部门列表 |
| 部门 | GET | `/depts/{id}` | 根据 ID 查询 |
| 部门 | POST | `/depts` | 新增部门 |
| 部门 | PUT | `/depts` | 修改部门 |
| 部门 | DELETE | `/depts` | 删除部门 |
| 员工 | GET | `/emps` | 员工分页查询 |
| 员工 | GET | `/emps/{id}` | 根据 ID 查询 |
| 员工 | GET | `/emps/list` | 员工列表 |
| 员工 | POST | `/emps` | 新增员工 |
| 员工 | PUT | `/emps` | 修改员工 |
| 员工 | DELETE | `/emps` | 批量删除 |
| 班级 | — | `/clazzes` | 班级增删改查 |
| 学员 | — | `/students` | 学员增删改查 |
| 报表 | GET | `/report/empJobData` | 员工职位统计 |
| 报表 | GET | `/report/empGenderData` | 员工性别统计 |
| 报表 | GET | `/report/studentDegreeData` | 学员学历统计 |
| 上传 | POST | `/upload` | 图片上传 |

## 项目结构

```
src/main/java/com/itheima/
├── controller/          # 接口层，接收请求、参数校验、返回响应
├── service/             # 业务逻辑层
│   └── impl/
├── mapper/              # 数据访问层
├── pojo/                # 实体类、DTO、VO
├── utils/               # 工具类（阿里云 OSS 上传等）
└── globalexceptionhandler/   # 全局异常处理
```

分层遵循 Controller → Service → Mapper 三层架构，业务规则统一在 Service 层处理。

## 快速开始

1. 创建数据库并导入脚本

```sql
CREATE DATABASE tlias DEFAULT CHARACTER SET utf8mb4;
```

2. 复制配置模板并填写自己的配置

```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

修改数据库连接、Redis、阿里云 OSS 配置。

3. 启动项目

```bash
mvn spring-boot:run
```

或直接运行 `TliasWebManagementApplication` 启动类。

## 待完善

- [ ] 登录认证与权限控制（JWT + 拦截器）
- [ ] 员工关联的详细信息查询与联表优化
- [ ] 接口文档（Swagger / Knife4j）
- [ ] 单元测试补充
- [ ] 日志切面（AOP 记录操作日志）

## 学习记录

| 日期 | 完成内容 | 遇到的问题 / 解决方式 |
|---|---|---|
| 2026-09-08 | 完成部门、员工、班级、学员模块与数据统计 | — |

## 难点复盘

<!-- 用「问题 → 方案 → 效果」三段式记录，面试时讲的就是这些 -->

### 待补充

**问题**：
**方案**：
**效果**：

---

> 配置文件 `application.yml` 已加入 `.gitignore`，仓库中只保留脱敏的 `application.yml.example`，避免密钥泄露。
