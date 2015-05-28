package com.objectfrontier.sms.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.objectfrontier.sms.dao", sqlSessionFactoryRef = "sqlSessionFactorySMS")
public class MessageDSConfig {

    private static final Logger logger = LoggerFactory.getLogger(MessageDSConfig.class);

    @Value("${sms.url}")
    private String jdbcUrl;

    @Value("${sms.username}")
    private String jdbcUsername;

    @Value("${sms.password}")
    private String jdbcPassword;

    @Value("${sms.driverClass}")
    private String driverClass;

    @Value("${sms.idleMaxAgeInMinutes}")
    private String idleMaxAgeInMinutes;

    @Value("${sms.idleConnectionTestPeriodInMinutes}")
    private String idleConnectionTestPeriodInMinutes;

    @Value("${sms.maxConnectionsPerPartition}")
    private String maxConnectionsPerPartition;

    @Value("${sms.minConnectionsPerPartition}")
    private String minConnectionsPerPartition;

    @Value("${sms.partitionCount}")
    private String partitionCount;

    @Value("${sms.acquireIncrement}")
    private String acquireIncrement;

    @Value("${sms.statementsCacheSize}")
    private String statementsCacheSize;

    @Bean(destroyMethod = "close")
    public DataSource dataSourceInia() {

        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setIdleConnectionTestPeriodInMinutes(Long.valueOf((idleConnectionTestPeriodInMinutes)).longValue());
        dataSource.setIdleMaxAgeInMinutes(Long.parseLong(idleMaxAgeInMinutes));
        dataSource.setMaxConnectionsPerPartition(Integer.parseInt(maxConnectionsPerPartition));
        dataSource.setMinConnectionsPerPartition(Integer.parseInt(minConnectionsPerPartition));
        dataSource.setPartitionCount(Integer.parseInt(partitionCount));
        dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
        dataSource.setStatementsCacheSize(Integer.parseInt(statementsCacheSize));
        logger.info("SMS data source intialized ----");
        return dataSource;
    }

    @Bean
    @Qualifier("messageDSTxMgr")
    public DataSourceTransactionManager transactionManagerInia() throws ClassNotFoundException {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSourceInia());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactorySMS() throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSourceInia());
        sessionFactoryBean.setTypeAliasesPackage("com.objectfrontier.sms.model");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/objectfrontier/sms/dao/*.xml"));
        return (SqlSessionFactory) sessionFactoryBean.getObject();
    }

}