package ru.dankoy.otus.diploma;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.dankoy.otus.diploma.core.hibernate.utils.HibernateUtils;
import ru.dankoy.otus.diploma.core.model.Crash;
import ru.dankoy.otus.diploma.flyway.MigrationsExecutor;
import ru.dankoy.otus.diploma.flyway.MigrationsExecutorFlyway;

@Configuration
public class HibernateConfig implements WebMvcConfigurer {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    @Bean(initMethod = "executeMigrations")
    public MigrationsExecutor migrationsExecutor() {
        return new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
    }

    @Bean
    @DependsOn("migrationsExecutor")
    public SessionFactory sessionFactory() {
        return HibernateUtils
                .buildSessionFactory(HIBERNATE_CFG_FILE, Crash.class);
    }

}
