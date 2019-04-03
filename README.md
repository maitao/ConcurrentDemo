# ConcurrentDemo
使用springboot、nginx、sqlserver(mysql)做的一个高并发Demo

##20190403
springboot-module 模拟多用户发送http请求

springboot-mybatis-sqlserver 作为多个部署应用
192.168.0.103:7007
192.168.0.103:8008

另外需要下载使用nginx做反向代理。

测试:
http://localhost/inserValue?userId=

nginx配置
upstream maitao{
		server 192.168.0.103:7007 weight=1;
		server 192.168.0.103:8008 weight=2;
	}
  
location / {
    proxy_pass http://maitao; 
    proxy_redirect default; 
  }
  
测试结果：（10000,1000）
SELECT count(*) FROM [dbo].[user] where host = 'localhost:7007' --1160 2654 543 236
SELECT count(*) FROM [dbo].[user] where host = 'localhost:8008' --2318 5309 235 470

模拟请求类如果没有设置允许超时，会有部分出现超时而请求失败。

表结构
CREATE TABLE [dbo].[user] (
[name] varchar(255) COLLATE Chinese_PRC_CI_AS NULL ,
[age] int NULL ,
[user_id] varchar(50) COLLATE Chinese_PRC_CI_AS NULL ,
[host] varchar(100) COLLATE Chinese_PRC_CI_AS NULL 
)
ON [PRIMARY]
GO