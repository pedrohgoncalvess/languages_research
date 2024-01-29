package database

object DatabaseMigration {
  import org.flywaydb.core.Flyway
  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load()
  private val dbProperties = config.getConfig("postgres").getConfig("properties")
  private val user = dbProperties.getString("user")
  private val password = dbProperties.getString("password")
  private val host = dbProperties.getString("serverName")
  private val port = dbProperties.getInt("portNumber")
  private val dbName = dbProperties.getString("databaseName")

  val flyway = Flyway.configure.dataSource(s"jdbc:postgresql://$host:$port/$dbName", user, password).load()
}

object Migration{

  def runMigrations(): Unit = {
    import DatabaseMigration.flyway
    flyway.migrate()
  }

}
