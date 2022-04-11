1. 创建数据库：demo,将目录中的demo.sql导入至数据库

2. 后端：启动application

3. 前端：进入到wms前端项目，依次输入以下命令
```javascript?linenums
cd wms
npm install
npm run serve
npm run build
```

+ SYSTEM(所有操作权限) 账号：001 密码：001 
+ USER(无删除用户权限) 账号：002 密码：002

<font color=red size=5>注：系统只对用户管理页面按钮进行权限管控，其他页面按钮默认所有人可访问</font>
