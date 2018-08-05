package com.catran.trading.aggregator

import com.catran.trading.dao.TeakDaoMock
import com.catran.trading.options.ApplicationOptions
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class TeakAggregatorSpec extends FlatSpec with GivenWhenThen with Matchers {

  val options = ApplicationOptions()

  val teakDao = new TeakDaoMock
  "TeakAggregator.createCandles" should "return list of candles that contains 4 types of tickers and have length 42" in {
    Given("Receive teaks")
    val teaks = teakDao.getTeaks(1533473224539L - 600000L, 1533473224539L)

    When("Candles will be")
    val candles = TeakAggregator.createCandles(teaks)

    Then("The length of candles should be 42")
    candles.length shouldEqual 42
    candles.groupBy(_.ticker).size shouldEqual 4
  }

  "TeakAggregator.createCandles" should "return empty list if list of teaks is empty" in {
    Given("the trying of make candles from empty list")
    val candles = TeakAggregator.createCandles(List.empty)

    Then("The length of list of candles should be 0")
    candles.length shouldBe 0
  }

  "TeakAggregator.groupByMinute" should "return map with size 10" in {
    Given("teaks")
    val teaks = teakDao.getTeaks(1533473224539L - 600000L, 1533473224539L)

    When("groupByMinuteTeaks")
    val grouped = TeakAggregator.groupByMinute(teaks.filter(_.ticker == "AAPL"))

    Then("grouped should have size 10")
    grouped.size shouldBe 10
  }

  "Returned timeRange from TeakAggregator" should "have size 11" in {
    Given("timeFrom timeTo")
    val timeFrom = 1533473224539L - 600000L
    val timeTo = 1533473224539L

    Then("timeRange should have size 11")
    TeakAggregator.getMinuteTimeRange(timeFrom, timeTo).length shouldBe 11
  }

  "Returned timeRange from TeakAggregator" should "have size 1" in {
    Given("timeFrom timeTo")
    val timeFrom = 0
    val timeTo = 0

    Then("timeRange should have size 1")
    TeakAggregator.getMinuteTimeRange(timeFrom, timeTo).length shouldBe 1
  }
}
