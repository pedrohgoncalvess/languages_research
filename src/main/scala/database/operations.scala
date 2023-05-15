package database

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(executor)
}


object operations {

  import slick.jdbc.MySQLProfile.api._
  import PrivateExecutionContext._

  def insertRepositorie(repositorie: Repositories, numTries: Int = 3): Future[Unit] = {

    val insertRepo = Tables.repositoriesTable += repositorie
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
}