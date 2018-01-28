package main.scala.lexer

import scala.collection.mutable.ListBuffer

object Lexer {

  val constantNumberMatcher: String = "(0|-?[1-9][0-9]*)"
  val identifierMatcher: String = "[A-Za-z_][A-Za-z0-9_]*"
  val charMatcher: String = "'\n'"


  def generateTokenList(codeLines: Iterator[String]): List[(Int, List[Token])] = {
    val tokenLines: ListBuffer[(Int, List[Token])] = ListBuffer.empty
    var lineNumber: Int = 1

    while(codeLines.hasNext) {
      val line: String = codeLines.next()
      val tokens: List[Token] = parseLine(line)
      tokenLines.+=((lineNumber, tokens))
      lineNumber += 1
    }

    tokenLines.filter(line => line._2.nonEmpty && line._2.exists(p => !p.isInstanceOf[EOLToken])).toList
  }

  private def parseLine(line: String): List[Token] = {
    val tokens: ListBuffer[Token] = ListBuffer.empty
    var pos: Int = 0
    var currentToken: String = ""

    while (pos < line.length) {
      line(pos) match {
        case ' ' | '\t' => // Scanning of next token complete -> parse token
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

    tokens.filter((p: Token) => p != null).toList
  }

  private def parseToken(token: String): Token = {
    if (token == null || token.isEmpty) return null
    token.toLowerCase match {
      case "halt" =>  new HALT
      case "pushc" => new PUSHC
      case "add" =>   new ADD
      case "sub" =>   new SUB
      case "mul" =>   new MUL
      case "div" =>   new DIV
      case "mod" =>   new MOD
      case "rdint" => new RDINT
      case "wrint" => new WRINT
      case "rdchr" => new RDCHR
      case "wrchr" => new WRCHR
      case "pushg" => new PUSHG
      case "popg" =>  new POPG
      case "asf" =>   new ASF
      case "rsf" =>   new RSF
      case "pushl" => new PUSHL
      case "popl" =>  new POPL
      case "eq" =>    new EQ
      case "ne" =>    new NE
      case "lt" =>    new LT
      case "le" =>    new LE
      case "gt" =>    new GT
      case "ge" =>    new GE
      case "jmp" =>   new JMP
      case "brf" =>   new BRF
      case "brt" =>   new BRT
      case "call" =>  new CALL
      case "ret" =>   new RET
      case "drop" =>  new DROP
      case "pushr" => new PUSHR
      case "popr" =>  new POPR
      case "dup" =>   new DUP
      case "new" =>   new NEW
      case "getf" =>  new GETF
      case "putf" =>  new PUTF
      case "newa" =>  new NEWA
      case "getfa" => new GETFA
      case "putfa" => new PUTFA
      case "getsz" => new GETSZ
      case "pushn" => new PUSHN
      case "refeq" => new REFEQ
      case "refne" => new REFNE

      case _ =>
        if (token.matches(constantNumberMatcher)) NumberToken(token.toInt)
        else if (token.matches(identifierMatcher)) IdentifierToken(token)
        else if (token.matches(identifierMatcher + ':')) LabelToken(token.replace(":", ""))
        else {
          val character: String = token.replace("'", "")
          if (character.length == 1)
            return CharacterToken(character)
          else if (character.length == 2 && character.head == '\\') {
            var c: Char = ' '
            character(1) match {
              case 'n' =>
                c = '\n'
              case 't' =>
                c = '\t'
              case _ =>
            }
            if (c != ' ') return CharacterToken(c.toString)
            else return new SyntaxErrorToken
          }


          new SyntaxErrorToken
        }
    }
  }
}
