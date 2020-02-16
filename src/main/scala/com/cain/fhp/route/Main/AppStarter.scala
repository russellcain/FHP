package com.cain.fhp.route.Main

import akka.NotUsed
import akka.actor.ActorRef
import akka.http.scaladsl.Http
import com.cain.fhp.util.marshalling.AppStarterMarshaller

import scala.concurrent.Future
//import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
//import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.routing.RoundRobinPool
import akka.stream.scaladsl.Source
//import com.cain.fhp.persistence.{Persistence, PlayerPersistence, UserPersistence}
import com.cain.fhp.persistence.application.Database
//import com.cain.fhp.util.AkkaService
//import com.cain.fhp.util.general.{ConfigHelper, CorsSupport}
import com.typesafe.scalalogging.LazyLogging
//import spray.json._
import com.cain.fhp.persistence.{Persistence, PlayerPersistence, UserPersistence}
import com.cain.fhp.util.general.{ConfigHelper, CorsSupport}
import com.cain.fhp.util.AkkaService
import com.cain.fhp.persistence.Tables._

//import scala.util.{Failure, Success, Try}

object AppStarter extends App with AkkaService with Persistence with CorsSupport with LazyLogging with AppStarterMarshaller {

  private val RoundRobinNumber = ConfigHelper.getIntValue("actor.roundRobin")

  val databaseService: ActorRef = system.actorOf(RoundRobinPool(RoundRobinNumber).props(Database.props), "databaseService")

  val httpMethods = get | put | post | delete | patch

  val route: Route = {
    pathPrefix("api") {
      concat(
        pathPrefix("player") {
          concat(
            path(LongNumber) { id => {
              get {
                println(s"pulling in stats for player id: ${id}")
                val resp = PlayerPersistence.readSingle(PlayerPersistence.byId(id))
                val finalResp = resp.map{
                  case Some(retrievedRow) => {
                    println(s"going to send back $retrievedRow")
                    Future(retrievedRow)
                  }
                  case None => Future(PlayersRow(playerPkid = 1, name = "Fake Player", number = 24, teamId = 4, position = "G"))
                }.mapTo[Future[PlayersRow]]
                complete(finalResp)
              }
            }}
          )
        },
        pathPrefix("user") {
          concat(
            path(LongNumber) { id => {
              get {
                println(s"pulling in stats for user id: ${id}")
                val resp: Source[UsersRow, NotUsed] = streaming(UserPersistence.byId(id))
                complete(s"Here is your data for user id: ${id} \n\t--TODO--")            
              }
            }}
          )
        }
      )
    }
  }

    private val address = ConfigHelper.getStringValue("application.addressString")
    private val port = ConfigHelper.getIntValue("application.portInt")
    val bindingFuture = Http().bindAndHandle(corsHandler(route), "0.0.0.0", port) // can still call locally using localhost:8080 (or whatever port is)
    logger.info(s"Fantasy Hockey Project running on $address : $port")
    println(s"Fantasy Hockey Project running on $address : $port")
  }