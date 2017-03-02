package com.github.j5ik2o.rakutenApi.itemSearch

import scala.concurrent.duration.FiniteDuration
import scalaz.Maybe

case class RakutenItemSearchAPIConfig(
  endPoint: String,
  timeoutForToStrict: FiniteDuration,
  applicationId: String,
  affiliateId: Maybe[String] = Maybe.empty,
  callback: Maybe[String] = Maybe.empty,
  formatVersion: Maybe[Int] = Maybe.just(1)
)

