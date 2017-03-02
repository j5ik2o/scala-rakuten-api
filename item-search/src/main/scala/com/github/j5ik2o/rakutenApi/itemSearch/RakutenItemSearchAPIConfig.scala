package com.github.j5ik2o.rakutenApi.itemSearch

import scala.concurrent.duration.FiniteDuration
import scalaz.Maybe

case class RakutenItemSearchAPIConfig(
  endPoint: String,
  timeoutForToStrict: FiniteDuration,
  applicationId: String,
  affiliateId: Option[String] = None,
  callback: Option[String] = None,
  formatVersion: Option[Int] = Some(1)
)

