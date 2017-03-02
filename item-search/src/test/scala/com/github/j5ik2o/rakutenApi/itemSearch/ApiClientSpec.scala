package com.github.j5ik2o.rakutenApi.itemSearch

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.FunSpecLike
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Seconds, Span }

import scala.concurrent.duration._

class ApiClientSpec extends TestKit(ActorSystem("ApiClientSpec"))
    with FunSpecLike with ScalaFutures {

  override implicit def patienceConfig: PatienceConfig =
    PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  describe("ApiClient") {
    it("should be able to get") {
      val config = RakutenItemSearchAPIConfig(
        endPoint = "app.rakuten.co.jp",
        timeoutForToStrict = 10 seconds,
        applicationId = "1096375936322000163"
      )

      val client = new RakutenItemSearchAPI(config)
      val result = client.searchItems(
        keyword = Some("楽天"),
        //genreId = Some(559887),
        genreInformationFlag = Some(true)
      //tagInformationFlag = Some(true)
      ).futureValue
      //      val result = client.search(
      //        keyword = Some("楽天"),
      //        genreId = Some(559887),
      //        genreInformationFlag = Some(true),
      //        tagInformationFlag = Some(true)
      //      ).futureValue
      println(result)
    }
  }

}
