package com.cain.fhp.util.general

import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive0, Route}

trait CorsSupport {
  lazy val allowedOrigin = `Access-Control-Allow-Origin`.*

  final val USER_TABLE = "DP_UserLogin"
  final val SERVICE_TABLE = "DP_ServiceAuth"

  //this directive adds access control headers to normal responses
  private def addAccessControlHeaders(): Directive0 = {
    respondWithHeaders(
      allowedOrigin,
      `Access-Control-Allow-Credentials`(true),
      `Access-Control-Allow-Headers`("Content-Type", "X-Requested-With","Origin", "Accept", "Accept-Encoding", "Accept-Language", "Host",
        "Referer", "User-Agent", "Cache-Control", "If-Modified-Since", "Content-Range", "Range", "Keep-Alive", "User-Agent", "Pragma",
        "Authorization", "hsaAuth", "internalAuth")
    )
  }

  //this handles preflight OPTIONS requests.
  private def preflightRequestHandler: Route = options {
    complete(HttpResponse(StatusCodes.OK).withHeaders(`Access-Control-Allow-Methods`(OPTIONS, POST, PUT, GET, DELETE)))
  }

  def corsHandler(r: Route): Route = addAccessControlHeaders() {
    preflightRequestHandler ~ r
  }


}
