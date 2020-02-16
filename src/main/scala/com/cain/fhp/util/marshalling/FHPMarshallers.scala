package com.cain.fhp.util.marshalling

import akka.http.scaladsl.common.{EntityStreamingSupport, JsonEntityStreamingSupport}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, deserializationError}

import scala.util.{Failure, Success, Try}

trait FHPMarshallers extends DefaultJsonProtocol with SprayJsonSupport {
  // TODO: Fill with implicits jsonFormat< # args >(CaseClasses) for each table thrown through API
}
