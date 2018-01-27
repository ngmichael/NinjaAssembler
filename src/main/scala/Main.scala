package main.scala

import main.scala.lexer.{Lexer, Token}

import scala.io.Source

/**
  * Main object of the NinjaAssembler
  */
object Main {
  var filePath: String = ""
  var input: Iterator[String] = Iterator.empty
  var tokens: List[(Int, List[Token])] = List.empty

  def main(args: Array[String]): Unit = {
    for (s: String <- args) {
      s match {
        case "--help" =>
          println()
          return
        case "--version" =>
          println("Ninja Assembler version 0.0.1")
          return
        case _ =>
          if (!s.startsWith("--")) {
            filePath = s
          }
      }
    }

    input = Source.fromFile(filePath).getLines()
    tokens = Lexer.generateTokenList(input)
    print(tokens)
  }
}
