package example.redisdelaytaskdemo;

import lombok.Data;

/**
 * @author hui
 * @description 队列中存放的数据实体
 * @date 2020/11/13
 */
@Data
public class User {
    private String name;
    private int age;
    private String wife;
    private Double salary;
    private String putTime;
}
