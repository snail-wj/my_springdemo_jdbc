package cn.wj.demo1;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试JDBC的模板类，使用IOC的方式
 * Created by WJ on 2017/10/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo2 {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void run1(){

        jdbcTemplate.update("insert into t_account values(null,?,?)","jayChen",1234.5);
    }


}
