package database.stackoverflow_schema

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

object stackoverflowOperations {

  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutionContext._
  import database.connection

  def insertQuestion(quest: Questions, numTries: Int = 3): Future[Unit] = {

    val insertQuest = stackoverflowTables.questionsTable += quest
    val futureId: Future[Int] = connection.db.run(insertQuest)
    futureId.flatMap { q =>
      println(s"New question has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertQuestion(quest, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }

  def insertTagQuestion(tagQuest: QuestionsTags, numTries: Int = 3): Future[Unit] = {

    val insertTag = stackoverflowTables.questionsTagTable += tagQuest
    val futureId: Future[Int] = connection.db.run(insertTag)
    futureId.flatMap { q =>
      println(s"New tag has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertTagQuestion(tagQuest, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }
}
