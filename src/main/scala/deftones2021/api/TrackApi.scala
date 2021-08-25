package deftones2021.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import deftones2021.api.directives.CustomDirectives
import deftones2021.config.Config
import deftones2021.datasource.TrackDataSource
import deftones2021.domain.Track
import deftones2021.domain.marshalling.JsonSerializers
import org.slf4s.Logging

trait TrackApi extends JsonSerializers
  with CustomDirectives
  with Config
  with Logging {

  def trackDataSource: TrackDataSource

  val itemRoutes = {
    path("tracks") {
      get {
        respondWithCacheHeaders {
          paginate(defaultPageLimit, maximumPageLimit) { pagination =>
            sort { itemSort =>
              complete {
                log.info("/tracks")
                toResponse(pagination) {
                  trackDataSource.getMultipleTracks(pagination.limit, pagination.offset)
                }
              }
            }
          }
        }
      }
    } ~ path("track" / Segment) { id =>
      get {
        respondWithCacheHeaders {
          complete {
            log.info(s"/track/$id")
            toResponse() {
              trackDataSource.getSingleTrack(id)
            }
          }
        }
      }
    } ~ path("track" / "create") {
      post {
        respondWithNoCacheHeaders {
          entity(as[Track]) { track =>
            complete {
              log.info(s"/track/create. id: ${track.id}")
              // 201 instead of 200
              toResponse(StatusCodes.Created) {
                trackDataSource.addSingleTrack(track)
              }
            }
          }
        }
      }
    }
  }
}

