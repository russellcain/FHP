package com.cain.unit

import com.cain.trades.Scraper

import org.scalatest.FreeSpec

class ScraperSpec extends FreeSpec {
  val testScraper: Scraper.type = Scraper

  "Scraper testing" - {
    "lets get our first page" in {
      testScraper.starterList
    }

    "lets see if our list for all pages rendered" in {
      testScraper.pageExtensions.slice(0, 3).foreach(println)
      println("...")
      testScraper.pageExtensions.takeRight(3).foreach(println)
    }

    "and lets get all results for all pages" in {
      testScraper.parallelPageRetrieval(testScraper.pageExtensions)
    }

  }
}