package br.com.devmedia.cleancode.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

/**
 *
 */

@EnableSpringConfigured
@Configuration
@ComponentScan(basePackages = "br.com.devmedia.cleancode", scopedProxy = ScopedProxyMode.TARGET_CLASS)
public class ConfiguracaoTesteIntegracao {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("integration");
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("br.com.devmedia.cleancode.modelo");
        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

}
