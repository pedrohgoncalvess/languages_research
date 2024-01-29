package logs

import java.io.{File, FileWriter, BufferedWriter, FileReader, BufferedReader}


object logs {
  def verifyFile: Unit = {
    val diretorio = new File("caminho/do/diretorio")
    val arquivo = new File(diretorio, "nome-do-arquivo.txt")

    if (!arquivo.exists()) {
      println("Existe.")
    } else {
      println("Não existe.")
    }
  }
}
