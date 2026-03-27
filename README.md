# 学生选课管理系统

## 项目简介
学生选课管理系统是一个基于Spring Boot的Web应用，用于管理学生、课程和选课信息。系统支持管理员和学生两种角色，实现了学生管理、课程管理、选课管理等核心功能，并提供了数据可视化统计。

## 技术栈
- **后端**：Spring Boot 3.5.0 + MyBatis Plus 3.5.7
- **前端**：Thymeleaf + Bootstrap 3.3.7 + ECharts 5.4.3
- **数据库**：MySQL 8.0
- **构建工具**：Maven

## 功能特性

### 管理员功能
- 学生管理：增删改查学生信息
- 课程管理：增删改查课程信息
- 选课管理：查看所有选课记录，对选课进行打分和评价
- 数据统计：查看课程分数分布情况

### 学生功能
- 个人信息管理：查看和修改个人信息
- 课程查看：浏览所有课程信息
- 选课功能：选择感兴趣的课程
- 退课功能：取消已选课程
- 我的选课：查看自己的选课记录和成绩

## 项目结构

```
app-springboot/
├── src/
│   ├── main/
│   │   ├── java/com/codeying/
│   │   │   ├── controller/      # 控制器
│   │   │   ├── entity/          # 实体类
│   │   │   ├── mapper/          # 数据访问层
│   │   │   ├── service/         # 服务层
│   │   │   ├── utils/           # 工具类
│   │   │   ├── component/       # 组件
│   │   │   └── App.java         # 应用入口
│   │   ├── resources/
│   │   │   ├── static/          # 静态资源
│   │   │   ├── templates/       # 页面模板
│   │   │   └── application.properties  # 配置文件
│   └── test/                    # 测试代码
├── app_springboot-Mysql.sql     # 数据库脚本
└── pom.xml                      # Maven配置文件
```

## 安装与运行

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置
1. 创建数据库：`CREATE DATABASE student_course_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
2. 执行数据库脚本：`app_springboot-Mysql.sql`
3. 修改 `application.properties` 中的数据库连接信息

### 3. 运行项目
```bash
# 编译项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

### 4. 访问系统
- 访问地址：`http://localhost:8080`
- 管理员账号：admin / 123456
- 学生账号：可通过注册功能创建

## 使用说明

### 1. 登录系统
- 打开登录页面，选择角色（管理员/学生）
- 输入账号和密码
- 点击登录按钮

### 2. 管理员操作
- **学生管理**：在左侧菜单中点击「用户管理」→「学生」
- **课程管理**：在左侧菜单中点击「课程管理」
- **选课管理**：在左侧菜单中点击「选课管理」
- **数据统计**：在首页查看课程分数分布图表

### 3. 学生操作
- **个人信息**：在左侧菜单中点击「个人中心」
- **课程查看**：在左侧菜单中点击「课程管理」
- **选课**：在左侧菜单中点击「学生选课」
- **我的选课**：在左侧菜单中点击「我的选课」

## 数据库设计

### 1. 学生表（student）
| 字段名 | 数据类型 | 描述 |
|-------|---------|------|
| id | varchar(32) | 主键 |
| username | varchar(32) | 用户名 |
| password | varchar(32) | 密码 |
| studentno | varchar(32) | 学号 |
| name | varchar(32) | 姓名 |
| gender | varchar(10) | 性别 |
| classname | varchar(32) | 班级 |
| nativeplace | varchar(32) | 籍贯 |
| tele | varchar(32) | 电话 |
| createtime | datetime | 创建时间 |

### 2. 课程表（course）
| 字段名 | 数据类型 | 描述 |
|-------|---------|------|
| id | varchar(32) | 主键 |
| courseno | varchar(32) | 课程号 |
| name | varchar(32) | 课程名称 |
| credit | double | 学分 |
| teacher | varchar(32) | 教师 |
| description | varchar(255) | 描述 |
| createtime | datetime | 创建时间 |

### 3. 选课表（course_selection）
| 字段名 | 数据类型 | 描述 |
|-------|---------|------|
| id | varchar(32) | 主键 |
| student_id | varchar(32) | 学生ID |
| course_id | varchar(32) | 课程ID |
| score | double | 分数 |
| evaluation | varchar(255) | 评价 |
| selection_time | datetime | 选课时间 |
| createtime | datetime | 创建时间 |

## 注意事项

1. 系统默认使用端口8080，如果端口被占用，请修改application.properties中的server.port配置
2. 数据库连接信息需要根据实际情况修改
3. 系统初始只创建了管理员账号，学生账号需要通过注册功能创建
4. 管理员可以对课程进行打分，学生只能查看自己的成绩
5. 学生选课有唯一性约束，一个学生不能重复选择同一门课程

## 项目维护

- **代码结构**：遵循MVC架构，清晰分层
- **命名规范**：使用驼峰命名法
- **日志记录**：使用Spring Boot默认日志框架
- **异常处理**：全局异常处理机制

## 许可证

本项目采用MIT许可证，详见LICENSE文件。