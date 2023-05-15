package database

import DatabaseMigration.flyway

object main extends App{
  flyway.migrate()
}
