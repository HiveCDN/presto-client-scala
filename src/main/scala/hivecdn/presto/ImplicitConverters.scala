package hivecdn.presto

import java.sql.ResultSet

object ImplicitConverters {

  implicit val rs2String: ResultSet => String   = (rs: ResultSet) => rs.getString(1)
  implicit val rs2Int: ResultSet => Int         = (rs: ResultSet) => rs.getInt(1)
  implicit val rs2Boolean: ResultSet => Boolean = (rs: ResultSet) => rs.getBoolean(1)
  implicit val rs2Double: ResultSet => Double   = (rs: ResultSet) => rs.getDouble(1)
}
