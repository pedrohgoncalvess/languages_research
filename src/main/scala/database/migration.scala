package database





object DatabaseMigration{
  import org.flywaydb.core.Flyway
  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load()
  private val user = config.getString("LOGIN_DATABASE")
  private val password = config.getString("PASSWORD_DATABASE")

  val flyway = Flyway.configure.dataSource("jdbc:mysql://localhost:3306/languages_search", "root", "fodao002").load()
}

object Migration extends App {

  import DatabaseMigration.flyway

  flyway.migrate()
}
