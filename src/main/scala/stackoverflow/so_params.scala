package stackoverflow
import com.typesafe.config.ConfigFactory


case class SOParams() {

  private val config = ConfigFactory.load()
  private val key = config.getString("SO_KEY")
  private val token = config.getString("SO_TOKEN")

  def urlGetQuestionsPage(tag:String, pageSize:String):String = {
    val url = s"https://api.stackexchange.com/2.3/search/advanced?&access_token=$token&key=$key&page=$pageSize&pagesize=100&order=desc&sort=activity&tagged=$tag&filter=default&site=stackoverflow"
    url
  }

}
