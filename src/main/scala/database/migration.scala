package database

object DatabaseMigration{
  import org.flywaydb.core.Flyway
  val flyway = Flyway.configure.dataSource("jdbc:mysql://localhost:3306/languages_search", "root", "fodao002").load()
}

object Migration extends App {

  import DatabaseMigration.flyway

  flyway.migrate()
}
