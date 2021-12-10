package tech.pinto.catel.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

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
