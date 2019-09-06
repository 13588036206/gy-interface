package com.gy.gyweb.dynamicDS;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Mybatis配置
 * @author Louis
 * @date Oct 31, 2018
 */
@Configuration
public class MybatisConfig {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        Connection connection = null;
        try {
            String driverClassName = dataSourceConfig.getDriverClass();
            String url = dataSourceConfig.getUrl();
            String username = dataSourceConfig.getUser();
            String password = dataSourceConfig.getPassword();
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT a.gsdm,b.dburl,b.dbloginname,b.dbpassword,b.dbdriver " +
                    "from ca_crm a INNER JOIN ca_db b on a.dbid = b.dbid");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ComboPooledDataSource ds = new ComboPooledDataSource();
                try {
                    ds.setDriverClass(resultSet.getString("dbdriver"));
                    ds.setUser(resultSet.getString("dbloginname"));
                    ds.setPassword(resultSet.getString("dbpassword"));
                    ds.setJdbcUrl(resultSet.getString("dburl"));
                    ds.setAutomaticTestTable("Test");
                    ds.setAcquireIncrement(3); //当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
                    ds.setIdleConnectionTestPeriod(60);//每60秒检查所有连接池中的空闲连接
                    ds.setMaxIdleTime(60); //最大空闲时间,60秒内未使用则连接被丢弃。
                    ds.setMaxPoolSize(100);//连接池中保留的最大连接数
                    dataSourceMap.put(resultSet.getString("gsdm"), ds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }catch(Exception e){

        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        dynamicDataSource.setDataSources(dataSourceMap);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

   @Bean("sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}