package com.rh.project.hikari.demo.controller;
 
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
 
@Controller
public class PhoneController {
 
    /**
     * JdbcTemplate 是 core 包的核心类，用于简化 JDBC 操作，还能避免一些常见的错误，如忘记关闭数据库连接
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，使用起来也不用再自己来关闭数据库连接
     */
    @Resource
    private JdbcTemplate jdbcTemplate;
 
    /**
     * 查询 phone 表所有数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping("phoneList")
    public List<Map<String, Object>> userList() {
        /**
         * 查询 phone 表所有数据
         * List 中的1个 Map 对应数据库的 1行数据
         * Map 中的 key 对应数据库的字段名，value 对应数据库的字段值
         */
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM phone");
        return mapList;
    }
 
    /**
     * 新增 phone 数据
     *
     * @return
     */
    @GetMapping("savePhone")
    public String savePhone() {
        String sql = "INSERT INTO phone(number,region) VALUES (?,?)";
        Object[] objects = new Object[2];
        objects[0] = "18673886425";
        objects[1] = "湖南";
 
        jdbcTemplate.update(sql, objects);
        return "forward:/phoneList";
    }
 
    /**
     * 修改 phone 数据
     *
     * @return
     */
    @GetMapping("updatePhone")
    public String updatePhone() {
        String sql = "UPDATE phone SET number=? WHERE pid=?";
        Object[] objects = new Object[2];
        objects[0] = "18666668888";
        objects[1] = "1";
 
        jdbcTemplate.update(sql, objects);
        return "forward:/phoneList";
    }
 
    /**
     * 删除 phone 数据
     * update 方法可以做查询以外的 增加、修改、删除操作
     *
     * @return
     */
    @GetMapping("deletePhone")
    public String deletePhone() {
        String sql = "DELETE FROM phone WHERE number=?";
        Object[] objects = new Object[1];
        objects[0] = "18673886425";
 
        jdbcTemplate.update(sql, objects);
        return "forward:/phoneList";
    }
}