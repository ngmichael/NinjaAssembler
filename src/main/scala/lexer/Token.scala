package main.scala.lexer

// General definition of Token
class Token()

// Definition of instruction token
class InstructionToken extends Token

case class HALT() extends InstructionToken
case class PUSHC() extends InstructionToken

case class ADD() extends InstructionToken
case class SUB() extends InstructionToken
case class MUL() extends InstructionToken
case class DIV() extends InstructionToken
case class MOD() extends InstructionToken

case class RDINT() extends InstructionToken
case class WRINT() extends InstructionToken
case class RDCHR() extends InstructionToken
case class WRCHR() extends InstructionToken

case class PUSHG() extends InstructionToken
case class POPG() extends InstructionToken

case class ASF() extends InstructionToken
case class RSF() extends InstructionToken
case class PUSHL() extends InstructionToken
case class POPL() extends InstructionToken

case class EQ() extends InstructionToken
case class NE() extends InstructionToken
case class LT() extends InstructionToken
case class LE() extends InstructionToken
case class GT() extends InstructionToken
case class GE() extends InstructionToken

case class JMP() extends InstructionToken
case class BRF() extends InstructionToken
case class BRT() extends InstructionToken

case class CALL() extends InstructionToken
case class RET() extends InstructionToken
case class DROP() extends InstructionToken
case class PUSHR() extends InstructionToken
case class POPR() extends InstructionToken

case class DUP() extends InstructionToken

case class NEW() extends InstructionToken
case class GETF() extends InstructionToken
case class PUTF() extends InstructionToken

case class NEWA() extends InstructionToken
case class GETFA() extends InstructionToken
case class PUTFA() extends InstructionToken

case class GETSZ() extends InstructionToken

case class PUSHN() extends InstructionToken
case class REFEQ() extends InstructionToken
case class REFNE() extends InstructionToken

// Misc Tokens
case class NumberToken(value: Int) extends Token
case class CharacterToken(value: Char) extends Token
case class IdentifierToken(value: String) extends Token
case class LabelToken(label: String) extends Token
case class EOLToken() extends Token
case class SyntaxErrorToken() extends Token

