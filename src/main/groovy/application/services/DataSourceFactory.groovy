package application.services

import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import org.sqlite.SQLiteConfig
import org.sqlite.javax.SQLiteConnectionPoolDataSource

import javax.sql.DataSource

import static com.diogonunes.jcdp.color.api.Ansi.*

enum DataSourceFactory {

    INSTANCE


    final DataSource dataSource
    final SqlSessionFactory sqlSessionFactory

    DataSourceFactory() {
        dataSource = setupDataSource()
        sqlSessionFactory = setupSqlSessionFactory()
    }


    SqlSessionFactory setupSqlSessionFactory() {

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment('production', transactionFactory, dataSource)
        Configuration configuration = new Configuration(environment)
        configuration.addMapper(BlogMapper.class)
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration)

        sqlSessionFactory
    }

    DataSource setupDataSource() {
        //Create the ConnectionPoolDataSource
        SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource()
        dataSource.setUrl('jdbc:sqlite:db/beerdb2.sqlite')

        //Pass in some additional config options (optional)
        SQLiteConfig config = new SQLiteConfig()
        config.enforceForeignKeys(true)
        config.enableLoadExtension(true)
        dataSource.setConfig(config)

        dataSource
    }
}