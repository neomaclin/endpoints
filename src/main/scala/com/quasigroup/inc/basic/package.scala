package com.quasigroup.inc

import io.circe._
import sttp.tapir._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import shapeless.HList
import sttp.model.{QueryParams, StatusCode}


package object basic {
  sealed trait ErrorInfo

  case class NotFound(what: String) extends ErrorInfo
  case class Unauthorized(realm: String) extends ErrorInfo
  case class Unknown(code: Int, msg: String) extends ErrorInfo
  case object NoContent extends ErrorInfo

  // here we are defining an error output, but the same can be done for regular outputs
  val baseEndpoint: Endpoint[Unit, ErrorInfo, Unit, Nothing] = endpoint.errorOut(
    oneOf[ErrorInfo](
      statusMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
      statusMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
      statusMapping(StatusCode.NoContent, emptyOutput.map(_ => NoContent)(_ => ())),
      statusDefaultMapping(jsonBody[Unknown].description("unknown"))
    )
  )
  val origins:EndpointInput[(Int, Option[Int])] =
    query[Int]("from") and query[Option[Int]]("with")

  def pagination(pageNumber:String, pageSize:String): EndpointInput[(Int, Int)] =
    query[Int](name = pageNumber) and query[Int]( name = pageSize)

  object Heartbeat {
    def ping(path: String): Endpoint[Unit, Unit, Unit, Nothing] =
      endpoint.in(path).out(statusCode(StatusCode.Ok))
  }

  object Entrance {

    def indexJson[T:Encoder: Decoder:Schema:Validator]: Endpoint[Unit, Unit, T, Nothing] =
      endpoint.get
        .in("index")
        .out(jsonBody[T]).out(statusCode(StatusCode.Ok))

    val indexHtml: Endpoint[Unit, Unit, String, Nothing] =
      endpoint.get
        .in("index.html")
        .out(htmlBodyUtf8).out(statusCode(StatusCode.Ok))

  }

  object Authentication{

    def signIn[T:Encoder: Decoder:Schema:Validator, R:Encoder: Decoder:Schema:Validator](signInPath:String): Endpoint[(Int, Option[Int], Option[T]), Unit, R, Nothing] =
      endpoint.post
        .in(signInPath)
        .in(origins)
        .in(jsonBody[Option[T]])
        .out(jsonBody[R]).out(statusCode(StatusCode.Ok))

    def signOut[R:Encoder: Decoder:Schema:Validator](signOutPath:String): Endpoint[Unit, Unit, R, Nothing] = endpoint
      .delete
      .in(signOutPath)
      .out(jsonBody[R])
      .out(statusCode(StatusCode.Ok))
  }

  object Content{

    def post[T:Encoder: Decoder:Schema:Validator](path:String): Endpoint[T, Unit, Unit, Nothing] =
      endpoint.post.in(path).in(jsonBody[T])
        .out(statusCode(StatusCode.Created))
        .errorOut(statusCode(StatusCode.Conflict))

    def getCollectionOf[T:Encoder: Decoder:Schema:Validator](path: String): Endpoint[Unit, Unit, List[T], Nothing] =
      endpoint.get.in(path)
        .out(jsonBody[List[T]]).out(statusCode(StatusCode.Ok))
        .errorOut(statusCode(StatusCode.NotFound))

    def getCollectionByParameters[R:Encoder: Decoder:Schema:Validator](path: String): Endpoint[QueryParams, Unit, List[R], Nothing] =
      endpoint.get.in(path).in(queryParams)
        .out(jsonBody[List[R]]).out(statusCode(StatusCode.Ok))
        .errorOut(statusCode(StatusCode.NotFound))

    def getCollectionByBody[Q:Encoder: Decoder:Schema:Validator, R:Encoder: Decoder:Schema:Validator](path: String): Endpoint[Q, Unit, List[R], Nothing] =
      endpoint.get.in(path).in(jsonBody[Q])
        .out(jsonBody[List[R]]).out(statusCode(StatusCode.Ok))
        .errorOut(statusCode(StatusCode.NotFound))

    def update[T:Encoder: Decoder:Schema:Validator](path:String): Endpoint[T, Unit, Unit, Nothing] =
      endpoint.put.in(path).in(jsonBody[T])
        .out(statusCode(StatusCode.Accepted))
        .errorOut(statusCode(StatusCode.Conflict))
  }
}
