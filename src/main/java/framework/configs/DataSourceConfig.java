package framework.configs;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import framework.utils.CommonJdbcTemplate;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties("main.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Primary
	@Bean(name = "commonJdbcTemplate")
	public CommonJdbcTemplate commonJdbcTemplate(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
		return new CommonJdbcTemplate(jdbcTemplate);
	}
	
	
	@Bean(name = "bankBotDataSource")
	@ConfigurationProperties("bankbot.datasource")
	public DataSource bankBotSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "bankBotJdbcTemplate")
	public JdbcTemplate bankBotJdbcTemplate(@Qualifier("bankBotDataSource") DataSource bankBotDataSource) {
		return new JdbcTemplate(bankBotDataSource);
	}

	@Bean(name = "bankBotCommonJdbcTemplate")
	public CommonJdbcTemplate bankBotCommonJdbcTemplate(@Qualifier("bankBotJdbcTemplate") JdbcTemplate bankBotJdbcTemplate) {
		return new CommonJdbcTemplate(bankBotJdbcTemplate);
	}
}
