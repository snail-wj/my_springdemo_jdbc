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
		* 先引入IOC基本的6个jar包
		* 再引入Spring-aop的jar包
		* 最后引入JDBC模板需要的jar包
			* MySQL数据库的驱动包
			* Spring-jdbc.jar
			* Spring-tx.jar
	
	3. 编写测试代码（自己来new对象的方式）
		@Test
		public void run1(){
			// 创建连接池，先使用Spring框架内置的连接池
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql:///spring_day03");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			// 创建模板类
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// 完成数据的添加
			jdbcTemplate.update("insert into t_account values (null,?,?)", "测试",10000);
		}
		
**技术分析之使用Spring框架来管理模板类**

    1. 刚才编写的代码使用的是new的方式，应该把这些类交给Spring框架来管理。
    
	2. 修改的步骤如下
		* 步骤一：Spring管理内置的连接池
			<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
		    	<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
		    	<property name="url" value="jdbc:mysql:///spring_day03"/>
		    	<property name="username" value="root"/>
		    	<property name="password" value="root"/>
		    </bean>
		
		* 步骤二：Spring管理模板类
			<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		    	<property name="dataSource" ref="dataSource"/>
		    </bean>
		
		* 步骤三：编写测试程序
			@RunWith(SpringJUnit4ClassRunner.class)
			@ContextConfiguration("classpath:applicationContext.xml")
			public class Demo2 {
				
				@Resource(name="jdbcTemplate")
				private JdbcTemplate jdbcTemplate;
				
				@Test
				public void run2(){
					jdbcTemplate.update("insert into t_account values (null,?,?)", "测试2",10000);
				}
			}
			
**技术分析之Spring框架管理开源的连接池**

    1. 管理DBCP连接池
		* 先引入DBCP的2个jar包
			* com.springsource.org.apache.commons.dbcp-1.2.2.osgi.jar
			* com.springsource.org.apache.commons.pool-1.5.3.jar
		
		* 编写配置文件
			<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		    	<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
		    	<property name="url" value="jdbc:mysql:///spring_day03"/>
		    	<property name="username" value="root"/>
		    	<property name="password" value="root"/>
		    </bean>
	
	2. 管理C3P0连接池
		* 先引入C3P0的jar包
			* com.springsource.com.mchange.v2.c3p0-0.9.1.2.jar
		
		* 编写配置文件
			<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		    	<property name="driverClass" value="com.mysql.jdbc.Driver"/>
		    	<property name="jdbcUrl" value="jdbc:mysql:///spring_day03"/>
		    	<property name="user" value="root"/>
		    	<property name="password" value="root"/>
		    </bean>