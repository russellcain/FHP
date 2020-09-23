package com.cain.unit

import com.cain.trades.Scraper

import org.scalatest.FreeSpec

class ScraperSpec extends FreeSpec {
  val testScraper: Scraper.type = Scraper

  "Scraper testing" - {
    "lets get our first page" in {
//      for( row <- testScraper.starterList) println(s"\n- Date: ${row.date}\tTeam: ${row.team}\n\tEvent: ${row.record.stripSuffix(row.date).stripPrefix(row.date).strip()}")
    }

    "lets see if our list for all pages rendered" in {
//      testScraper.pageExtensions.foreach(println)
    }

    "and lets get all results for all pages" in {
      testScraper.fuckShitUPPP(testScraper.pageExtensions)
    }

  }
}