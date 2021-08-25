package deftones2021.api.rejection

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Rejection

sealed trait deftones2021Rejection extends Rejection {
  def statusCode: StatusCode
  def message: String
}

case class InvalidOffsetRejection(offset: Int) extends deftones2021Rejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Invalid value for offset: '$offset'"
}

case class InvalidPageLimitRejection(maximumPageLimit: Int) extends deftones2021Rejection {
  val statusCode = StatusCodes.BadRequest
  val message = s"Page limit must be between 0 and $maximumPageLimit"
}
