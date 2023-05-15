package github

import database.Repositories
import database.operations.insertRepositorie

import scala.collection.mutable.ArrayBuffer
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object github_requests extends App {
  getUrlRepositories(since=1928)
  //getTeste

  def getTeste: Unit = {

    val requestVals = RequestParams()
    val headers = requestVals.headers

    val basicUrl = requestVals.urlLanguages(owner = "pedrohgoncalvess", "webserver-jobanalytics")
    val repo = requests.get(basicUrl, headers = headers)
    val jsonInfo = ujson.read(repo.text()).obj
    println(jsonInfo.foreach(println)) //LANGUAGES -> NUM LINES
  }

  def getUrlRepositories(since:Int = 0):Unit = {

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

        Thread.sleep(1500)
      idRepository.toString.toInt
    })
    Thread.sleep(2000)
    getUrlRepositories(repositoriesInfos.max)
  }


    def getLanguageRepo(owner: String, name_repo:String): Unit = {
      val requestVals = RequestParams()

      val urlRequestApi = requests.get(requestVals.urlLanguages(owner=owner,repo_name = name_repo), headers=requestVals.headers)

      val jsonRepositories = ujson.read(urlRequestApi.text())
      val languages = jsonRepositories.arr.map(_.obj)
      println(languages.getClass)
      println(languages)
      languages.foreach(println)


//        val jsonLang = ujson.read(responseLang.text())
//        jsonLang match {
//          case obj: ujson.Obj =>
//            val languageMap = obj.value
//
//            languageMap.foreach { case (language, _) =>
//              println(s"Repo id:${repoInfo.get("id").getOrElse(None)} \nLanguage: $language")
//            }
//
//          case _ =>
//            println("O JSON não é um objeto.")
//        }
    }
  }