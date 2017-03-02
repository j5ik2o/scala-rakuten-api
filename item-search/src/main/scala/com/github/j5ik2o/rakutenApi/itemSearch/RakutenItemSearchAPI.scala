package com.github.j5ik2o.rakutenApi.itemSearch

import java.net.URLEncoder

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import com.typesafe.scalalogging.LazyLogging
import io.circe.parser._

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

case class ItemSearchException(message: String, cause: Throwable)
  extends Exception(message, cause)

class RakutenItemSearchAPI(apiConfig: RakutenItemSearchAPIConfig)(implicit system: ActorSystem) extends LazyLogging {

  private implicit val materializer = ActorMaterializer()

  import system.dispatcher

  private val poolClientFlow = Http().cachedHostConnectionPoolHttps[Int](apiConfig.endPoint, 443)
  private val timeout: FiniteDuration = apiConfig.timeoutForToStrict

  def searchItems(
    keyword: Option[String] = None,
    shopCode: Option[String] = None,
    itemCode: Option[String] = None,
    genreId: Option[Long] = Some(0),
    tagId: Option[Long] = None,
    hits: Option[Int] = Some(30),
    page: Option[Int] = Some(1),
    sort: Option[SortType.Value] = Some(SortType.Standard),
    minPrice: Option[Long] = None,
    maxPrice: Option[Long] = None,
    availability: Option[AvailabilityType.Value] = Some(AvailabilityType.Available),
    field: Option[FieldType.Value] = Some(FieldType.Limited),
    carrier: Option[CarrierType.Value] = Some(CarrierType.PC),
    imageFlag: Option[ImageFlagType.Value] = Some(ImageFlagType.All),
    orFlag: Option[OrFlagType.Value] = Some(OrFlagType.And),
    ngKeyword: Option[String] = None,
    purchaseType: Option[PurchaseType.Value] = None,
    shipOverseasFlag: Option[ShipOverseasFlagType.Value] = Some(ShipOverseasFlagType.NotAvailable),
    shipOverseasArea: Option[String] = None,
    asurakuFlag: Option[AsurakuFlagType.Value] = None,
    asurakuArea: Option[String] = None,
    pointRateFlag: Option[PointRateFlagType.Value] = None,
    pointRate: Option[Int] = None,
    postageFlag: Option[PostageFlagType.Value] = None,
    creditCardFlag: Option[CreditCardFlagType.Value] = None,
    giftFlag: Option[GiftFlagType.Value] = None,
    hasReviewFlag: Option[HasReviewFlagType.Value] = None,
    maxAffiliateRate: Option[Double] = None,
    minAffiliateRate: Option[Double] = None,
    hasMovieFlag: Option[HasMovieFlagType.Value] = None,
    pamphletFlag: Option[PamphletFlagType.Value] = None,
    appointDeliveryDateFlag: Option[AppointDeliveryDateFlagType.Value] = None,
    genreInformationFlag: Option[Boolean] = None,
    tagInformationFlag: Option[Boolean] = None
  ): Future[ItemSearchResult] = {
    val affiliateIdParams = apiConfig.affiliateId.fold("")(e => s"&affiliateId=$e")
    val callbackParams = apiConfig.callback.fold("")(e => s"&callback=$e")
    val formatVersionParams = apiConfig.formatVersion.fold("")(e => s"&formatVersion=$e")
    val keywordParams = keyword.map(e => URLEncoder.encode(e, "UTF-8")).fold("")(e => s"&keyword=$e")
    val shopCodeParams = shopCode.fold("")(e => s"&shopCode=$e")
    val itemCodeParams = itemCode.fold("")(e => s"&itemCode=$e")
    val genreIdParams = genreId.fold("")(e => s"&genreId=$e")
    val tagIdParams = tagId.fold("")(e => s"&tagId=$e")
    val hitsParams = hits.fold("")(e => s"&hits=$e")
    val pageParams = page.fold("")(e => s"&page=$e")
    val sortParams = sort.fold("")(e => s"&sort=${e.toString}")
    val minPriceParams = minPrice.fold("")(e => s"&minPrice=$e")
    val maxPriceParams = maxPrice.fold("")(e => s"&maxPrice=$e")
    val availabilityParams = availability.fold("")(e => s"&availability=${e.id}")
    val fieldParams = field.fold("")(e => s"&field=${e.id}")
    val carrierParams = carrier.fold("")(e => s"&carrier=${e.id}")
    val imageFlagParams = imageFlag.fold("")(e => s"&imageFlag=${e.id}")
    val orFlagParams = orFlag.fold("")(e => s"&orFlag=${e.id}")
    val ngKeywordParams = ngKeyword.fold("")(e => s"&NGKeyword=$e")
    val purchaseTypeParams = purchaseType.fold("")(e => s"&purchaseType=${e.id}")
    val shipOverseasFlagParams = shipOverseasFlag.fold("")(e => s"&shipOverseasFlag=${e.id}")
    val shipOverseasAreaParams = shipOverseasArea.fold("")(e => s"&shipOverseasArea=$e")
    val asurakuFlagParams = asurakuFlag.fold("")(e => s"&asurakuFlag=${e.id}")
    val asurakuAreaParams = asurakuArea.fold("")(e => s"&asurakuArea=$e")
    val pointRateFlagParams = pointRateFlag.fold("")(e => s"&pointRateFlag=${e.id}")
    val pointRateParams = pointRate.fold("")(e => s"&pointRate=$e")
    val postageFlagParams = postageFlag.fold("")(e => s"&postageFlag=${e.id}")
    val creditCardFlagParams = creditCardFlag.fold("")(e => s"&creditCardFlag=${e.id}")
    val giftFlagParams = giftFlag.fold("")(e => s"&giftFlag=${e.id}")
    val hasReviewFlagParams = hasReviewFlag.fold("")(e => s"&hasReviewFlag=${e.id}")
    val maxAffiliateRateParams = maxAffiliateRate.fold("")(e => s"&maxAffiliateRate=$e")
    val minAffiliateRateParams = minAffiliateRate.fold("")(e => s"&minAffiliateRate=$e")
    val hasMovieFlagParams = hasMovieFlag.fold("")(e => s"&hasMovieFlag=${e.id}")
    val pamphletFlagParams = pamphletFlag.fold("")(e => s"&pamphletFlag=${e.id}")
    val appointDeliveryDateFlagParams = appointDeliveryDateFlag.fold("")(e => s"&appointDeliveryDateFlag=${e.id}")
    val genreInformationFlagParams = genreInformationFlag.fold("")(e => s"&genreInformationFlag=${if (e) 1 else 0}")
    val tagInformationFlagParams = tagInformationFlag.fold("")(e => s"&tagInformationFlag=${if (e) 1 else 0}")
    val url = Seq(
      "/services/api/IchibaItem/Search/20140222?format=json", s"&applicationId=${apiConfig.applicationId}", affiliateIdParams, callbackParams, formatVersionParams, keywordParams, shopCodeParams, itemCodeParams, genreIdParams, tagIdParams, hitsParams, pageParams, sortParams, minPriceParams, maxPriceParams, availabilityParams, fieldParams, carrierParams, imageFlagParams, orFlagParams, ngKeywordParams, purchaseTypeParams, shipOverseasFlagParams, shipOverseasAreaParams, asurakuFlagParams, asurakuAreaParams, pointRateFlagParams, pointRateParams, postageFlagParams, creditCardFlagParams, giftFlagParams, hasReviewFlagParams, maxAffiliateRateParams, minAffiliateRateParams, hasMovieFlagParams, pamphletFlagParams, appointDeliveryDateFlagParams, genreInformationFlagParams, tagInformationFlagParams
    ).mkString
    logger.debug("url = {}", url)
    val future = Source.single(HttpRequest(uri = url) -> 1).via(poolClientFlow).runWith(Sink.head)
    future.flatMap {
      case (triedResponse, _) =>
        triedResponse.map { response =>
          response.entity.toStrict(timeout).map {
            _.data
          }.map(_.utf8String).map { s =>
            parse(s) match {
              case Left(e) =>
                throw ItemSearchException("Occurred Error", e)
              case Right(jsonResult) =>
                logger.debug("jsonResult = {}", jsonResult)
                jsonResult.as[ItemSearchResult] match {
                  case Left(e) =>
                    throw ItemSearchException("Occurred Error", e)
                  case Right(model) =>
                    model
                }
            }
          }.recoverWith {
            case ex: Exception =>
              Future.failed(ItemSearchException("Occurred Error", ex))
          }
        }.get
    }
  }

}
