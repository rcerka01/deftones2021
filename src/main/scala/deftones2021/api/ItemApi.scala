package deftones2021.api

import akka.http.scaladsl.server.Directives._
import deftones2021.api.directives.CustomDirectives
import deftones2021.config.Config
import deftones2021.datasource.TrackDataSource
import deftones2021.domain.marshalling.JsonSerializers
import org.slf4s.Logging

trait ItemApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Logging {

  def itemDataSource: TrackDataSource

  val itemRoutes = {
    path("tracks") {
      get {
        respondWithCacheHeaders {
          paginate(defaultPageLimit, maximumPageLimit) { pagination =>
            sort { itemSort =>
              complete {
                log.info("/tracks")
                toResponse(pagination) {
                  itemDataSource.getMultipleTracks(pagination.limit, pagination.offset)
                }
              }
            }
          }
        }
      }
    }
//    ~ path("items" / IntNumber) { id =>
//      get {
//        respondWithCacheHeaders {
//          complete {
//            log.info(s"/items/$id")
//            toResponse() {
//              itemDataSource.getSingleItem(id)
//            }
//          }
//        }
//      }
//    }
  }
}

