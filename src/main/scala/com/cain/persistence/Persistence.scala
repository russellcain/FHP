package com.cain.persistence

import akka.stream.ThrottleMode
import akka.{Done, NotUsed}
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.{Sink, Source}
import com.cain.route.Main.AppStarter._
import slick.jdbc.{ResultSetConcurrency, ResultSetType}
import slick.sql.FixedSqlStreamingAction

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
/**
  * The root Persistence component which provides information
  * how a driver has to look -> Just a JDBC Profile
  */
trait Persistence {

  import session.profile.api._

  lazy val FETCH_PAGE_SIZE = 100

  def modifyAsSource[T, R](source: Source[T, NotUsed], sql: T => DBIO[R]): Future[Done] = {
    source
      .via(Slick.flowWithPassThrough(PARALLELISM_FACTOR, sql))
      .runWith(Sink.ignore)
  }

  def modifyWithResponseSource[T, U, V](source: Source[T, NotUsed], sql: T => DBIO[U], handleResponse: U => V, handleFailure: Option[Exception => V] = None):
  Source[V, NotUsed] = {
    source
      .via(Slick.flowWithPassThrough(PARALLELISM_FACTOR, sql))
      .map(resp => handleResponse(resp))
      .recover {
        case e: Exception => handleFailure match {
          case Some(handle) => handle(e)
          case None => throw e
        }
      }
  }

  /**
    * Adds proper postgres parameters to minimize the amount of data brought into memory from the database
    * @param q: Slick SQL DSL query to run
    * @tparam T: Result type of query
    * @return Slick query as streamed, transactional result
    */
  def streamingSQL[T](q: FixedSqlStreamingAction[Seq[T], T, Effect.Read]): StreamingDBIO[Seq[T], T] = {
    q
      .withStatementParameters(
        rsType = ResultSetType.ForwardOnly,
        rsConcurrency = ResultSetConcurrency.ReadOnly,
        fetchSize = FETCH_PAGE_SIZE
      )
      .transactionally
  }

  def streaming[T, U](q: Query[T, U, Seq]): Source[U, NotUsed] = {
    Slick.source(streamingSQL(q.result))
  }

  def reactiveStreaming[T, U, V, W](q: V => Query[T, U, Seq], initialState: V, updateState: U => V, mapResult: U => W):
  Source[W, NotUsed] = {
    Source
      .unfoldAsync(initialState) { newState =>
        read(q(newState)).map { recs =>
          Some(
            recs.lastOption match {
              case Some(row) => updateState(row)
              case None => newState
            }
            ,
            recs)
        }
      }
      .throttle(PARALLELISM_FACTOR, 1 second, 1, ThrottleMode.shaping)
      .flatMapConcat { recs =>
        Source.fromIterator(() => recs.map(mapResult).iterator)
      }
  }

  def single[T](q: DBIO[T]): Future[T] = {
    session.db.run(q)
  }

  def read[T, U](q: Query[T, U, Seq]): Future[Seq[U]] = {
    session.db.run(q.result)
  }

  def applyRecoverOrDefault[T](handler: Option[PartialFunction[Throwable, T]]): PartialFunction[Throwable, T] = {
    handler match {
      case Some(fn) => fn
      case None => {
        case e: Exception => println(e); throw e
      }
    }
  }

  /**
    * Uses configured database session to run a slick query. Will return an option for the first
    * matching element. It is recommended that only queries using .take(1) are run with this function
    *
    * @param query: DB query composed in Slick (see Slick docs)
    * @return Optional first result
    */
  def readSingle[T, U](query: Query[T, U, Seq], recover: Option[PartialFunction[Throwable, Option[U]]] = None): Future[Option[U]] = {
    session.db.run(query.result.headOption)
      .recover(applyRecoverOrDefault(recover))
  }
}