package github

case class RequestParams() {

  val token = "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"

  val headers = Map(
    "Accept" -> "application/vnd.github+json",
    "Authorization" -> s"Bearer $token",
    "X-GitHub-Api-Version" -> "2022-11-28"
  )


  def urlRepositories(since: Int = 0): String = {
    s"https://api.github.com/repositories?since=$since"
  }

  def urlLanguages(owner: String, repo_name:String): String = {
    s"https://api.github.com/repos/$owner/$repo_name/languages"
  }

  def urlInformation(owner: String, repo_name:String): String = {
    s"https://api.github.com/repos/$owner/$repo_name"
  }
}
