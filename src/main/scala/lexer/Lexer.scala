package main.scala.lexer

import scala.collection.mutable.ListBuffer

object Lexer {

  def generateTokenList(codeLines: Iterator[String]): List[(Int, List[Token])] = {
    val tokenLines: ListBuffer[(Int, List[Token])] = ListBuffer.empty
    var lineNumber: Int = 0

    while(codeLines.hasNext) {
      val line: String = codeLines.next()
      val tokens: List[Token] = parseLine(line)
      tokenLines.+=((lineNumber, tokens))
      lineNumber += 1
    }

    tokenLines.toList
  }

  private def parseLine(line: String): List[Token] = {
    val tokens: ListBuffer[Token] = ListBuffer.empty
    var pos: Int = 0
    var currentToken: String = ""

    while (pos < line.length) {
      line(pos) match {
        case ' ' => // Scanning of next token complete -> parse token
          tokens += parseToken(currentToken)
          currentToken = ""
        case '\n' =>
          tokens += parseToken(currentToken)
          tokens += new EOLToken
          currentToken = ""
        case '/' =>
          if (pos + 1 <= line.length && line(pos+1) == '/') {
            pos = line.length-1
          }
          if (currentToken.nonEmpty) tokens += parseToken(currentToken)
          tokens += new EOLToken
          currentToken = ""
        case _ =>
          currentToken += line(pos)
      }

      pos += 1
    }

    if (currentToken.nonEmpty) {
      tokens += parseToken(currentToken)
      tokens += new EOLToken
    }

    tokens.toList
  }

  private def parseToken(token: String): Token = {
    token.toLowerCase match {
      case "halt" =>
        new HALT
      case "pushc" =>
        new PUSHC
      case "add" =>
        new ADD
      case _ =>
        new SyntaxErrorToken
    }
  }
}
