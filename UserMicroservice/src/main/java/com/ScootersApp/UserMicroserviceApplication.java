package com.ScootersApp;
import com.ScootersApp.Service.loadData.CSVReader;
import io.swagger.models.Contact;
import io.swagger.models.Model;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.xmlpull.v1.XmlPullParserException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class UserMicroserviceApplication {
    @Autowired
    private CSVReader loadDb;
    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }
    @PostConstruct
    public void init() throws SQLException, IOException {
        this.loadDb.load();
    }

}