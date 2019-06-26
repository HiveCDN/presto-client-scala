package hivecdn.presto

import java.sql.{Connection, DriverManager, ResultSet}
import ImplicitConverters._
class PrestoClient(config: PrestoDBConfig) {
  private val url          = config.toJDBCString
  private val prestoDriver = new io.prestosql.jdbc.PrestoDriver
  DriverManager.registerDriver(prestoDriver)
  private val connection: Connection = DriverManager.getConnection(url)

  def runQueryAsList[T](sql: String)(implicit converter: ResultSet => T): List[T] = {
    val statement       = connection.createStatement()
    val rs              = statement.executeQuery(sql)
    val result: List[T] = convert[T](rs)(converter)
    statement.close()
    result
  }

  def runQueryAsSingle[T](sql: String)(implicit converter: ResultSet => T): T = {
    runQueryAsList[T](sql).head
  }
  def runQueryAsScalar(sql: String): Int = runQueryAsList[Int](sql).head

  private def convert[T](resultSet: ResultSet)(f: ResultSet => T): List[T] = {
    val result: List[T] = new Iterator[T] {
      def hasNext: Boolean = resultSet.next()
      def next(): T        = f(resultSet)
    }.toList
    result
  }
}

case class PrestoDBConfig(
    host: String,
    port: Int,
    catalog: String,
    schema: String,
    user: String = "",
    password: String = ""
) {
  val toJDBCString: String = s"jdbc:presto://$host:$port/$catalog/$schema?user=$user&password=$password"
}
