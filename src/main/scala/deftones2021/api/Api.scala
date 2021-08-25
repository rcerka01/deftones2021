package deftones2021.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import deftones2021.deftones2021Context
import deftones2021.api.error.deftones2021ExceptionHandler
import deftones2021.api.rejection.deftones2021RejectionHandler
import deftones2021.datasource.TrackDataSource

import scala.concurrent.ExecutionContextExecutor

trait Api extends StatusApi
  with TrackApi
  with deftones2021RejectionHandler
  with deftones2021ExceptionHandler {

  override def trackDataSource: TrackDataSource = deftones2021Context.itemDataSource

  lazy val routes = {
    logRequestResult("deftones2021") {
      statusRoutes ~ itemRoutes
    }
  }
}
