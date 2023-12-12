package com.ScootersApp;
import com.ScootersApp.Service.loadData.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import java.io.IOException;
import java.sql.SQLException;
import com.ScootersApp.repository.RoleRepository;
import com.ScootersApp.domain.*;


@SpringBootApplication
@EnableDiscoveryClient
public class UserMicroserviceApplication {

    @Autowired
    private RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }
    @PostConstruct
    public void init() throws SQLException, IOException {
        Role role = new Role("admin");
        this.roleRepository.save(role);
    }

}