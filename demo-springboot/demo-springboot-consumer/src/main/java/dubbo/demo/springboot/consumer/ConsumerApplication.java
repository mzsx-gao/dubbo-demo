package dubbo.demo.springboot.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "dubbo.demo.springboot.consumer")
@PropertySource("classpath:dubbo-consumer.properties")
public class ConsumerApplication {

    public static void main(String[] args){
        SpringApplication.run(ConsumerApplication.class,args);
        System.out.println("consumer启动成功");
    }

    @Configuration
    static class ConsumerConfiguration {

    }
}
