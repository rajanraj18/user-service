package com.user.service.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataSourceConfig {
	
	@Value("${datasource.url}")
	private String dataSourceUrl;
	
	@Value("${datasource.username}")
	private String dataSourceUserName;
	
	@Value("${datasource.password}")
	private String dataSourcePassword;
	
	@Value("${hibernate.ddl-auto}")
	private String hibernateDDLAuto;
	
	@Value("${hibernate.show-sql}")
	private String hibernateShowSql;
	
	
	/**
	 *  Configure Data Source.
	 */
	@Bean
	public DataSource dataSource() {
	
		return DataSourceBuilder
				.create()
				.url(dataSourceUrl)
				.username(dataSourceUserName)
				.password(dataSourcePassword)
				.build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("com.user.service");
		
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", hibernateDDLAuto);
		jpaProperties.setProperty("hibernate.show_sql", hibernateShowSql);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		return entityManagerFactoryBean;
	}
	
}
