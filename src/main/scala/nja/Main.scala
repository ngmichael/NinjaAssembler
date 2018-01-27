package nja
import scala.io.Source

/**
  * Main object of the NinjaAssembler
  */
class Main {
  var filePath: String = ""
  var assembly: Iterator[String] = _

  def main(args: Array[String]): Unit = {
    for (x: String <- args) {
      x match {
        case "--help" =>
          println()
          return
        case "--version" =>
          println("Ninja Assembler version 0.0.1")
          return
        case _ =>
          val s: String = _
          if (!s.startsWith("--")) {
            filePath = s
          }
      }
    }

    assembly = Source.fromFile(filePath).getLines()
  }
}
