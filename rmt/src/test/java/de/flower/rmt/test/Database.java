package de.flower.rmt.test;

import de.flower.common.util.IO;
import org.apache.commons.lang3.Validate;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.operation.TransactionOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Map;



public class Database {

    private final static Logger log = LoggerFactory.getLogger(Database.class);

    private DataSource dataSource;

    private Map<String, Object> properties;

    public Database(Map<String, Object> properties) {
        this.properties = properties;
    }

    public static IDataSet createDataSet(String classpathResource) {
        try {
            return new FlatXmlDataSetBuilder().build(IO.loadClasspathResourceAsStream(classpathResource));
        } catch (DataSetException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(String[] tables)  {
        IDatabaseConnection connection;
        try {
            connection = getDatabaseConnection();
            IDataSet dataset = connection.createDataSet(tables);
            deleteAll(dataset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(IDataSet dataSet) {
        log.info("Delete all. Dataset: " + dataSet);
        execute(DatabaseOperation.DELETE_ALL, dataSet);
    }

    public void cleanInsert(IDataSet dataSet) {
        log.info("Clean inserting dataset: " + dataSet);
        execute(DatabaseOperation.CLEAN_INSERT, dataSet);
    }

    public void export(String filename, String[] tables) {
        try {
            IDatabaseConnection connection = getDatabaseConnection();
            IDataSet dataset = connection.createDataSet(tables);
            FlatXmlDataSet.write(dataset, new FileOutputStream(filename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void execute(DatabaseOperation operation, IDataSet dataSet) {
        IDatabaseConnection connection;
        try {
            connection = getDatabaseConnection();
            TransactionOperation transOp = new TransactionOperation(operation);
            transOp.execute(connection, dataSet);
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private IDatabaseConnection getDatabaseConnection()  {
        IDatabaseConnection connection;
        try {
            connection = new DatabaseDataSourceConnection(Validate.notNull(dataSource));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return configureConnection(connection);
    }

    private IDatabaseConnection configureConnection(IDatabaseConnection connection) {
        for (String key : properties.keySet()) {
            connection.getConfig().setProperty(key, properties.get(key));
        }
        return connection;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
}