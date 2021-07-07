package top.onepecent.oneu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.onepecent.oneu.mapper")
public class OneuApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneuApplication.class, args);
    }

}
