package main.scala.parser

import main.scala.lexer
import main.scala.lexer.{EOLToken, Token}

import scala.collection.mutable.ListBuffer

//noinspection ComparingLength
object Parser {

  def parse(tokens: List[(Int, List[Token])]): List[Instruction] = {
    val instructions: ListBuffer[Instruction] = ListBuffer.empty

    for (line: (Int, List[Token]) <- tokens) {
      instructions += parseTokenList(line._1, line._2)
    }

    instructions.toList
  }

  private def parseTokenList(lineNumber: Int, line: List[Token]): Instruction = {

    if (line.length > 3 || line.length < 2) {
      return NoOp(lineNumber, -1)
    }

    line.head match {
      case _: lexer.HALT =>
        parseHalt(lineNumber, line)

    }

    NoOp(lineNumber, 0)
  }

  private def parseHalt(lineNuber: Int, tokens: List[Token]): Instruction = {
    if (tokens.length != 2 || tokens(1).isInstanceOf[EOLToken]) NoOp(lineNuber, 6)
    if (!tokens.head.isInstanceOf[lexer.HALT]) NoOp(lineNuber, 1)
    HALT()
  }

}
