package database.github_schema

import java.util.concurrent.Executors
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

object githubOperations {

  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutionContext._
  import database.connection

  def insertRepositorie(repositorie: Repositories, numTries: Int = 3): Future[Unit] = {

    val insertRepo = githubTables.repositoriesTable += repositorie
    val futureId: Future[Int] = connection.db.run(insertRepo)
    futureId.flatMap { repo =>
      println(s"New repo has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertRepositorie(repositorie, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }

  def insertTag(tag_name: TagsRepositories, numTries: Int = 3): Future[Unit] = {

    val insertNewTag = githubTables.tagsTable += tag_name
    val futureId: Future[Int] = connection.db.run(insertNewTag)
    futureId.flatMap { _ =>
      println(s"New tag has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertTag(tag_name, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }

  def insertLanguage(language: LanguagesRepositories, numTries: Int = 3): Future[Unit] = {

    val insertNewLanguage = githubTables.languagesTable += language
    val futureId: Future[Int] = connection.db.run(insertNewLanguage)
    futureId.flatMap { _ =>
      println(s"New language has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertLanguage(language, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }

  def maxIDRepositorie: Option[Long] = {
    val table = githubTables.repositoriesTable.map(_.id_repo)
    val max = table.max

    val result = connection.db.run(max.result)
    Await.result(result, 5.seconds)

    result.value.get.get
  }
}
