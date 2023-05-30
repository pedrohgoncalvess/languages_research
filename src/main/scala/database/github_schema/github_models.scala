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

case class TagsRepositories(
                       id_compost:String,
                       id_repo:Int,
                       _tag:String
                       )

case class LanguagesRepositories(
                                id_compost:String,
                                id_repo:Int,
                                language:String,
                                use_language:Int,
                                representativity:Double
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
  class TagsTable(tag:Tag) extends Table[TagsRepositories](tag,Some("github"),"repositories_tags") {
    def id_compost = column[String]("id_compost",O.Unique,O.PrimaryKey, O.Length(70))

    def id_repo = column[Int]("id_repo")

    def _tag = column[String]("tag",O.Length(50))

    override def * = (id_compost,id_repo,_tag) <> (TagsRepositories.tupled,TagsRepositories.unapply)
  }
  class LanguagesTable(tag:Tag) extends Table[LanguagesRepositories](tag,Some("github"),"repositories_languages") {
    def id_compost = column[String]("id_compost",O.PrimaryKey,O.Length(60))

    def id_repo = column[Int]("id_repo")

    def language = column[String]("language",O.Length(50))

    def use_language = column[Int]("use_language")

    def representativity = column[Double]("representativity")

    override def * = (id_compost,id_repo,language,use_language,representativity) <> (LanguagesRepositories.tupled, LanguagesRepositories.unapply)
  }

  lazy val languagesTable = TableQuery[LanguagesTable]
  lazy val tagsTable = TableQuery[TagsTable]
  lazy val repositoriesTable = TableQuery[RepositoriesTable]
}
