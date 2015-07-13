package br.com.devmedia.cleancode.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
class ConfiguracaoBancoDados {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Value("/massa-de-dados/clientes.sql")
    private Resource scriptCliente;

    @Value("/massa-de-dados/numero-pedidos.sql")
    private Resource scriptNumerosPedidos;

    @Value("/massa-de-dados/produtos.sql")
    private Resource scriptProdutos;

    @Value("/massa-de-dados/pedidos.sql")
    private Resource scriptPedidos;

    @Value("/massa-de-dados/itens-pedidos.sql")
    private Resource scriptItensPedidos;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(scriptCliente);
        populator.addScript(scriptNumerosPedidos);
        populator.addScript(scriptProdutos);
        populator.addScript(scriptPedidos);
        populator.addScript(scriptItensPedidos);
        return populator;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
