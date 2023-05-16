package github

case class RequestParams() {

  val token = "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"

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
}
