package deftones2021.datasource

import deftones2021.domain.Track
import deftones2021.api.directives.ResponseDirectives.SingleEntityResponseData
import org.specs2.mutable.Specification
import util.FutureSupport

class RandomItemDataSourceSpec extends Specification
  with FutureSupport {

  "The RandomItemDataSource" should {
    "return an item at some point in the future" in {
      val randomItemDataSource = new RandomItemDataSource

      whenReady(randomItemDataSource.getSingleItem(1)) { item =>
        item must haveClass[SingleEntityResponseData[Track]]
      }
    }
  }
}
