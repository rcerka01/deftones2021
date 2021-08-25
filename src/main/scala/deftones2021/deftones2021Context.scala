package deftones2021

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import deftones2021.config.Config
import deftones2021.datasource.{ImplTrackDataSource, TrackDataSource}
import deftones2021.domain.Track

object deftones2021Context extends Config {
  implicit val system: ActorSystem = ActorSystem(serviceName)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // what would normally be a database
  var database: Seq[Track] = Nil

  val itemDataSource: TrackDataSource = new ImplTrackDataSource
}
