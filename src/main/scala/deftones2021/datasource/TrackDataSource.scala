package deftones2021.datasource

import deftones2021.api.directives.ResponseDirectives.{AddedEntityResponseData, MultiEntityResponseData, SingleEntityResponseData}
import deftones2021.domain.Track

import scala.concurrent.Future

trait TrackDataSource {
  def getSingleTrack(id: String): Future[SingleEntityResponseData[Track]]
  def getMultipleTracks(limit: Int, offset: Int): Future[MultiEntityResponseData[Track]]
  def addSingleTrack(track: Track): Future[AddedEntityResponseData]
}
