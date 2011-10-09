package de.flower.test

import javax.sql.DataSource
import org.dbunit.operation.{TransactionOperation, DatabaseOperation}
import org.dbunit.database.{DatabaseDataSourceConnection, IDatabaseConnection}
import org.dbunit.dataset.IDataSet
import org.slf4j.{LoggerFactory, Logger}
import org.apache.commons.lang3.Validate
import de.flower.common.util.IO
import org.dbunit.dataset.xml.{FlatXmlDataSet, FlatXmlDataSetBuilder}
import java.io.FileOutputStream

/**
 *
 * @author flowerrrr
 */

object Database {

    def createDataSet(classpathResource: String): IDataSet = {
        return new FlatXmlDataSetBuilder().build(IO.loadClasspathResourceAsStream(classpathResource))
    }
}

class Database(properties: Map[String, Object]) {

    private val log: Logger = LoggerFactory.getLogger(getClass)

    private var dataSource: DataSource = _

    def deleteAll(tables: Array[String]) {
        var connection: IDatabaseConnection = getDatabaseConnection
        val dataset = connection.createDataSet(tables);
        deleteAll(dataset)
    }

    def deleteAll(dataSet: IDataSet) {
        log.info("Delete all. Dataset: " + dataSet)
        execute(DatabaseOperation.DELETE_ALL, dataSet)
    }

    def cleanInsert(dataSet: IDataSet) {
        log.info("Clean inserting dataset: " + dataSet)
        execute(DatabaseOperation.CLEAN_INSERT, dataSet)
    }

    def export(filename: String, tables: Array[String]) {
        var connection: IDatabaseConnection = getDatabaseConnection
        val dataset = connection.createDataSet(tables);
        FlatXmlDataSet.write(dataset, new FileOutputStream(filename));
    }

    private def execute(operation: DatabaseOperation, dataSet: IDataSet) {
        var connection: IDatabaseConnection = getDatabaseConnection
        var transOp: TransactionOperation = new TransactionOperation(operation)
        transOp.execute(connection, dataSet)
        connection.close
    }

    private def getDatabaseConnection: IDatabaseConnection = {
        val connection = new DatabaseDataSourceConnection(Validate.notNull(dataSource))
        return configureConnection(connection)
    }

    private def configureConnection(connection: IDatabaseConnection): IDatabaseConnection = {
        properties.foreach((e) => connection.getConfig.setProperty(e._1, e._2))
        return connection
    }

    /**
     * @return the dataSource
     */
    def getDataSource: DataSource = dataSource

    /**
     * @param dataSource
     *            the dataSource to set
     */
    def setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
    }
}