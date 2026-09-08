# tlias 智能学习辅助系统

JavaWeb 阶段学习项目。基于 SpringBoot + MyBatis 实现的培训机构后台管理系统，涵盖部门、员工、班级、学员的管理与数据统计。

> 学习项目，用于巩固 JavaWeb 阶段知识（SpringBoot、MyBatis、RESTful 接口、分页、文件上传、登录鉴权、全局异常处理）。

## 技术栈

| 类别 | 技术 | 版本 |
|---|---|---|
| JDK | Java | 17 |
| 框架 | SpringBoot | 4.0.7 |
| 持久层 | MyBatis | 4.0.1 |
| 分页 | PageHelper | 1.4.7 |
| 数据库 | MySQL | 8.x |
| 文件存储 | 阿里云 OSS SDK | v2 (0.6.0) |
| 鉴权 | JWT (jjwt) | 0.12.5 |
| 工具 | Lombok | — |
| 构建 | Maven | — |

## 已实现功能

### 登录认证
- 登录接口校验用户名密码，成功后签发 JWT 返回给前端
- `TokenFilter` 基于 Servlet Filter 拦截 `/*`，除 `/login` 外的请求统一校验令牌
- 令牌为空或解析失败直接返回 401，不进入后续业务

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
| 认证 | POST | `/login` | 登录，成功后返回 JWT |
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
├── filter/              # 过滤器（TokenFilter 统一校验令牌）
├── utils/               # 工具类（JwtUtils 签发/解析令牌、阿里云 OSS 上传）
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

- [x] 登录认证（JWT + Filter）
- [ ] 权限控制：按角色区分接口访问（目前只校验令牌有效性，未校验权限）
- [ ] 密钥外置到配置文件（现为 `JwtUtils` 中的硬编码字符串）
- [ ] 对比 Filter / 拦截器 / Spring Security 三种实现方式的差异
- [ ] 令牌过期后的无感刷新
- [ ] 员工关联的详细信息查询与联表优化
- [ ] 接口文档（Swagger / Knife4j）
- [ ] 单元测试补充
- [ ] 日志切面（AOP 记录操作日志）

## 学习记录

| 日期 | 完成内容 | 遇到的问题 / 解决方式 |
|---|---|---|
| 2026-09-08 | 完成部门、员工、班级、学员模块与数据统计 | — |
| 2026-09-08 | 新增登录认证：`LoginController` 签发 JWT、`TokenFilter` 统一校验令牌 | jjwt 0.9.1 在 JDK 17 下报 `NoClassDefFoundError` → 升级到 0.12.5 并改写工具类 |
| 2026-09-08 | 修复 `TokenFilter` 鉴权漏洞、脱敏阿里云密钥、定位 Debug 假报错 | 见下方难点复盘 |

## 难点复盘

<!-- 用「问题 → 方案 → 效果」三段式记录，面试时讲的就是这些 -->

### jjwt 0.9.1 在 JDK 17 下解析令牌直接崩

**问题**：生成 token 正常，一调用 `parseJWT` 就报 `NoClassDefFoundError: javax.xml.bind.DatatypeConverter`。

**方案**：jjwt 0.9.1 内部用 `javax.xml.bind.DatatypeConverter` 做 Base64，这个类是 Java EE 的一部分，**JDK 11 已从 JDK 中移除**。先试着补 `jaxb-api` 依赖救场，结果 `jaxb-core:2.3.1` 在阿里云仓库压根不存在（最高只到 2.3.0.1），这条路走死。最终直接升级 jjwt 到 **0.12.5**，新版改用 `java.util.Base64`，不再依赖任何 JAXB。

**效果**：顺带适配了 0.12 的新 API（`Jwts.builder().claims().signWith()` / `Jwts.parser().verifyWith().parseSignedClaims()`），并把密钥从 7 字节换成 40 字节——新版本拒绝弱密钥，HS256 要求密钥 ≥ 32 字节。

### 过滤器设置 401 后没 return，鉴权形同虚设

**问题**：`TokenFilter` 里 catch 到令牌解析异常后只调用了 `setStatus(401)`，没有 `return`，代码继续往下执行 `chain.doFilter`，请求照样打进 Controller。

**方案**：令牌为空的分支和 catch 块都补上 `return`。

**效果**：无效令牌才真正被拦截。这个 bug 很隐蔽——不报错、不崩溃，只是"鉴权根本没生效"，测接口时很容易忽略。

### Debug 卡在 Jackson2AutoConfiguration，Run 却完全正常

**问题**：以 Debug 方式启动必停在 `Jackson2AutoConfiguration` 报 `FileNotFoundException`，Reload Maven、Rebuild Project、Invalidate Caches 三种办法全部无效，且整个本地仓库里搜不到这个类。

**方案**：翻 `.idea/workspace.xml` 才发现挂着一个启用的 `java.lang.IllegalStateException` **异常断点**。Spring Boot 启动时会尝试加载大量自动配置候选类，找不到就抛异常、然后被框架内部 catch 掉——这是正常流程。但异常断点不管你有没有 catch，命中就暂停，IDEA 就把栈帧定位到那个"找不到的类名"上，看起来像崩了。

**效果**：删掉断点后立即正常。**经验：Debug 报错、Run 正常，第一嫌疑永远是异常断点（`Ctrl+Shift+F8`），别去查依赖。**

### 差点把阿里云密钥推上公开仓库

**问题**：`application.yml` 里明文写着 OSS 的 `access-key-secret`，已经进了 git 暂存区。

**方案**：`git rm --cached` 移出版本控制 → 写进 `.gitignore` → `git commit --amend` 重写历史；另存一份脱敏的 `application.yml.example` 供参考。

**效果**：本地仓库与远端均无密钥。注意：如果密钥真的被推上去过，**必须在阿里云控制台吊销并重新生成**，光删代码没用——GitHub 上被爬走的密钥几分钟内就会被拿去挖矿。

---

> 配置文件 `application.yml` 已加入 `.gitignore`，仓库中只保留脱敏的 `application.yml.example`，避免密钥泄露。
