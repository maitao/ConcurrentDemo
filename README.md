# ConcurrentDemo
使用springboot、nginx、sqlserver(mysql)做的一个高并发Demo

环境准备：</br>
安装nginx、mysql数据库、sqlserver数据库</br>

nginx反向代理配置</br>
upstream maitao{</br>
		server 192.168.0.103:7007 weight=1;</br>
		server 192.168.0.103:8008 weight=2;</br>
	}</br>
  
location / {</br>
    proxy_pass http://maitao; </br>
    proxy_redirect default; </br>
  }</br>
</br>
</br>
</br>
##20190403</br>
springboot-mybatis-sqlserver 作为多个部署应用，模拟负载均衡</br>
---
192.168.0.103:7007</br>
192.168.0.103:8008</br>
##表结构</br>
CREATE TABLE [dbo].[user] (</br>
[name] varchar(255) COLLATE Chinese_PRC_CI_AS NULL ,</br>
[age] int NULL ,</br>
[user_id] varchar(50) COLLATE Chinese_PRC_CI_AS NULL ,</br>
[host] varchar(100) COLLATE Chinese_PRC_CI_AS NULL </br>
)</br>
ON [PRIMARY]</br>
GO</br>
</br></br></br>
springboot-module 模拟多用户发送http请求</br>
---
测试:</br>
http://localhost/inserValue?userId=</br>

测试结果：（10000,1000）</br>
SELECT count(*) FROM [dbo].[user] where host = 'localhost:7007' --1160 2654 543 236</br>
SELECT count(*) FROM [dbo].[user] where host = 'localhost:8008' --2318 5309 235 470</br>

模拟请求类如果没有设置允许超时，会有部分出现超时而请求失败。</br>
</br></br></br>


springboot-mybatis-mysql redis缓存操作</br>
---
使用cache+redis缓存、对List<E>redis存储
</br></br></br>
springboot-session redis共享session</br>
---
部署端口</br>
http://localhost:8088/gets/</br>
http://localhost:8089/sets/</br>
####结果：</br>
{"sessionId":"39f2a08d-e023-4b35-a063-bef8dc0a3383","user":null}</br>
{"sessionId":"39f2a08d-e023-4b35-a063-bef8dc0a3383","user":{"name":"超级管理员","account":"admin"}}</br>


