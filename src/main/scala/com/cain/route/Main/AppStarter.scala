package com.cain.route.Main

import akka.NotUsed
import akka.actor.ActorRef
import akka.http.scaladsl.Http
import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.routing.RoundRobinPool
import akka.stream.scaladsl.Source
import com.typesafe.scalalogging.LazyLogging
import spray.json._
import com.cain.persistence.application.Database
import com.cain.persistence.{UserPersistence, PlayerPersistence, Persistence}
import com.cain.util.general.{ConfigHelper, CorsSupport}
import com.cain.util.AkkaService

import com.cain.persistence.Tables._

import scala.util.{Failure, Success, Try}

object AppStarter extends App with AkkaService with Persistence with CorsSupport with LazyLogging {

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
                val resp: Source[PlayersRow, NotUsed] = streaming(PlayerPersistence.byId(id))
                complete(s"Here is your data for player #${id}: \n\t${resp}")
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