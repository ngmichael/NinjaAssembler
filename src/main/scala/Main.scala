package main.scala

import java.io.{BufferedOutputStream, FileOutputStream}

import main.scala.lexer.{Lexer, Token}
import main.scala.parser.{Instruction, Parser}

import scala.io.Source

/**
  * Main object of the NinjaAssembler
  */
object Main {
  var inputFilePath: String = ""
  var outputFilePath: String = ""
  var input: Iterator[String] = Iterator.empty
  var tokens: List[(Int, List[Token])] = List.empty
  var instructions: List[Instruction] = List.empty
  var binaryCode: List[Byte] = List.empty

  // Parse command line arguments
  def main(args: Array[String]): Unit = {
    var i: Int = 0
    while(i < args.length) {
      val s: String = args(i)
      s match {
        case "--help" =>
          println()
          return
        case "--version" =>
          println("Ninja Assembler version 0.0.1")
          return
        case "--output" =>
          i += 1
          outputFilePath = args(i)
        case _: String =>
          if (!s.startsWith("--")) {
            inputFilePath = s
          }
      }
      i += 1
    }

    input = Source.fromFile(inputFilePath).getLines()
    tokens = Lexer.generateTokenList(input)
    instructions = Parser.parse(tokens)
    binaryCode = CodeGenerator.generate(instructions)


    val bos = new BufferedOutputStream(new FileOutputStream(outputFilePath))
    bos.write(binaryCode.toArray)
    bos.close()
  }
}
