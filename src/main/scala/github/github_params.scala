package github
import com.typesafe.config.ConfigFactory




case class RequestParams() {

  private val config = ConfigFactory.load()

  val token = config.getString("GIT_TOKEN")

  val headers = Map(
    "Accept" -> "application/vnd.github+json",
    "Authorization" -> s"Bearer $token",
    "X-GitHub-Api-Version" -> "2022-11-28"
  )


  def urlRepositories(since: String = "0"): String = {
    s"https://api.github.com/repositories?since=$since"
  }

  def urlLanguages(owner: String, repo_name:String): String = {
    s"https://api.github.com/repos/$owner/$repo_name/languages"
  }

  def urlInformation(owner: String, repo_name:String): String = {
    s"https://api.github.com/repos/$owner/$repo_name"
  }

  def rateLimit: String = {
    "https://api.github.com/rate_limit"
  }
}
