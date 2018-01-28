package main.scala.parser

class Instruction {}

case class Halt() extends Instruction
case class Pushc(constant: Int) extends Instruction

case class Add() extends Instruction
case class Sub() extends Instruction
case class Mul() extends Instruction
case class Div() extends Instruction
case class Mod() extends Instruction

case class Rdint() extends Instruction
case class Wrint() extends Instruction
case class Rdchr() extends Instruction
case class Wrchr() extends Instruction

case class Pushg(index: Int) extends Instruction
case class Popg(index: Int) extends Instruction

case class Asf(size: Int) extends Instruction
case class Rsf() extends Instruction
case class Pushl(offset: Int) extends Instruction
case class Popl(offset: Int) extends Instruction

case class Eq() extends Instruction
case class Ne() extends Instruction
case class Lt() extends Instruction
case class Le() extends Instruction
case class Gt() extends Instruction
case class Ge() extends Instruction

case class Jmp(target: Int) extends Instruction
case class Brf(target: Int) extends Instruction
case class Brt(target: Int) extends Instruction

case class Call(target: Int) extends Instruction
case class Ret() extends Instruction
case class Drop(amount: Int) extends Instruction
case class Pushr() extends Instruction
case class Popr() extends Instruction

case class Dup() extends Instruction

case class New(size: Int) extends Instruction
case class Getf(offset: Int) extends Instruction
case class Putf(offset: Int) extends Instruction

case class Newa() extends Instruction
case class Getfa() extends Instruction
case class Putfa() extends Instruction

case class Getsz() extends Instruction

case class Pushn() extends Instruction
case class Refeq() extends Instruction
case class Refne() extends Instruction

case class Noop(line: Int) extends Instruction
