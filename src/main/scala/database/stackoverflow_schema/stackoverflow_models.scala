package database.stackoverflow_schema

import java.time.LocalDate

case class Questions(
                      id_question:Int,
                      title:String,
                      view_count:Int,
                      answer_count:Int,
                      score:Int,
                      is_answered:Boolean,
                      creation_date:LocalDate,
                      last_activity:LocalDate
                    )

case class QuestionsTags(
                          id_question:Int,
                          id_compost:String,
                          tag_question:String
                        )

object stackoverflowTables {

  import slick.jdbc.MySQLProfile.api._

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

    def id_compost = column[String]("id_compost")

    def tag_question = column[String]("tag_question")

    override def * = (id_question, id_compost, tag_question) <> (QuestionsTags.tupled, QuestionsTags.unapply)
  }

  lazy val questionsTagTable = TableQuery[QuestionsTagsTable]

}
