package stackoverflow

import ujson._
import upickle.core.LinkedHashMap
import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}
import scala.collection.mutable.ArrayBuffer
import database.stackoverflow_schema.stackoverflowOperations._
import database.stackoverflow_schema._


object so_requests extends App {
  getInfos(requestQuestions)

  def requestQuestions: ArrayBuffer[LinkedHashMap[String, Value]] = {

    val requestVals = SOParams()
    val responseUrl = requests.get(requestVals.urlGetQuestionsPage(tag = "scala", pageSize = "1"))
    val jsonRepositories = ujson.read(responseUrl.text())
    val repositories = jsonRepositories.obj.getOrElse("items", Null)
    val jsonArray = ujson.read(repositories).arr
    val mapQuestions = jsonArray.map(_.obj)

    mapQuestions
  }

  def getInfos(mapQuestions: ArrayBuffer[LinkedHashMap[String, Value]]): Unit = {
    mapQuestions.foreach(answer => {

      val creationId = answer.getOrElse("question_id",None).toString.toInt
      val viewCount = answer.getOrElse("view_count",None).toString.toInt
      val lastActivityDate = answer.getOrElse("last_activity_date",None).toString.toLong
      val creationDate = answer.getOrElse("creation_date",None).toString.toLong
      val answerCount = answer.getOrElse("answer_count",None).toString.toInt
      val title = answer.getOrElse("title",None).toString
      val score = answer.getOrElse("score",None).toString.toInt
      val isAnswered = answer.getOrElse("is_answered",None).toString.toBoolean

      val _creationDateFormat = Instant.ofEpochSecond(creationDate)
      val creationDateFormated = LocalDate.ofInstant(_creationDateFormat, ZoneId.systemDefault())
      val _lastActivityDateFormat = Instant.ofEpochSecond(lastActivityDate)
      val lastActivityDateFormated = LocalDate.ofInstant(_lastActivityDateFormat, ZoneId.systemDefault())

      val newQuestion = Questions(id_question=creationId,
        title=title,
        view_count=viewCount,
        answer_count=answerCount,
        score=score,
        is_answered=isAnswered,
        creation_date=creationDateFormated,
        last_activity = lastActivityDateFormated)
      insertQuestion(newQuestion)

      val jsonTags = answer.get("tags")
      jsonTags.map(tags => {
        val stringList = tags.arr.map(_.str)
        stringList.map(tag => {
          val newTagQuest = QuestionsTags(
            id_question = creationId,
            id_compost = s"$creationId-$tag",
            tag_question = tag
          )
          insertTagQuestion(newTagQuest)
        })
      })
    })
  }
}
