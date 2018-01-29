package main.scala

import main.scala.parser._

import scala.collection.mutable.ListBuffer

object CodeGenerator {

  private var sdaVarCount: Int = 0

  def generate(instructions: List[Instruction]): List[Byte] = {
    val identifier: List[Byte] = List[Byte](0x4E.toByte, 0x4A.toByte, 0x42.toByte, 0x46.toByte) // NJBF
    val versionNumber: List[Byte] = List[Byte](0x0, 0x0, 0x0, 0x8).reverse

    val code: ListBuffer[Byte] = ListBuffer.empty
    for (instr: Instruction <- instructions) {
      code.++=(compileInstruction(instr))
    }
    val sda: List[Byte] = List[Byte](
      ((sdaVarCount & 0xFF000000)>>>24).toByte,
      ((sdaVarCount & 0x00FF00000)>>>16).toByte,
      ((sdaVarCount & 0x0000FF00)>>>8).toByte,
      (sdaVarCount & 0x000000FF).toByte).reverse
    val instrCount: Int = instructions.length
    val instructionCount: List[Byte] = List[Byte](
      ((instrCount & 0xFF000000)>>>24).toByte,
      ((instrCount & 0x00FF00000)>>>16).toByte,
      ((instrCount & 0x0000FF00)>>>8).toByte,
      (instrCount & 0x000000FF).toByte).reverse

    identifier ::: versionNumber ::: instructionCount ::: sda ::: code.toList
  }

  private def compileInstruction(instruction: Instruction): List[Byte] = {
    instruction match {
      case _: Halt => List[Byte](0x0.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: Pushc =>
        val im: List[Byte] = immediateFromInt(i.constant)
        List[Byte](0x1.toByte, im.head, im(1), im(2)).reverse
      case _: Add => List[Byte](0x2.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Sub => List[Byte](0x3.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Mul => List[Byte](0x4.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Div => List[Byte](0x5.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Mod => List[Byte](0x6.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Rdint => List[Byte](0x7.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Wrint => List[Byte](0x8.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Rdchr => List[Byte](0x9.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Wrchr => List[Byte](0xA.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: Pushg =>
        sdaVarCount = scala.math.max(sdaVarCount, i.index)
        val im: List[Byte] = immediateFromInt(i.index)
        List[Byte](0xB.toByte, im.head, im(1), im(2)).reverse
      case i: Popg =>
        sdaVarCount = scala.math.max(sdaVarCount, i.index)
        val im: List[Byte] = immediateFromInt(i.index)
        List[Byte](0xC.toByte, im.head, im(1), im(2)).reverse
      case i: Asf =>
        val im: List[Byte] = immediateFromInt(i.size)
        List[Byte](0xD.toByte, im.head, im(1), im(2))
      case _: Rsf => List[Byte](0xE.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: Pushl =>
        val im: List[Byte] = immediateFromInt(i.offset)
        List[Byte](0xF.toByte, im.head, im(1), im(2)).reverse
      case i: Popl =>
        val im: List[Byte] = immediateFromInt(i.offset)
        List[Byte](0x10.toByte, im.head, im(1), im(2)).reverse
      case _: Eq => List[Byte](0x11.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Ne => List[Byte](0x12.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Lt => List[Byte](0x13.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Le => List[Byte](0x14.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Gt => List[Byte](0x15.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Ge => List[Byte](0x16.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: Jmp =>
        val im: List[Byte] = immediateFromInt(i.target)
        List[Byte](0x17.toByte, im.head, im(1), im(2)).reverse
      case i: Brf =>
        val im: List[Byte] = immediateFromInt(i.target)
        List[Byte](0x18.toByte, im.head, im(1), im(2)).reverse
      case i: Brt =>
        val im: List[Byte] = immediateFromInt(i.target)
        List[Byte](0x19.toByte, im.head, im(1), im(2)).reverse
      case i: Call =>
        val im: List[Byte] = immediateFromInt(i.target)
        List[Byte](0x1A.toByte, im.head, im(1), im(2)).reverse
      case _: Ret => List[Byte](0x1B.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: Drop =>
        val im: List[Byte] = immediateFromInt(i.amount)
        List[Byte](0x1C.toByte, im.head, im(1), im(2)).reverse
      case _: Pushr => List[Byte](0x1D.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Popr => List[Byte](0x1E.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Dup => List[Byte](0x1F.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case i: New =>
        val im: List[Byte] = immediateFromInt(i.size)
        List[Byte](0x20.toByte, im.head, im(1), im(2)).reverse
      case i: Getf =>
        val im: List[Byte] = immediateFromInt(i.offset)
        List[Byte](0x21.toByte, im.head, im(1), im(2)).reverse
      case i: Putf =>
        val im: List[Byte] = immediateFromInt(i.offset)
        List[Byte](0x22.toByte, im.head, im(1), im(2)).reverse
      case _: Newa => List[Byte](0x23.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Getfa => List[Byte](0x24.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Putfa => List[Byte](0x25.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Getsz => List[Byte](0x26.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Pushn => List[Byte](0x27.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Refeq => List[Byte](0x28.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
      case _: Refne => List[Byte](0x29.toByte, 0x0.toByte, 0x0.toByte, 0x0.toByte).reverse
    }
  }

  private def immediateFromInt(value: Int): List[Byte] = {
    var intermediate: Int = value & 0x00FFFFFF
    if (value < 0) intermediate = intermediate | 0x800000
    val immediate: ListBuffer[Byte] = new ListBuffer[Byte]

    immediate += ((intermediate & 0xFF0000)>>>16).toByte
    immediate += ((intermediate & 0x00FF00)>>>8).toByte
    immediate += (intermediate & 0x0000FF).toByte

    immediate.toList
  }

}
