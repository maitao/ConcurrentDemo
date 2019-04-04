# ConcurrentDemo
使用springboot、nginx、sqlserver(mysql)做的一个高并发Demo

##20190403</br>
springboot-module 模拟多用户发送http请求</br>

springboot-mybatis-sqlserver 作为多个部署应用</br>
192.168.0.103:7007</br>
192.168.0.103:8008</br>

另外需要下载使用nginx做反向代理。</br>

测试:</br>
http://localhost/inserValue?userId=</br>

nginx配置</br>
upstream maitao{</br>
		server 192.168.0.103:7007 weight=1;</br>
		server 192.168.0.103:8008 weight=2;</br>
	}</br>
  
location / {</br>
    proxy_pass http://maitao; </br>
    proxy_redirect default; </br>
  }</br>
  
测试结果：（10000,1000）</br>
SELECT count(*) FROM [dbo].[user] where host = 'localhost:7007' --1160 2654 543 236</br>
SELECT count(*) FROM [dbo].[user] where host = 'localhost:8008' --2318 5309 235 470</br>

模拟请求类如果没有设置允许超时，会有部分出现超时而请求失败。</br>
</br>
---
##表结构</br>
CREATE TABLE [dbo].[user] (</br>
[name] varchar(255) COLLATE Chinese_PRC_CI_AS NULL ,</br>
[age] int NULL ,</br>
[user_id] varchar(50) COLLATE Chinese_PRC_CI_AS NULL ,</br>
[host] varchar(100) COLLATE Chinese_PRC_CI_AS NULL </br>
)</br>
ON [PRIMARY]</br>
GO</br>
