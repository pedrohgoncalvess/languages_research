package database.infos_schema

case class ProgrammingLanguages(
                              programming_language:String,
                              name:String,
                              _type:String
                              )

case class Tecnologies(
                    tecnologie:String,
                    _type:String
                      )

object Infos_tables {

  import slick.jdbc.MySQLProfile.api._

  class ProgrammingLanguageTable(tag:Tag) extends Table[ProgrammingLanguages](tag,Some("infos"),"programming_languages") {
    def programming_language = column[String]("programming_language")

    def name = column[String]("package")

    def _type = column[String]("type")

    override def * = (programming_language,name,_type) <> (ProgrammingLanguages.tupled,ProgrammingLanguages.unapply)
  }
  lazy val programmingLanguageTable = TableQuery[ProgrammingLanguageTable]

  class TecnologiesTable(tag: Tag) extends Table[Tecnologies](tag, Some("infos"), "tecnologies") {
    def tecnologie = column[String]("tecnologie")

    def _type = column[String]("type")

    override def * = (tecnologie, _type) <> (Tecnologies.tupled, Tecnologies.unapply)
  }
  lazy val tecnologiesTable = TableQuery[TecnologiesTable]

}
