# scala-rakuten-api

This product is a Scala library for Rakuten API. 

## Installation

Add the following to your sbt build (Scala 2.11.x, 2.12.x):

### Release Version

```scala
resolvers += "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "com.github.j5ik2o" %% "scala-rakuten-item-search-api" % "1.0.3"
```

### Snapshot Version

```scala
resolvers += "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "com.github.j5ik2o" %% "scala-rakuten-item-search-api" % "1.0.4-SNAPSHOT"
```

## Usage

The supported API is Item Search API only. Please refer to the following code.

```scala
import scala.concurrent.duration._

implicit val system = ActorSytem()

val config = RakutenItemSearchAPIConfig(
  endPoint = "app.rakuten.co.jp",
  timeoutForToStrict = 10 seconds,
  applicationId = ???
)

val client = new RakutenItemSearchAPI(config)

val future = client.search(
  keyword = Some("楽天"),
  genreId = Some(559887),
  genreInformationFlag = Some(true)
  tagInformationFlag = Some(true)
)
      
// If the value is obtained from Future, call Await.result. 
// You should call it as necessary as minimum to avoid frequent blocking.
val result = Await.result(future, Duration.Inf) 
```
