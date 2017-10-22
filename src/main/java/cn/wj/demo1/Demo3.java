package cn.wj.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 使用dbcp连接池
 * Created by WJ on 2017/10/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext1.xml")
public class Demo3 {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void run1() {
        jdbcTemplate.update("insert into t_account values(null,?,?)", "孙燕姿", 1234.5);
    }
}
