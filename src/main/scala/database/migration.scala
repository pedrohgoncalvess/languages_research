package database

object DatabaseMigration{
  import org.flywaydb.core.Flyway
  val flyway = Flyway.configure.dataSource("jdbc:mysql://localhost:3306/languages_search", "root", "fodao002").load()
}
