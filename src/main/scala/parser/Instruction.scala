package main.scala.parser

class Instruction {}

case class HALT() extends Instruction
case class PUSHC(constant: Int) extends Instruction

case class ADD() extends Instruction
case class SUB() extends Instruction
case class MUL() extends Instruction
case class DIV() extends Instruction
case class MOD() extends Instruction

case class RDINT() extends Instruction
case class WRINT() extends Instruction
case class RDCHR() extends Instruction
case class WRCHR() extends Instruction

case class PUSHG(index: Int) extends Instruction
case class POPG(index: Int) extends Instruction

case class ASF(size: Int) extends Instruction
case class RSF() extends Instruction
case class PUSHL(offset: Int) extends Instruction
case class POPL(offset: Int) extends Instruction

case class EQ() extends Instruction
case class NE() extends Instruction
case class LT() extends Instruction
case class LE() extends Instruction
case class GT() extends Instruction
case class GE() extends Instruction

case class JMP(target: Int) extends Instruction
case class BRF(target: Int) extends Instruction
case class BRT(target: Int) extends Instruction

case class CALL(target: Int) extends Instruction
case class RET() extends Instruction
case class DROP(amount: Int) extends Instruction
case class PUSHR() extends Instruction
case class POPR() extends Instruction

case class DUP() extends Instruction

case class NEW(size: Int) extends Instruction
case class GETF(offset: Int) extends Instruction
case class PUTF(offset: Int) extends Instruction

case class NEWA(size: Int) extends Instruction
case class GETFA(offset: Int) extends Instruction
case class PUTFA(offset: Int) extends Instruction

case class GETSZ() extends Instruction

case class PUSHN() extends Instruction
case class REFEQ() extends Instruction
case class REFNE() extends Instruction

case class NoOp(line: Int, col: Int) extends Instruction
