package com.cain.trades

import com.typesafe.scalalogging.LazyLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import collection.JavaConverters._
import scala.collection.mutable
import scala.collection.parallel.{ForkJoinTaskSupport, ParSeq}
import scala.collection.parallel.immutable.ParMap

case class TransactionRow(team: String, record: String, date: String)

object Scraper extends LazyLogging {

  def getToTransactionRows(url: String): List[TransactionRow] = {
    val doc = Jsoup.connect(url).get()
    val transactionRowElements: mutable.Buffer[Element] = doc.getElementsByClass("table table-hover table-striped").select("tr").asScala
    // table is a Buffer comprised of
    val sampleRow: String =
      """<tr>
        | <td><a href="/team/edmonton-oilers"><img class="team-sm" title="Edmonton Oilers" alt="Edmonton Oilers logo" src="/sites/default/files/logos/edmonton-oilers_0.svg" typeof="foaf:Image"> </a></td>
        | <td>
        |  <div class="only-mobile">
        |   2020-09-21
        |  </div> <a href="/team/"> </a> assigned LW <a href="/player/tyler-benson">Tyler Benson</a> to GCK Lions (Sweden). </td>
        | <td class="text-nowrap no-mobile">2020-09-21</td>
        |</tr>""".stripMargin


    transactionRowElements.map(el => {
      TransactionRow(
        team = el.getElementsByClass("team-sm").attr("title"), // get the <img> by class and take the title attribute
        record = el.text, // this is the body of the table. we will need to strip out the date before and after it to prettify it
        date = el.getElementsByClass("only-mobile").text // because the "only-mobile" divs contain this
      )
    }).toList
  }

  val baseURL: String = "https://puckpedia.com/transactions"
  val upperPageCount: Int = 643 // todo: pull this from the main page selection url redirect
  val starterList = getToTransactionRows(baseURL)


  val pageExtensions: List[String] = (for (i <- 1 until upperPageCount) yield baseURL + s"?page=${i}").toList

  def fuckShitUPPP(urlList: List[String]): Unit = {
    val parMap: ParSeq[String] = urlList.par
    parMap.tasksupport = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(100))
    parMap.foreach(url => {
      val pageRows = getToTransactionRows(url)
      println(s"Consumed ${pageRows.length} rows between ${pageRows.last.date} and ${pageRows.head.date}")
    })

  }



}