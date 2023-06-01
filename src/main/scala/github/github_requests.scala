package github

import database.github_schema.githubOperations._
import database.github_schema._
import ujson.{Value, validate}

import scala.collection.mutable.ArrayBuffer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable._

object github_requests extends App {
  getUrlRepositories(since=maxIDRepositorie.getOrElse(None).toString)
  //getLimitRate

  def getLimitRate: Unit = {

    val requestVals = RequestParams()

    val repo = requests.get(requestVals.rateLimit, headers = requestVals.headers)
    println(repo.text())
  }

  def getUrlRepositories(since:String = "0"):Unit = {

    val requestVals = RequestParams()

    val responseUrl = requests.get(requestVals.urlRepositories(since=since), headers = requestVals.headers)
    val jsonRepositories = ujson.read(responseUrl.text())
    val repositories = jsonRepositories.arr.map(_.obj)

    val repositoriesInfos:ArrayBuffer[Int] = repositories.map(repoinfo => {
      val idRepository = repoinfo.get("id").getOrElse(None).toString.toInt
      val infosRepo = repoinfo.get("full_name").getOrElse(None).toString.split("/")

      val owner = infosRepo(0).replace("\"","")
      val repo_name = infosRepo(1).replace("\"","")

      try {

        //get and insert basic infos about repo
        val jsonInfo = getBasicInfos(owner, repo_name, idRepository)

        //insert tags
        getTagsRepo(jsonInfo, idRepository)

        //insert languages
        getLanguageRepo(owner, repo_name, idRepository)

      }
      catch {
        case e: Throwable => println(s"Cannot access repo. Reason: $e")
      }

        Thread.sleep(500) //sleep between infos of repos
      idRepository.toString.toInt
    })
    Thread.sleep(500) //sleep between list of repos
    getUrlRepositories(repositoriesInfos.max.toString)
  }

  def getBasicInfos(owner:String, repo_name:String, idRepository:Int): Value.Value = {
    val requestVals = RequestParams()

    //insert basic infos
    val urlInfo = requestVals.urlInformation(owner = owner, repo_name = repo_name)
    val repoInfo = requests.get(urlInfo, headers = requestVals.headers)

    val jsonInfo = ujson.read(repoInfo.text())

    val created_at = jsonInfo.obj("created_at").str
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(created_at, inputFormatter)
    val formated_created = dateTime.toLocalDate()

    val stars = jsonInfo.obj("stargazers_count").num.toInt
    val forks = jsonInfo.obj("forks_count").num.toInt

    val repositorieClass = Repositories(0L, id_repo = idRepository, owner = owner, name_repo = repo_name, created_at = formated_created, stars = stars, forks = forks)
    insertRepositorie(repositorieClass)
    jsonInfo
  }

    def getLanguageRepo(owner: String, name_repo:String, idRepository:Int): Unit = {
      val requestVals = RequestParams()

      val basicUrl = requestVals.urlLanguages(owner=owner, name_repo)
      val repo = requests.get(basicUrl, headers = requestVals.headers)
      val jsonInfo = ujson.read(repo.text()).obj
      if (jsonInfo.toArray.length > 0) {
        val resultMap = Map[String, Int]()
        var sumLang: Double = 0
        for ((key, value) <- jsonInfo) {
          resultMap += (key -> value.toString().toInt)
          sumLang += value.toString().toInt
        }
        resultMap.keys.foreach(language => {
          val useLanguage = resultMap.getOrElse(language, None).toString.toInt
          val representatitivy = useLanguage / sumLang
          val newLanguage = LanguagesRepositories(id_compost=s"$idRepository-$language",
            id_repo=idRepository,
            language=language,
            representativity=representatitivy,
            use_language=useLanguage)
          insertLanguage(newLanguage)
                        })
      }
      Thread.sleep(500)
    }

      def getTagsRepo(json:Value.Value, idRepository:Int): Unit = {
      val repoTags = json.obj.get ("topics")
      val repoTagsArray = repoTags.map (tags => {
      val stringsList = tags.arr.map (_.str)
      stringsList.toArray
      }).getOrElse (Array.empty[String] )

        if (repoTagsArray.length > 0) {
        repoTagsArray.foreach (tag_repo => {
        val tagClass = TagsRepositories (id_compost = s"$idRepository-$tag_repo"
        , id_repo = idRepository.toString.toInt,
        _tag = tag_repo)
        insertTag(tagClass)
        })
      }
    }
  }