package com.github.j5ik2o.rakutenApi.itemSearch

import java.net.URL

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

import scalaz.{ Maybe, \/ }

case class ImageUrl(value: URL)

//case class ParentGenre(value: ParentGenreValue)

case class ParentGenre(
  genreId: String,
  genreName: String,
  genreLevel: Int
)

//case class CurrentGenre(value: CurrentGenreValue)

case class CurrentGenre(
  genreId: String,
  genreName: String,
  itemCount: Int,
  genreLevel: Int
)

case class ChildGenre(value: ChildGenreValue)

case class ChildGenreValue(
  genreId: String,
  genreName: String,
  itemCount: Int,
  genreLevel: Int
)

case class GenreInformation(
  parent: Seq[ParentGenre],
  current: Seq[CurrentGenre],
  children: Seq[ChildGenre]
)

case class TagGroup(value: TagGroupValue)

case class TagGroupValue(
  tagGroupName: String,
  tagGroupId: Long,
  tags: Seq[Tag]
)

case class Tag(value: TagValue)

case class TagValue(
  tagId: Long,
  tagName: String,
  parentTagId: Long,
  itemCount: Int
)

//case class TagInformation(tagGroup: Seq[TagGroup])

case class Item(value: ItemValue)

case class ItemValue(
  itemName: String,
  catchcopy: String,
  itemCode: String,
  itemPrice: Long,
  itemCaption: String,
  itemUrl: URL,
  affiliateUrl: Maybe[URL],
  imageFlag: ImageFlagType.Value,
  smallImageUrls: Seq[ImageUrl],
  mediumImageUrls: Seq[ImageUrl],
  availability: AvailabilityType.Value,
  taxFlag: TaxFlagType.Value,
  postageFlag: PostageFlagType.Value,
  creditCardFlag: CreditCardFlagType.Value,
  shopOfTheYearFlag: ShopOfTheYearFlagType.Value,
  shipOverseasFlag: ShipOverseasFlagType.Value,
  shipOverseasArea: String,
  asurakuFlag: AsurakuFlagType.Value,
  asurakuClosingTime: String,
  asurakuArea: String,
  affiliateRate: Double,
  startTime: String,
  endTime: String,
  reviewCount: Long,
  reviewAverage: Double,
  pointRate: Double,
  pointRateStartTime: String,
  pointRateEndTime: String,
  shopName: String,
  shopCode: String,
  shopUrl: URL,
  shopAffiliateUrl: Maybe[URL],
  genreId: String,
  tagIds: Seq[Long]
)

case class ItemSearchResult(
  count: Long,
  page: Long,
  first: Long,
  last: Long,
  hits: Long,
  carrier: CarrierType.Value,
  pageCount: Long,
  Items: Seq[Item],
  GenreInformation: Seq[GenreInformation],
  TagInformation: Seq[TagGroup]
)

object ItemSearchResult {

  implicit def maybeEncoder[A](implicit encoder: Encoder[A]): Encoder[Maybe[A]] = {
    Encoder.encodeOption[A].contramap[Maybe[A]](_.toOption)
  }

  implicit def maybeDecoder[A](implicit encoder: Decoder[A]): Decoder[Maybe[A]] = {
    import scalaz.std.option._
    import scalaz.syntax.optional._
    Decoder.decodeOption[A].map(_.toMaybe)
  }

  implicit lazy val uriEncoder: Encoder[URL] = Encoder[String].contramap(_.toString)
  implicit lazy val uriDecoder: Decoder[URL] = Decoder[String].map { v =>
    new URL(v)
  }

  implicit lazy val maybeUriEncoder: Encoder[Maybe[URL]] = Encoder[Maybe[String]].contramap(_.map(_.toString))
  implicit lazy val maybeUriDecoder: Decoder[Maybe[URL]] = Decoder[String].map { v =>
    if (v == "") {
      Maybe.empty
    } else {
      Maybe.just(new URL(v))
    }
  }

  implicit lazy val imageUrlEncoder: Encoder[ImageUrl] = Encoder.instance[ImageUrl] { v =>
    Json.obj(
      "imageUrl" -> v.value.asJson
    )
  }
  implicit lazy val imageUrlDecoder: Decoder[ImageUrl] = Decoder.instance[ImageUrl] { v =>
    \/.fromEither(v.downField("imageUrl").as[URL]).map(ImageUrl).toEither
  }

  implicit lazy val availabilityTypeEncoder: Encoder[AvailabilityType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val availabilityTypeDecoder: Decoder[AvailabilityType.Value] = Decoder.decodeInt.map(AvailabilityType(_))

  implicit lazy val imageFlagTypeEncoder: Encoder[ImageFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val imageFlagTypeDecoder: Decoder[ImageFlagType.Value] = Decoder.decodeInt.map(ImageFlagType(_))

  implicit lazy val taxFlagTypeEncoder: Encoder[TaxFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val taxFlagTypeDecoder: Decoder[TaxFlagType.Value] = Decoder.decodeInt.map(TaxFlagType(_))

  implicit lazy val postageFlagTypeEncoder: Encoder[PostageFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val postageFlagTypeDecoder: Decoder[PostageFlagType.Value] = Decoder.decodeInt.map(PostageFlagType(_))

  implicit lazy val creditCardFlagTypeEncoder: Encoder[CreditCardFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val creditCardFlagTypeDecoder: Decoder[CreditCardFlagType.Value] = Decoder.decodeInt.map(CreditCardFlagType(_))

  implicit lazy val shopOfTheYearFlagTypeEncoder: Encoder[ShopOfTheYearFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val shopOfTheYearFlagTypeDecoder: Decoder[ShopOfTheYearFlagType.Value] = Decoder.decodeInt.map(ShopOfTheYearFlagType(_))

  implicit lazy val shipOverseasFlagTypeEncoder: Encoder[ShipOverseasFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val shipOverseasFlagTypeDecoder: Decoder[ShipOverseasFlagType.Value] = Decoder.decodeInt.map(ShipOverseasFlagType(_))

  implicit lazy val asurakuFlagTypeEncoder: Encoder[AsurakuFlagType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit lazy val asurakuFlagTypeDecoder: Decoder[AsurakuFlagType.Value] = Decoder.decodeInt.map(AsurakuFlagType(_))

  implicit val carrierTypeEncoder: Encoder[CarrierType.Value] = Encoder.encodeInt.contramap(_.id)
  implicit val carrierTypeDecoder: Decoder[CarrierType.Value] = Decoder.decodeInt.map(CarrierType(_))

  implicit val parentGenreValueEncoder: Encoder[ParentGenre] = deriveEncoder
  implicit val parentGenreValueDecoder: Decoder[ParentGenre] = deriveDecoder

  //  implicit lazy val parentGenreEncoder: Encoder[ParentGenre] = Encoder.instance[ParentGenre] { v =>
  //    Json.obj(
  //      "parent" -> v.asJson
  //    )
  //  }
  //  implicit lazy val parentGenreDecoder: Decoder[ParentGenre] = Decoder.instance[ParentGenre] { v =>
  //    \/.fromEither(v.downField("parent").as[ParentGenreValue]).map(ParentGenre).toEither
  //  }

  implicit val currentGenreValueEncoder: Encoder[CurrentGenre] = deriveEncoder
  implicit val currentGenreValueDecoder: Decoder[CurrentGenre] = deriveDecoder

  //  implicit lazy val currentGenreEncoder: Encoder[CurrentGenre] = Encoder.instance[CurrentGenre] { v =>
  //    Json.obj(
  //      "current" -> v.asJson
  //    )
  //  }
  //  implicit lazy val currentGenreDecoder: Decoder[CurrentGenre] = Decoder.instance[CurrentGenre] { v =>
  //    \/.fromEither(v.downField("current").as[CurrentGenreValue]).map(CurrentGenre).toEither
  //  }

  implicit val childGenreValueEncoder: Encoder[ChildGenreValue] = deriveEncoder
  implicit val childGenreValueDecoder: Decoder[ChildGenreValue] = deriveDecoder

  implicit lazy val childGenreEncoder: Encoder[ChildGenre] = Encoder.instance[ChildGenre] { v =>
    Json.obj(
      "child" -> v.asJson
    )
  }
  implicit lazy val childGenreDecoder: Decoder[ChildGenre] = Decoder.instance[ChildGenre] { v =>
    \/.fromEither(v.downField("child").as[ChildGenreValue]).map(ChildGenre).toEither
  }

  implicit val genreInformationEncoder: Encoder[GenreInformation] = deriveEncoder
  implicit val genreInformationDecoder: Decoder[GenreInformation] = deriveDecoder

  implicit val tagGroupValueEncoder: Encoder[TagGroupValue] = deriveEncoder
  implicit val tagGroupValueDecoder: Decoder[TagGroupValue] = deriveDecoder

  implicit lazy val tagGroupEncoder: Encoder[TagGroup] = Encoder.instance[TagGroup] { v =>
    Json.obj(
      "tagGroup" -> v.asJson
    )
  }
  implicit lazy val tagGroupDecoder: Decoder[TagGroup] = Decoder.instance[TagGroup] { v =>
    \/.fromEither(v.downField("tagGroup").as[TagGroupValue]).map(TagGroup).toEither
  }

  implicit val tagValueEncoder: Encoder[TagValue] = deriveEncoder
  implicit val tagValueDecoder: Decoder[TagValue] = deriveDecoder

  implicit lazy val tagEncoder: Encoder[Tag] = Encoder.instance[Tag] { v =>
    Json.obj(
      "tag" -> v.asJson
    )
  }
  implicit lazy val tagDecoder: Decoder[Tag] = Decoder.instance[Tag] { v =>
    \/.fromEither(v.downField("tag").as[TagValue]).map(Tag).toEither
  }

  //
  //  implicit val tagInformationEncoder: Encoder[TagInformation] = deriveEncoder
  //  implicit val tagInformationDecoder: Decoder[TagInformation] = deriveDecoder

  implicit val itemValueEncoder: Encoder[ItemValue] = deriveEncoder
  implicit val itemValueDecoder: Decoder[ItemValue] = deriveDecoder

  implicit lazy val itemEncoder: Encoder[Item] = Encoder.instance[Item] { v =>
    Json.obj(
      "Item" -> v.asJson
    )
  }
  implicit lazy val itemDecoder: Decoder[Item] = Decoder.instance[Item] { v =>
    \/.fromEither(v.downField("Item").as[ItemValue]).map(Item).toEither
  }

  implicit lazy val itemSearchResultEncoder: Encoder[ItemSearchResult] = deriveEncoder
  implicit lazy val itemSearchResultDecoder: Decoder[ItemSearchResult] = deriveDecoder
}

