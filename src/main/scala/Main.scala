package main.scala

import main.scala.lexer.Token

import scala.io.Source

/**
  * Main object of the NinjaAssembler
  */
object Main {
  var filePath: String = ""
  var input: Iterator[String] = Iterator.empty
  var tokens: List[(Int, List[Token])] = List.empty

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
          if (!x.startsWith("--")) {
            filePath = x
          }
      }
    }

    input = Source.fromFile(filePath).getLines()
    //
  }
}
