package tech.pinto.catel.conf;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfHibernate {

//    @Bean
//    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.unwrap(SessionFactory.class);
//    }

//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        var vendorAdaptor = new HibernateJpaVendorAdapter();
//        var factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdaptor);
//        factory.setPackagesToScan("tech.pinto.catel.domain");
//        factory.setDataSource(dataSource);
//        return factory;
//    }
}
