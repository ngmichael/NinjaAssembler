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

  var verbose: Boolean = false

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
        case "--verbose" =>
          verbose = true;
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
    if (verbose) printLexResult(tokens)
    instructions = Parser.parse(tokens)
    if (verbose) printParseResult(instructions)
    binaryCode = CodeGenerator.generate(instructions)


    val bos = new BufferedOutputStream(new FileOutputStream(outputFilePath))
    bos.write(binaryCode.toArray)
    bos.close()
  }

  private def printLexResult(result: List[(Int, List[Token])]): Unit = {
    println("Result of lexical analysis:")
    for (tokenLine: (Int, List[Token]) <- tokens) {
      print(tokenLine._1 + ": ")
      for (token: Token <- tokenLine._2) {
        print(token + " ")
      }
      println()
    }
  }

  private def printParseResult(result: List[Instruction]): Unit = {
    println("Result of context analysis:")
    for (instruction: Instruction <- instructions) {
      println(instruction)
    }
  }
}
