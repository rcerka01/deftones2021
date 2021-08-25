package deftones2021.api.directives

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCode}
import deftones2021.api.directives.ResponseDirectives.{AddedEntityResponseData, MultiEntityResponseData, SingleEntityResponseData}
import deftones2021.api.params.Paginate
import deftones2021.domain.response.Response

import scala.concurrent.{ExecutionContextExecutor, Future}

trait ResponseDirectives {
  implicit def executor: ExecutionContextExecutor

  def toResponse[T](pagination: Paginate)
                   (resultsFuture: Future[MultiEntityResponseData[T]]): Future[Response[T]] = {

    resultsFuture.map { results =>
      Response(
        total = results.total,
        limit = pagination.limit,
        offset = pagination.offset,
        results = results.entities
      )
    }
  }

  def toResponse[T]()
                   (resultsFuture: Future[SingleEntityResponseData[T]]): Future[Response[T]] = {

    resultsFuture.map { result =>
      Response(
        total = result.total,
        limit = 1,
        offset = 0,
        results = result.entity.toSeq
      )
    }
  }

  def toResponse(status: StatusCode)
                (resultsFuture: Future[AddedEntityResponseData]): Future[HttpResponse] = {

    resultsFuture.map ( result => {
      val entity = HttpEntity(ContentTypes.`application/json`, result.message)
      HttpResponse(
        status = status,
        entity = entity)
    })
  }

}

object ResponseDirectives {
  case class SingleEntityResponseData[T](entity: Option[T], total: Int)
  case class MultiEntityResponseData[T](entities: Seq[T], total: Int)
  case class AddedEntityResponseData(message: String)
}

