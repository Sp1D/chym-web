
package net.sp1d.chym.web.config;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import net.sp1d.chym.loader.prototype.Fetcher;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * Основной файл конфигурации. Бины доступные для всех контекстов.
 * БД инициализируется при первом запуске скриптами, указанными в application.properties
 *
 * @author sp1d
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories("net.sp1d.chym.loader.repo")
@EnableTransactionManagement
@ComponentScan(basePackages = {"net.sp1d.chym.web", "net.sp1d.chym.loader.service", "net.sp1d.chym.loader.tracker"})
public class RootConfig {

    @Autowired
    Environment env;

    private static final Logger LOG = LogManager.getLogger(RootConfig.class);


   @Bean
    DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(env.getProperty("db.conn.jdbcUrl"));
        ds.setUsername(env.getProperty("db.conn.user"));
        ds.setPassword(env.getProperty("db.conn.password"));
        ds.setDriverClassName(env.getProperty("db.conn.driverClass"));
        return ds;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        va.setDatabase(Database.HSQL);
        va.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        va.setGenerateDdl(false);
        va.setShowSql(false);
        emf.setJpaVendorAdapter(va);

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("net.sp1d.chym.loader.bean", "net.sp1d.chym.loader.repo");
        emf.setPersistenceUnitName("net.sp1d.chym.web_PU0");

        return emf;
    }

    
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
            DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setPersistenceUnitName("net.sp1d.chym.web_PU0");
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
    
    @Bean
    TheMovieDbApi theMovieDbApi() throws MovieDbException{
        return null;
    }
    
    @Bean
    Fetcher fetcher(){
        return null;
    }

}
