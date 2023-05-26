package stackoverflow
import stackoverflow.SOParams

object so_requests extends App{
  val requestVals = SOParams()

  val responseUrl = requests.get(requestVals.urlGetQuestionsPage(tag = "scala", pageSize = "1"))
  val jsonRepositories = ujson.read(responseUrl.text())
  println(jsonRepositories)
}
