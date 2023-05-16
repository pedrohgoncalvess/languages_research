package database

import java.util.concurrent.Executors
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

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

  def maxIDRepositorie: Option[Long] = {
    val table = Tables.repositoriesTable.map(_.id_repo)
    val max = table.max

    val result = connection.db.run(max.result)
    Await.result(result, 5.seconds)

    result.value.get.get
  }
}