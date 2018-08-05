package com.catran.trading.util

import java.io._

import org.apache.log4j.Logger

object ResourceFileHandler {

  private val logger = Logger.getLogger(getClass)

  def read(sourceFilePath: String): String = {
    val stream : InputStream = getClass.getResourceAsStream(sourceFilePath)
    val lines: Iterator[String] = scala.io.Source.fromInputStream( stream ).getLines
    lines.mkString("")
  }

  def write(sourceFilePath: String, msg: String): Unit = {
    val outputStream= new FileOutputStream(s"src/main/resources/${sourceFilePath}",true)
    outputStream.write(msg.getBytes())
  }
}