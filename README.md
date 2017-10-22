# my_springdemo_jdbc
有关于spring中jdbc的内容

**技术分析之演示JDBC的模板类**

    1. 步骤一：创建数据库的表结构
        create database spring_day03;
        use spring_day03;
        create table t_account(
            id int primary key auto_increment,
            name varchar(20),
            money double
        );
        
    2. 引入开发的jar包
        * 先引入IOC基本的4个jar包
            spring-beans
            spring-context
            spring-core
            spring-experssion
        * 再引入Spring-aop的jar包
            spring-aop
        * 再引入日志的包
            commons-logging
            log4j
        * 最后引入JDBC模板需要的jar包
            * MySQL数据库的驱动包
            * Spring-jdbc.jar
            * mysql-connector-java(数据库连接)
            * Spring-tx.jar(事务)
            
**技术分析之Spring框架的JDBC模板技术概述**

    1. Spring框架中提供了很多持久层的模板类来简化编程，使用模板类编写程序会变的简单
    
    2. 提供了JDBC模板，Spring框架提供的
        * JdbcTemplate类
        
    3. Spring框架可以整合Hibernate框架，也提供了模板类
        * HibernateTemplate类
            
    