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
		    
### 技术分析之Spring框架的事务管理 ###

**技术分析之事务的回顾**
1. 事务：指的是逻辑上一组操作，组成这个事务的各个执行单元，要么一起成功,要么一起失败！

2. 事务的特性
	* 原子性
	* 一致性
	* 隔离性
	* 持久性
	
3. 如果不考虑隔离性,引发安全性问题
    * 读问题:
        * 脏读:
        * 不可重复读:
        * 虚读:
		
    * 写问题:
        * 丢失更新:

4. 如何解决安全性问题
    * 读问题解决，设置数据库隔离级别
    * 写问题解决可以使用 悲观锁和乐观锁的方式解决
    
**技术分析之Spring框架的事务管理相关的类和API**
	1. PlatformTransactionManager接口		-- 平台事务管理器.(真正管理事务的类)。该接口有具体的实现类，根据不同的持久层框架，需要选择不同的实现类！
	2. TransactionDefinition接口			-- 事务定义信息.(事务的隔离级别,传播行为,超时,只读)
	3. TransactionStatus接口				-- 事务的状态
	
	4. 总结：上述对象之间的关系：平台事务管理器真正管理事务对象.根据事务定义的信息TransactionDefinition 进行事务管理，在管理事务中产生一些状态.将状态记录到TransactionStatus中
	
	5. PlatformTransactionManager接口中实现类和常用的方法
    		1. 接口的实现类
    			* 如果使用的Spring的JDBC模板或者MyBatis框架，需要选择DataSourceTransactionManager实现类
    			* 如果使用的是Hibernate的框架，需要选择HibernateTransactionManager实现类
    		
    		2. 该接口的常用方法
    			* void commit(TransactionStatus status) 
    			* TransactionStatus getTransaction(TransactionDefinition definition) 
    			* void rollback(TransactionStatus status)
    
    6. TransactionDefinition
        1. 事务隔离级别的常量
            * static int ISOLATION_DEFAULT 					-- 采用数据库的默认隔离级别
            * static int ISOLATION_READ_UNCOMMITTED 
            * static int ISOLATION_READ_COMMITTED 
            * static int ISOLATION_REPEATABLE_READ 
            * static int ISOLATION_SERIALIZABLE 
         
        2. 事务的传播行为常量（不用设置，使用默认值）
                * 先解释什么是事务的传播行为：解决的是业务层之间的方法调用！！
                
                * PROPAGATION_REQUIRED（默认值）	-- A中有事务,使用A中的事务.如果没有，B就会开启一个新的事务,将A包含进来.(保证A,B在同一个事务中)，默认值！！
                * PROPAGATION_SUPPORTS			-- A中有事务,使用A中的事务.如果A中没有事务.那么B也不使用事务.
                * PROPAGATION_MANDATORY			-- A中有事务,使用A中的事务.如果A没有事务.抛出异常.
                
                * PROPAGATION_REQUIRES_NEW（记）-- A中有事务,将A中的事务挂起.B创建一个新的事务.(保证A,B没有在一个事务中)
                * PROPAGATION_NOT_SUPPORTED		-- A中有事务,将A中的事务挂起.
                * PROPAGATION_NEVER 			-- A中有事务,抛出异常.
                
                * PROPAGATION_NESTED（记）		-- 嵌套事务.当A执行之后,就会在这个位置设置一个保存点.如果B没有问题.执行通过.如果B出现异常,运行客户根据需求回滚(选择回滚到保存点或者是最初始状态)
