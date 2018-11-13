### Spring Boot actuator服务监控与管理

actuaotr是spring boot项目中非常强大的一个功能，有助于对应用程序进行监控和管理，
通过restful api请求来监管、审计、收集应用的运行情况，针对微服务而言它是必不可少的一个环节。

#### Endpoints

　　actuator的核心部分，它用来监视应用程序及交互，spring-boot-actuator中已经内置了非常多的Endpoints（health、info、beans、httptrace、shutdown）等等，
同时也允许我们自己扩展自己的端点。

　　spring boot 2.0中的端点和之前版本有较大不同，使用时需要注意。另外端点的监控机制也有很大不同，
启用了不代表可以直接访问，还需要将其暴露出来，传统的management.security管理已被标记为不推荐。

 

#### 内置Endpoints

| id | desc | Sensitive |
| ------ | ------ | ------ |
| auditevents | 显示当前应用程序的审计事件信息 | Yes |
| beans | 显示应用Spring Beans的完整列表  | Yes  |
| caches | 显示可用缓存信息 | Yes  |
| conditions | 显示自动装配类的状态及及应用信息 | Yes  |
| configprops | 显示所有 @ConfigurationProperties 列表 | Yes  |
| env         |  	显示 ConfigurableEnvironment 中的属性   | 	Yes |      
|  flyway     |   	显示 Flyway 数据库迁移信息              |   Yes |     
|  health     |   	显示应用的健康信息（未认证只显示status，认证显示全部信息详情）| No |       
| info        |  	显示任意的应用信息（在资源文件写info.xxx即可）  |     	No |      
|  liquibase  |   	展示Liquibase 数据库迁移                |  	Yes |     
|  metrics    |   	展示当前应用的 metrics 信息             |   Yes |      
|  mappings   |   	显示所有 @RequestMapping 路径集列表     |   Yes |     
|  scheduledtasks |	显示应用程序中的计划任务                |       	Yes  |    
|  sessions    |  	允许从Spring会话支持的会话存储中检索和删除用户会话。|      	Yes  |    
|  shutdown    | 	允许应用以优雅的方式关闭（默认情况下不启用）        |     	Yes |      
|  threaddump  |  	执行一个线程dump                        | 	Yes     | 
|  httptrace   |  	显示HTTP跟踪信息（默认显示最后100个HTTP请求 - 响应交换）|	Yes |    
 

#### 导入依赖

　　在pom.xml中添加spring-boot-starter-actuator的依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
如果需要访问info接口来获取maven中的属性内容请记得添加如下内容

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>build-info</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
 

属性配置

在application.yml文件中配置actuator的相关配置，其中info开头的属性，就是访问info端点中显示的相关内容，
值得注意的十spring boot2.x中，默认只开放了info、health两个端点，
其余的需要自己通过配置management.endpoints.web.exposure.include属性来加载（有include自然就有exclude）。
如果想单独操作某个端点可以使用management.endpoint.端点.enabled属性进行启用或者禁用。

```
info:
  head: head
  body: body
management:
  endpoints:
    web:
      exposure:
        #加载所有的端点，默认只加载了info、health
        include: '*'
  endpoint:
    health:
      show-details: always
    #可以关闭指定的端点
    shutdown:
      enabled: false
```
 

#### 测试

启动项目，浏览器输入：http://localhost:8088/actuator/info

{"head":"head","body":"body"}
 

#### 自定义

　　上面很多都是配置相关的，以及自带的一些端点，在实际应用中又时候默认并不能满足我们的需求。

#### 默认装配HealthIndicators

下列是依赖spring-boot-xxx-starter后相关HealthIndicator的实现（通过management.health.defaults.enabled属性可以禁用他们），
但想要获取一些额外的信息时，自定义的作用就体现出来了。

|  |  |
| ------ | ------ |
|CassandraHealthIndicator |	检查 Cassandra 数据库是否启动。|
|DiskSpaceHealthIndicator	| 检查磁盘空间不足。 | 
|DataSourceHealthIndicator	| 检查是否可以获得连接 DataSource。 |
|ElasticsearchHealthIndicator	| 检查 Elasticsearch 集群是否启动。 |
|InfluxDbHealthIndicator	| 检查 InfluxDB 服务器是否启动。 |
|JmsHealthIndicator	    | 检查 JMS 代理是否启动。 |
|MailHealthIndicator	| 检查邮件服务器是否启动。 |
|MongoHealthIndicator	| 检查 Mongo 数据库是否启动。 |
|Neo4jHealthIndicator	| 检查 Neo4j 服务器是否启动。 |
|RabbitHealthIndicator	| 检查 Rabbit 服务器是否启动。 |
|RedisHealthIndicator	| 检查 Redis 服务器是否启动。 |
|SolrHealthIndicator	| 检查 Solr 服务器是否已启动。 |
 

#### 健康端点（第一种方式）

　　实现HealthIndicator接口，根据自己的需要判断返回的状态是UP还是DOWN，功能简单。

```
package com.spring.boot.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("my1")
public class MyHealthIndicator implements HealthIndicator {
    private  static final String VERSION = "v1.0.0";
    @Override
    public Health health() {
        int code = 0;
        if(code != 0){
            Health.down().withDetail("code",code).withDetail("version",VERSION).build();
        }
        return Health.up().withDetail("code",code).withDetail("version",VERSION).up().build();
    }
}
```
输入测试地址：http://localhost:8088/actuator/health

```
{
    "status": "DOWN",
    "details": {
        "my1": {
            "status": "UP",
            "details": {
                "code": 0,
                "version": "v1.0.0"
            }
        },
        "rabbit": {
            "status": "DOWN",
            "details": {
                "error": "org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused (Connection refused)"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 250790436864,
                "free": 67546259456,
                "threshold": 10485760
            }
        },
        "db": {
            "status": "UP",
            "details": {
                "database": "MySQL",
                "hello": 1
            }
        },
        "redis": {
            "status": "DOWN",
            "details": {
                "error": "org.springframework.data.redis.connection.PoolException: Could not get a resource from the pool; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 10.211.55.5:6379"
            }
        }
    }
}
```
可以看到当前项目的健康程度，由于没有开启linux虚拟机中的redis及rabbitMQ 所以发生异常了，平时启动项目时不去执行是不会报错的

 

#### 健康端点（第二种方式）

　　继承AbstractHealthIndicator抽象类，重写doHealthCheck方法，功能比第一种要强大一点点，
默认的DataSourceHealthIndicator、RedisHealthIndicator都是这种写法，内容回调中还做了异常的处理。

```
package com.spring.boot.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 功能更强大，AbstractHealthIndicator实现了HealthIndicator接口
 */
@Component("my2")
public class MyAbstractHealthIndicator extends AbstractHealthIndicator {
    private static final String VERSION = "v1.0.1";
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int code = 0;
        if(code != 0){
            builder.down().withDetail("code",code).withDetail("version",VERSION).build();
        }
        builder.withDetail("code",code).withDetail("version",VERSION).up().build();
    }
}
```
测试 localhost:8088/actuator/health

```
{
    "status": "DOWN",
    "details": {
        "my2": {
            "status": "UP",
            "details": {
                "code": 0,
                "version": "v1.0.1"
            }
        },
        "my1": {
            "status": "UP",
            "details": {
                "code": 0,
                "version": "v1.0.0"
            }
        },
        "rabbit": {
            "status": "DOWN",
            "details": {
                "error": "org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused (Connection refused)"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 250790436864,
                "free": 67543334912,
                "threshold": 10485760
            }
        },
        "db": {
            "status": "UP",
            "details": {
                "database": "MySQL",
                "hello": 1
            }
        },
        "redis": {
            "status": "DOWN",
            "details": {
                "error": "org.springframework.data.redis.connection.PoolException: Could not get a resource from the pool; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to 10.211.55.5:6379"
            }
        }
    }
}
```
 

#### 定义自己的端点

　　info、health都是spring-boot-actuator内置的，真正要实现自己的端点还得通过@Endpoint、@ReadOperator、@WriteOperation、@DeleteOperation。

不同请求的操作，调用时缺少必须参数，或者使用无法转换为所需类型的参数，则不会调用操作方法，响应状态为400（错误请求)

　　@Endpoint 构建rest api的唯一路径

　　@ReadOperation GET请求，响应状态为200 如果没有返回值响应404（资源未找到）

　　@WriteOperation POST请求，响应状态为200如果没有返回值响应204（无响应内容）

　　@DeleteOperation DELETE请求，响应状态为200如果没有返回值响应204（无响应内容）

```
package com.spring.boot.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "david")
public class MyEndPoint {

    @ReadOperation
    public Map<String,String> test(){
        Map<String,String> result = new HashMap<>();
        result.put("name","david");
        result.put("age","18");
        return result;
    }

}
```
然后在启动类中注入bean

```
package com.spring.boot;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.spring.boot.endpoint.MyEndPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@EnableCaching
@SpringBootApplication
public class BootApplication{

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class,args);
    }

    @Configuration
    static class MyEndpointConfiguration{
        @Bean
        @ConditionalOnMissingBean
        @ConditionalOnEnabledEndpoint
        public MyEndPoint myEndPoint(){
            return new MyEndPoint();
        }
    }
}
```
测试

启动项目 输入测试地址：http://localhost:8088/actuator/david

{"name":"david","age":"18"}