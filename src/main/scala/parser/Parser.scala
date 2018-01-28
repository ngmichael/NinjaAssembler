package main.scala.parser

import main.scala.lexer
import main.scala.lexer._

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
      return Noop(lineNumber)
    }

    line.head match {
      case _: HALT =>
        if (noImmediate(line)) return Halt()
        Noop(lineNumber)
      case _: PUSHC =>
        if (numberImmediate(line)) return Pushc(line(1).asInstanceOf[NumberToken].value)
        else if (charImmediate(line)) {
          val const: Int = convertCharImmediate(line(1).asInstanceOf[CharacterToken])
          return Pushc(const)
        }
        Noop(lineNumber)
      case _: ADD =>
        if (noImmediate(line)) return Add()
        Noop(lineNumber)
      case _: SUB =>
        if (noImmediate(line)) return Sub()
        Noop(lineNumber)
      case _: MUL =>
        if (noImmediate(line)) return Mul()
        Noop(lineNumber)
      case _: DIV =>
        if (noImmediate(line)) return Div()
        Noop(lineNumber)
      case _: MOD =>
        if (noImmediate(line)) return Mod()
        Noop(lineNumber)
      case _: RDINT =>
        if (noImmediate(line)) return Rdint()
        Noop(lineNumber)
      case _: WRINT =>
        if (noImmediate(line)) return Wrint()
        Noop(lineNumber)
      case _: RDCHR =>
        if (noImmediate(line)) return Rdchr()
        Noop(lineNumber)
      case _: WRCHR =>
        if (noImmediate(line)) return Wrchr()
        Noop(lineNumber)
      case _: PUSHG =>
        if (numberImmediate(line)) return Pushg(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: POPG =>
        if (numberImmediate(line)) return Popg(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: ASF =>
        if (numberImmediate(line)) return Asf(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: RSF =>
        if (noImmediate(line)) return Rsf()
        Noop(lineNumber)
      case _: PUSHL =>
        if (numberImmediate(line)) return Pushl(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: POPL =>
        if (numberImmediate(line)) return Popl(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: EQ =>
        if (noImmediate(line)) return Eq()
        Noop(lineNumber)
      case _: NE =>
        if (noImmediate(line)) return Ne()
        Noop(lineNumber)
      case _: LT =>
        if (noImmediate(line)) return Lt()
        Noop(lineNumber)
      case _: LE =>
        if (noImmediate(line)) return Le()
        Noop(lineNumber)
      case _: GT =>
        if (noImmediate(line)) return Gt()
        Noop(lineNumber)
      case _: GE =>
        if (noImmediate(line)) return Ge()
        Noop(lineNumber)
      case _: JMP =>
        if (numberImmediate(line)) return Jmp(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: BRF =>
        if (numberImmediate(line)) return Brf(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: BRT =>
        if (numberImmediate(line)) return Brt(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: CALL =>
        if (numberImmediate(line)) return Call(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: RET =>
        if (noImmediate(line)) return Ret()
        Noop(lineNumber)
      case _: DROP =>
        if (numberImmediate(line)) return Drop(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: DUP =>
        if (noImmediate(line)) return Dup()
        Noop(lineNumber)
      case _: NEW =>
        if (numberImmediate(line)) return New(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: GETF =>
        if (numberImmediate(line)) return Getf(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: PUTF =>
        if (numberImmediate(line)) return Putf(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: NEWA =>
        if (numberImmediate(line)) return Newa(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: GETFA =>
        if (numberImmediate(line)) return Getfa(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: PUTFA =>
        if (numberImmediate(line)) return Putfa(line(1).asInstanceOf[NumberToken].value)
        Noop(lineNumber)
      case _: GETSZ =>
        if (noImmediate(line)) return Getsz()
        Noop(lineNumber)
      case _: PUSHN =>
        if (noImmediate(line)) return Pushn()
        Noop(lineNumber)
      case _: REFEQ =>
        if (noImmediate(line)) return Refeq()
        Noop(lineNumber)
      case _: REFNE =>
        if (noImmediate(line)) return Refne()
        Noop(lineNumber)
      case _: Any =>
        Noop(lineNumber)
    }
  }

  private def noImmediate(line: List[Token]): Boolean = line.length == 2 && line(1).isInstanceOf[EOLToken]

  private def numberImmediate(line: List[Token]): Boolean = line.length == 3 && line(1).isInstanceOf[NumberToken] && line(2).isInstanceOf[EOLToken]

  private def charImmediate(line: List[Token]): Boolean = line.length == 3 && line(1).isInstanceOf[CharacterToken] && line(2).isInstanceOf[EOLToken]

  private def convertCharImmediate(token: CharacterToken): Int = {
    0
  }
}
