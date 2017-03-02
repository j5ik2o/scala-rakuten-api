package com.github.j5ik2o.rakutenApi.itemSearch

object SortType extends Enumeration {
  val Standard = Value("standard")
  val PlusAffiliateRate = Value("+affiliateRate")
  val MinusAffiliateRate = Value("-affiliateRate")
  val PlusReviewCount = Value("+reviewCount")
  val MinusReviewCount = Value("-reviewCount")
  val PlusReviewAverage = Value("+reviewAverage")
  val MinusReviewAverage = Value("-reviewAverage")
  val PlusItemPrice = Value("+itemPrice")
  val MinusItemPrice = Value("-itemPrice")
  val PlusUpdateTimestamp = Value("+updateTimestamp")
  val MinusUpdateTimestamp = Value("-updateTimestamp")
}
