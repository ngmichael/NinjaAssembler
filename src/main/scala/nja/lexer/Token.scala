package nja.lexer

// General definition of Token
class Token()

// Definition of instruction token
class InstructionToken extends Token

case class HALT() extends InstructionToken
case class PUSHC(const: Int) extends InstructionToken

case class ADD() extends InstructionToken
case class SUB() extends InstructionToken
case class MUL() extends InstructionToken
case class DIV() extends InstructionToken
case class MOD() extends InstructionToken

case class RDINT() extends InstructionToken
case class WRINT() extends InstructionToken
case class RDCHR() extends InstructionToken
case class WRCHR() extends InstructionToken

case class PUSHG(index: Int) extends InstructionToken
case class POPG(index: Int) extends InstructionToken

case class ASF(size: Int) extends InstructionToken
case class RSF() extends InstructionToken
case class PUSHL(offset: Int) extends InstructionToken
case class POPL(offset: Int) extends InstructionToken

case class EQ() extends InstructionToken
case class NE() extends InstructionToken
case class LT() extends InstructionToken
case class LE() extends InstructionToken
case class GT() extends InstructionToken
case class GE() extends InstructionToken

case class JMP(target: Int) extends InstructionToken
case class BRF(target: Int) extends InstructionToken
case class BRT(target: Int) extends InstructionToken

case class CALL(target: Int) extends InstructionToken
case class RET() extends InstructionToken
case class DROP(amount: Int) extends InstructionToken
case class PUSHR() extends InstructionToken
case class POPR() extends InstructionToken

case class DUP() extends InstructionToken

case class NEW(fields: Int) extends InstructionToken
case class GETF(offset: Int) extends InstructionToken
case class PUTF(offset: Int) extends InstructionToken

case class NEWA(fields: Int) extends InstructionToken
case class GETFA(offset: Int) extends InstructionToken
case class PUTFA(offset: Int) extends InstructionToken

case class GETSZ() extends InstructionToken

case class PUSHN() extends InstructionToken
case class REFEQ() extends InstructionToken
case class REFNE() extends InstructionToken

// Misc Tokens
case class IdentifierToken(value: String) extends Token
case class LabelToken(label: String) extends Token
case class EOLToken() extends Token
case class SyntaxErrorToken() extends Token

