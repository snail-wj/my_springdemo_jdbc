package cn.wj.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 使用c3p0的连接池
 * Created by WJ on 2017/10/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class Demo4 {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * update(String sql,Object...params) 可以完成增删改查
     */
    @Test
    public void run1(){
        jdbcTemplate.update("delete from t_account where id = ?",  3);
    }

    /**
     * 查询测试：通过主键查询一条记录
     */
    @Test
    public void run2(){
        Account ac = jdbcTemplate.queryForObject("select * from t_account where id = ?", new BeanMapper(), 4);
        System.out.println(ac);
    }

    /**
     * 查询测试：查询所有的
     */
    @Test
    public void run3(){
        List<Account> accounts = jdbcTemplate.query("select * from t_account", new BeanMapper());
        System.out.println(accounts);
    }
}

/**
 * 自己手动封装数据(一行一行封装)
 */
class BeanMapper implements RowMapper<Account>{
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setMoney(resultSet.getDouble("money"));
        return account;
    }
}
