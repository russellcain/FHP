package com.cain.trades

import com.typesafe.scalalogging.LazyLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import collection.JavaConverters._
import scala.collection.mutable
import scala.collection.parallel.{ForkJoinTaskSupport, ParSeq}

object Scraper extends LazyLogging {

  case class TransactionRow(team: String, record: String, date: String)
  case class PageRequest(pageMin: Int = 0, pageMax: Int = 1, parellism: Int = 10) {
    def apply(pageMin: Int, pageMax: Int, parellism: Int): PageRequest ={
      val boundedMax = Math.min(pageMax, getPageMax)
      PageRequest(pageMin, pageMax, parellism)
    }
  }

  def getToTransactionRows(url: String): List[TransactionRow] = {
    val transactionRowElements: mutable.Buffer[Element] = Jsoup
      .connect(url).get()
      .getElementsByClass("table table-hover table-striped") // go to the transaction table
      .select("tr") // select all the individual rows which are children
      .asScala // turn this into a scala collection so we can map over them

    /*
    <tr>
       <td>
          <a href="/team/edmonton-oilers"><img class="team-sm" title="Edmonton Oilers" alt="Edmonton Oilers logo" src="/sites/default/files/logos/edmonton-oilers_0.svg" typeof="foaf:Image"> </a>
        </td>
       <td>
          <div class="only-mobile">
            2020-09-21
          </div>
          <a href="/team/"> </a> assigned LW <a href="/player/tyler-benson">Tyler Benson</a> to GCK Lions (Sweden).
        </td>
       <td class="text-nowrap no-mobile">
          2020-09-21
        </td>
    </tr>
     */


    transactionRowElements.map(el => {
      val date = el.getElementsByClass("only-mobile").text // because the "only-mobile" divs contain this
      TransactionRow(
        team = el.getElementsByClass("team-sm").attr("title"), // get the <img> by class and take the title attribute
        record = el.text.replace(date, ""), // this is the body of the table. we will need to strip out the date before and after it to prettify it // remove the date as it is in other variable
        date = date
      )
    }).toList
  }

  def getPageMax: Int = {
    Jsoup.connect(baseURL)
      .get() // pull in HTML
      .getElementsByClass("pager__item pager__item--last") // go down to the page selection at the bottom (last one)
      .select("a") // get the main tag
      .attr("href") // pull in the href attribute (is `?page={THE_NUMBER_WE_WANT)`)
      .takeRight(3) // grab the last three characters (guess it could be two, kinda risky)
      .toInt // trust that this can marshall to an int (would fail if we pulled in a real char)
  }

  val baseURL: String = "https://puckpedia.com/transactions"
  val upperPageCount: Int = {
    val pageCount: Int = 1000 / 50 // getPageMax
    println(s"upperPageCount: ${pageCount}")
    pageCount
  }
  val pageExtensions: List[String] = (for (i <- 1 to upperPageCount) yield baseURL + s"?page=$i").toList

  def getPageExtensions(startPage: Int, endPage: Int): List[String] = {
    (for (i <- startPage to endPage) yield baseURL + s"?page=$i").toList
  }
//  val starterList: List[TransactionRow] = getToTransactionRows(baseURL)

  def parallelPageRetrieval(urlList: List[String], parallelismCount: Int = 150): ParSeq[TransactionRow] = {
    val parMap: ParSeq[String] = urlList.par // make this a parallel sequence
    parMap.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(parallelismCount)) // which can be operated upon with this number of threads
    parMap.flatMap(url => { // flat map it so we end up with a mega (~50 per page) list of TransactionRows
      val pageRows: List[TransactionRow] = getToTransactionRows(url)
      println(s"Consumed ${pageRows.length} rows between ${pageRows.last.date} and ${pageRows.head.date}") // logging
      pageRows
    })
  }

  def getApiRequest(req: PageRequest): List[TransactionRow] = {
    parallelPageRetrieval(getPageExtensions(req.pageMin, req.pageMax), req.parellism).toList
  }
}
