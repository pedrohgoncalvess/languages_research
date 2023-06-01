package database.infos_schema

import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, Future}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}

class infos_operations {

  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutionContext._
  import database.infos_schema._
  import database.connection

  def insertProgrammingLanguage(language: ProgrammingLanguages, numTries: Int = 3): Future[Unit] = {

    val insertLanguage = Infos_tables.programmingLanguageTable += language
    val futureId: Future[Int] = connection.db.run(insertLanguage)
    futureId.flatMap { _ =>
      println(s"New programming language has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertProgrammingLanguage(language, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }

  def insertTecnologie(tecnologie: Tecnologies, numTries: Int = 3): Future[Unit] = {

    val insertNewTecnologie = Infos_tables.tecnologiesTable += tecnologie
    val futureId: Future[Int] = connection.db.run(insertNewTecnologie)
    futureId.flatMap { _ =>
      println(s"New programming language has been added.")
      Future.successful(())
    }.recoverWith {
      case ex: Throwable if numTries > 1 =>
        println(s"Query failed, reason: $ex")
        insertTecnologie(tecnologie, numTries - 1)
      case ex: Throwable =>
        println(s"Query failed after $numTries tries, reason: $ex")
        Future.failed(ex)
    }
  }
}
