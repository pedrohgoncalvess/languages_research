ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "prog-languages-search"
  )

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "requests" % "0.8.0", //REQUESTS
  "com.typesafe.slick" %% "slick" % "3.3.3", //FRM
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3", //FRM
  "mysql" % "mysql-connector-java" % "8.0.27", //MYSQL DRIVER
  "org.flywaydb" % "flyway-core" % "7.14.0", //MIGRATIONS
  "ch.qos.logback" % "logback-classic" % "1.2.3", //LOGBACK
  "com.lihaoyi" %% "ujson" % "3.1.0", //READ RESPONSE OF REQUESTS
  "com.typesafe" % "config" % "1.4.1" //READ APPLICATION.CONF
)