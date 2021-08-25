package deftones2021.api

import akka.http.scaladsl.server.Directives._
import deftones2021.api.directives.CustomDirectives
import deftones2021.domain.response.Status
import deftones2021.domain.marshalling.JsonSerializers

trait StatusApi extends JsonSerializers
  with CustomDirectives {

  val statusRoutes = {
    path("status") {
      get {
        respondWithNoCacheHeaders {
          complete {
            Status("OK")
          }
        }
      }
    }
  }
}
