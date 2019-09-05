# Spring项目-社区论坛
##
Spring、Spring Boot、MyBatis、Thymeleaf、MySQL/H2、Bootstrap、[UCloud服务器](https://www.ucloud.cn/)
**编辑使用markdown，使用[pandao/editor.md](https://github.com/pandao/editor.md/)**
### [演示地址](http://106.75.62.142/)

### [Spring文档](https://spring.io/guides)

### GitHub API
[GitHub OAuth API](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

### MyBatis Generator
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
### [Markdown编辑](https://github.com/pandao/editor.md/)


#部署

### 依赖
- Git
- JDK
- Maven
-  MySQL

### 步骤
#### 更新
```bash
yum update
```
###$安装Git
```bash
yum install git
```
#### 下载代码
```bash
mkdir SpringApp
cd SpringApp/
git clone https://github.com/MeowsQAQ/SpringDemo.git
cd SpringDemo
```
#### 安装JAVA和maven
```bash
yum install maven #会自动安装JAVA
mvn -v 查看版本号
```
#### 打包
```bash
mvn clean compile package
```
#### 查看修改配置
```bash
 more src/main/resources/application.properties
 cp src/main/resources/application.properties src/main/resources/application-production.properties
 vim src/main/resources/application-production.properties
 mvn package  #将新配置导入
 
 ps -aux | grep java
```
#### 运行
```bash
java -jar -Dspring.profiles.active=production SpringApp/SpringDemo/target/demo-0.0.1-SNAPSHOT.jar

```