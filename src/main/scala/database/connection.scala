package database

import slick.jdbc.MySQLProfile.api._

object connection {

  val db = Database.forConfig("mydb")

}
