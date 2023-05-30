package github

import database.github_schema.githubOperations._
import database.github_schema._

import scala.collection.mutable.ArrayBuffer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object github_requests extends App {
  //getTeste
  getUrlRepositories(since=maxIDRepositorie.getOrElse(None).toString)
  //getLimitRate

  def getLimitRate: Unit = {

    val requestVals = RequestParams()

    val repo = requests.get(requestVals.rateLimit, headers = requestVals.headers)
    println(repo.text())
  }

  def getTeste: Unit = {

    val requestVals = RequestParams()
    val headers = requestVals.headers

    val basicUrl = requestVals.urlLanguages(owner = "pedrohgoncalvess", "webserver-jobanalytics")
    val repo = requests.get(basicUrl, headers = headers)
    val jsonInfo = ujson.read(repo.text()).obj
    println(jsonInfo.foreach(println)) //LANGUAGES -> NUM LINES
  }

  def getUrlRepositories(since:String = "0"):Unit = {

    val requestVals = RequestParams()

    val responseUrl = requests.get(requestVals.urlRepositories(since=since), headers = requestVals.headers)
    val jsonRepositories = ujson.read(responseUrl.text())
    val repositories = jsonRepositories.arr.map(_.obj)

    val repositoriesInfos:ArrayBuffer[Int] = repositories.map(repoinfo => {
      val idRepository = repoinfo.get("id").getOrElse(None)
      val infosRepo = repoinfo.get("full_name").getOrElse(None).toString.split("/")

      val owner = infosRepo(0).replace("\"","")
      val repo_name = infosRepo(1).replace("\"","")

      try {
        val urlInfo = requestVals.urlInformation(owner = owner, repo_name = repo_name)
        val repoInfo = requests.get(urlInfo, headers = requestVals.headers)

        val jsonInfo = ujson.read(repoInfo.text())

        val created_at = jsonInfo.obj("created_at").str
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(created_at, inputFormatter)
        val formated_created = dateTime.toLocalDate()

        val stars = jsonInfo.obj("stargazers_count").num.toInt
        val forks = jsonInfo.obj("forks_count").num.toInt

        val repositorieClass = Repositories(0L, id_repo = idRepository.toString.toInt, owner = owner, name_repo = repo_name, created_at = formated_created, stars = stars, forks = forks)
        insertRepositorie(repositorieClass)
      }
      catch {
        case e: Throwable => println(s"Cannot access repo. Reason: $e")
      }

        Thread.sleep(500) //sleep between infos of repos
      idRepository.toString.toInt
    })
    Thread.sleep(1000) //sleep between list of repos
    getUrlRepositories(repositoriesInfos.max.toString)
  }

    def getLanguageRepo(owner: String, name_repo:String): Unit = {
      val requestVals = RequestParams()

      val urlRequestApi = requests.get(requestVals.urlLanguages(owner=owner,repo_name = name_repo), headers=requestVals.headers)

      val jsonRepositories = ujson.read(urlRequestApi.text())
      val languages = jsonRepositories.arr.map(_.obj)
      println(languages.getClass)
      println(languages)
      languages.foreach(println)
    }
  }