package database.github_schema

import java.time.LocalDate

case class Repositories(
                         id:Long,
                         id_repo:Long,
                         owner:String,
                         name_repo:String,
                         created_at:LocalDate,
                         stars:Int,
                         forks:Int
                       )

object githubTables {

  import slick.jdbc.MySQLProfile.api._
  import java.time.LocalDate

  class RepositoriesTable(tag: Tag) extends Table[Repositories](tag, Some("github"), "repositories") {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

    def id_repo = column[Long]("id_repo")

    def owner = column[String]("owner")

    def name_repo = column[String]("name_repo")

    def created_at = column[LocalDate]("created_at")

    def stars = column[Int]("stars")

    def forks = column[Int]("forks")

    override def * = (id, id_repo, owner, name_repo, created_at, stars, forks) <> (Repositories.tupled, Repositories.unapply)

  }
  lazy val repositoriesTable = TableQuery[RepositoriesTable]
}
