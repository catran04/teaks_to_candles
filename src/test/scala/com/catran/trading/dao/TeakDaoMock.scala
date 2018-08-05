package com.catran.trading.dao

import com.catran.trading.dao.teakDao.TeakDao
import com.catran.trading.model.Teak
import com.catran.trading.util.ResourceFileHandler
import org.apache.log4j.Logger
import org.json4s.native.Serialization
import org.json4s.{NoTypeHints, native}

class TeakDaoMock extends TeakDao {
  private val logger = Logger.getLogger(getClass)

  override def getTeaks(from: Long, to: Long): List[Teak] = {
    require(from <= to, "'From' should be less or equal 'to'")
    logger.info("Reading teaks from a storage...")
    val strTeaks = ResourceFileHandler.read("/teaks.json")
    implicit val formats = native.Serialization.formats(NoTypeHints)
    val teaks = Serialization.read[List[Teak]](strTeaks)
    val filteredTeaks = teaks.filter(teak => teak.timestamp >= from && teak.timestamp <= to)
    logger.info(s"Teaks was received from: ${from}, to: ${to}")
    filteredTeaks
  }

  override def setTeak(teak: Teak): Unit = logger.info(s"Setting teak: ${teak}")
}
