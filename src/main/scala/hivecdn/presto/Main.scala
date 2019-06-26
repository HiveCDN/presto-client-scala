package hivecdn.presto

import java.sql.ResultSet

object Main extends App {



  override def main(args: Array[String]): Unit = {
    case class Test2(id: Int, name: String, rnd: Double, module: String, active: Boolean)
    implicit val resultSet2Test2: ResultSet => Test2 =
      (rs: ResultSet) =>
        Test2(rs.getInt("id"), rs.getString("name"), rs.getDouble("rnd"), rs.getString("module"), rs.getBoolean("active"))

    val prestoClient = new PrestoClient(
      PrestoDBConfig(
        "presto.kudu.hivecdn.com",
        8086,
        "kudu",
        "default",
        "a"
      ))

    val result = prestoClient.runQueryAsList[Test2]("select * from kudu.default.test2")
    val result2: Int = prestoClient.runQueryAsScalar("select count(*) from kudu.default.test2")
    println(s"result = $result count=$result2")

  }

}
