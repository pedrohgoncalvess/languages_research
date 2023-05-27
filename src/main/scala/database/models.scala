package database

import java.time.LocalDate

case class Repositories(
                       id:Long,
                       id_repo:Long,
                       owner:String,
                       name_repo:String,
                       created_at:LocalDate,
                       stars:Int,
                       forks:Int
                       )

case class Questions(
                    id_question:Int,
                    title:String,
                    view_count:Int,
                    answer_count:Int,
                    score:Int,
                    is_answered:Boolean,
                    creation_date:LocalDate,
                    last_activity:LocalDate
                    )

case class QuestionsTags(
                        id_question:Int,
                        tag_question:String
                        )
