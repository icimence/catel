# catel酒店预订平台后端

项目启动方法

```bash
mvn clean
mvn package
```

数据库配置方法

在本地MySQL数据库中创建名为hotel的database，修改yml配置文件中数据库的密码，spring在启动时会进行数据注入。

找到主类，启动即可