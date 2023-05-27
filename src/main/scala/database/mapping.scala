package database
import database._

object Tables {

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


  class QuestionsTable(tag: Tag) extends Table[Questions](tag, Some("stackoverflow"), "questions") {
    def id_question = column[Int]("id_question", O.Unique, O.PrimaryKey)

    def title = column[String]("title")

    def view_count = column[Int]("view_count")

    def answer_count = column[Int]("answer_count")

    def score = column[Int]("score")

    def is_answered = column[Boolean]("is_answered")

    def creation_date = column[LocalDate]("creation_date")

    def last_activity = column[LocalDate]("last_activity")

    override def * = (id_question, title, view_count, answer_count, score, is_answered, creation_date, last_activity) <> (Questions.tupled, Questions.unapply)
  }

  lazy val questionsTable = TableQuery[QuestionsTable]

  class QuestionsTagsTable(tag: Tag) extends Table[QuestionsTags](tag, Some("stackoverflow"), "questions_tags") {
    def id_question = column[Int]("id_question")

    def tag_question = column[String]("tag")

    override def * = (id_question, tag_question) <> (QuestionsTags.tupled, QuestionsTags.unapply)
  }

  lazy val questionsTagTable = TableQuery[QuestionsTagsTable]

}
