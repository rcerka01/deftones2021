package deftones2021.datasource

import deftones2021.api.directives.ResponseDirectives.{MultiEntityResponseData, SingleEntityResponseData}
import deftones2021.domain.Track
import org.json4s._
import org.json4s.jackson.JsonMethods._
import deftones2021.config.Config
import deftones2021.deftones2021Context.database

import scala.concurrent.Future
import scala.io.Source

class ImplTrackDataSource extends TrackDataSource with Config {
  implicit val formats: Formats = DefaultFormats

  private def getInitialData(fileName: String): Track = {
    val source = Source.fromFile(fileName)
    val data = parse(source.mkString).extract[Track]
    source.close()
    data
  }

  override def getMultipleTracks(limit: Int, offset: Int): Future[MultiEntityResponseData[Track]] = {
    val results: Seq[Track] = database.take(limit)
    Future.successful(MultiEntityResponseData(results, results.size))
  }

  database = database :+ getInitialData("initialData.json")
}

