lazy val prestoClient = project in file(".")
scalaVersion := "2.12.8"
libraryDependencies += "io.prestosql" % "presto-jdbc" % "315"