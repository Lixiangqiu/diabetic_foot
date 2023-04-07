package cn.edu.cqu.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 搭建springboot项目的步骤:
 *     1.新建一个springboot项目
 *     2.修改pom.xml文件  添加要用到的所有的依赖
 *     3.修改核心配置文件 application.properties|.yml 文件
 *     4.创建对应的数据库以及对应的表  如果有数据,需要进行数据的添加
 *     5.执行mybatis plus中自动生成器相关的代码(根据实际需求修改) 生成对应的子包和里面的java文件
 *     6.在启动类(含有main函数的类)前添加@MapperScan(basePackages = "cn.edu.cqu.boot.mapper")
 *         目的是将自动生成的所有xxxMapper.java 接口的实例注入到spring的IOC容器中
 *     7.新建一个config包,编写配置文件
 *     8.依次从Controller层(前端数据交互)-->Service层(业务逻辑实现)-->Mapper层(数据库数据交互)编写代码
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.edu.cqu.boot.mapper")
public class Day03UserBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Day03UserBootApplication.class, args);
    }

}
