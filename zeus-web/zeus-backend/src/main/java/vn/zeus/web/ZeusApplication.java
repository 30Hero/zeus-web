package vn.zeus.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import vn.zeus.web.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ZeusApplication 
{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZeusApplication.class, args);
	}
}
