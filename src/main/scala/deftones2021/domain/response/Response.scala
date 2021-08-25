package deftones2021.domain.response

case class Response[T](
  total: Int,
  limit: Int,
  offset: Int,
  results: Seq[T])

case class ResponseSuccess(message: String)
