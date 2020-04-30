package io.pivotal.pal.tracker;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PalTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }

    @Bean
    public static TimeEntryRepository timeEntryRepository() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(System.getenv("SPRING_DATASOURCE_URL"));

        return new JdbcTimeEntryRepository(dataSource);
    }
}
