package com.cain.fhp.persistence.application

//import akka.NotUsed
import akka.actor.{Actor, ActorRef, Props}
//import akka.stream.scaladsl.Source
import com.cain.fhp.route.Main.AppStarter.{ec, materializer}
import com.cain.fhp.persistence.Tables.PlayersRow
import com.cain.fhp.persistence._
import Database.commitData
import com.cain.fhp.persistence.PlayerPersistence
import com.typesafe.scalalogging.LazyLogging

//import scala.concurrent.Await
//import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}


// database actor properties/configuration:
object Database {
  def props: Props = Props[Database]
  case class commitData(persistedData: AnyRef)
}

final case class GetRequestException(private val message: String = "",
                                     private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

trait DatabaseCall {
  def pushDB(dataRow: AnyRef): Unit
}


class Database extends Actor with LazyLogging with DatabaseCall {
  // actor who/which will handle all of our DBIO calls to free up threads

  def receive: Receive = {
    case commitData(passedData) => pushDB(passedData)
    case _ => println("Sorry, we don't process that sorta request here")
  }


  def pushDB(newRow: AnyRef): Unit = {
    // Persist by the type of the passed data:
    newRow match {
      case _: PlayersRow => PlayerPersistence.single(PlayerPersistence.add(newRow.asInstanceOf[PlayersRow])).onComplete({
        case Success(x) =>
          println("added a new player")
        case Failure(exception) =>
          println("Uhoh, couldn't add player", exception)
      })
    }
  }

}
