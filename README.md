<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">ErQianYinZi-SC2: Travel Management Assistant v1.0.0</h1>
<h2 align="center" style="margin: 30px 0 30px;">二钱银子：行程管理助手 SC2 v1.0.0</h2>

## Platform Overview 平台简介

Er Qian Yin Zi: SC2 Travel Management Assistant is a web-based travel management platform, optimized and modified from the [ruoyi](https://doc.ruoyi.vip/ruoyi-vue/) platform.
Frontend: Vue, Element UI.
Backend: Spring Boot, Spring Security, Redis & JWT.
Authentication: Uses JWT, supporting multi-terminal authentication.
Dynamic Permission Management: Supports dynamic permission menus with flexible access control.

二钱银子：行程管理助手（sc2）是一套行程管理web平台，基于<a href="https://doc.ruoyi.vip/ruoyi-vue/">ruoyi</a>平台的代码优化修改而成。

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。

## Built-in Features 内置功能

1. User Management: Configures system users who operate the platform.

2. Department Management: Configures organizational structures (companies, departments, teams) with tree-structured data permission support.

3. Position Management: Assigns user roles and job positions.

4. Menu Management: Configures system menus, operation permissions, and button permission identifiers.

5. Role Management: Assigns menu permissions to roles and sets data scope permissions by organization.

6. Dictionary Management: Maintains frequently used fixed data within the system.

7. Parameter Management: Configures system-wide dynamic parameters.

8. Notifications & Announcements: Publishes and manages system-wide notifications.

9. Operation Logs: Records and queries normal system operations and exceptions.

10. Login Logs: Records and queries login attempts, including failed logins.

11. Online Users: Monitors active user sessions in real-time.

12. Scheduled Tasks: Supports online scheduling (add, edit, delete) with execution logs.

13. Code Generation: Generates front-end and back-end code (Java, HTML, XML, SQL) with CRUD support.

14. API Documentation: Automatically generates API documentation based on business logic.

15. System Monitoring: Monitors CPU, memory, disk usage, stack, and other system metrics.

16. Cache Monitoring: Queries system cache information and command statistics.

17. Online Form Builder: Drag-and-drop form elements to generate corresponding HTML code.

18. Database Connection Pool Monitoring: Monitors the status of database connection pools and analyzes SQL performance bottlenecks.

**

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。

2. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。

3. 岗位管理：配置系统用户所属担任职务。

4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。

5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。

6. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。

7. 参数管理：对系统动态配置常用参数。

8. 通知公告：系统通知公告信息发布维护。

9. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。

10. 登录日志：系统登录日志记录查询包含登录异常。

11. 在线用户：当前系统中活跃用户状态监控。

12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。

13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。

14. 系统接口：根据业务代码自动生成相关的api接口文档。

15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。

16. 缓存监控：对系统的缓存信息查询，命令统计等。

17. 在线构建器：拖动表单元素生成相应的HTML代码。

18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。



## How to Launch 项目启动说明

1. Pre-configured Environment Requirements:
   
   ```
   JDK >= 1.8 (Recommended: 1.8)  
   MySQL >= 5.7.0 (Recommended: 5.7)  
   Redis >= 3.0  
   Maven >= 3.0  
   Node >= 12  
   ```

2. Database Setup：Create a database named `sc2-trip` based on your actual setup. Then, import and execute the two `.sql` files located in `\back\sql`.

3. Running the Backend: Open `com.sc2.Sc2Application.java` in an IDE such as IntelliJ IDEA. If you see the message **"SC2: Backend started!"**, it indicates the backend has been successfully launched.

4. Running the Frontend: Open a terminal, navigate to the `\front` directory, and execute the following commands sequentially:
- ```
  # Install dependencies
  npm install --registry=https://registry.npmmirror.com  
  
  # Start the project in development mode
  npm run dev  
  ```
5. Verify Successful Setup: Open a browser and visit [http://localhost:80](http://localhost/).
   
   - Default account: **admin**
   
   - Default password: **admin123**  
     
     If the login page loads correctly, you can log in successfully, and menus and pages display as expected, then the environment setup is complete.

**

1. 需要提前配置以下环境：
   
   ```
   JDK >= 1.8 (推荐1.8版本)
   Mysql >= 5.7.0 (推荐5.7版本)
   Redis >= 3.0
   Maven >= 3.0
   Node >= 12
   ```

2. 根据实际情况，创建数据库sc2-trip，导入并运行\back\sql中的两个.sql文件。

3. 运行后端：在IDEA等开发工具中打开com.sc2.Sc2Application.java，出现“SC2：后端已启动！”字样，表明后端启动成功。

4. 运行前端：打开终端，进入\front目录下，依次运行以下语句：
- ```
  # 安装依赖
  npm install --registry=https://registry.npmmirror.com
  
  # 本地开发 启动项目
  npm run dev
  ```
5. 验证环境搭建是否成功：打开浏览器，输入([http://localhost:80 (opens new window)](http://localhost/))，默认账户admin，默认密码admin123，若能正确展示登录页面并登录成功，且菜单及页面展示正常，则表明环境搭建成功。
