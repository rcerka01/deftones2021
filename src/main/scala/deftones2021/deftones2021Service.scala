package deftones2021

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import deftones2021.api.Api
import deftones2021.config.Config
import org.slf4s.Logging
import scala.concurrent.ExecutionContextExecutor

object deftones2021Service extends App
  with Api
  with Config
  with Logging {

  implicit val system: ActorSystem = deftones2021Context.system
  implicit val materializer: ActorMaterializer = deftones2021Context.materializer

  implicit def executor: ExecutionContextExecutor = system.dispatcher


  Http().bindAndHandle(routes, httpInterface, httpPort)

  log.info(s"Starting $serviceName on $httpInterface:$httpPort")
}
