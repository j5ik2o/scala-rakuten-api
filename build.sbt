val circeVersion = "0.7.0"

lazy val commonSettings = Seq(
  sonatypeProfileName := "com.github.j5ik2o",
  organization := "com.github.j5ik2o",
  scalaVersion := "2.12.1",
  crossScalaVersions := Seq("2.11.8", "2.12.1"),
  scalacOptions ++= Seq(
    "-feature"
    , "-deprecation"
    , "-unchecked"
    , "-encoding"
    , "UTF-8"
    , "-Xfatal-warnings"
    , "-language:existentials"
    , "-language:implicitConversions"
    , "-language:postfixOps"
    , "-language:higherKinds"
    //    , "-Yinline-warnings" // Emit inlining warnings. (Normally surpressed due to high volume)
    , "-Ywarn-adapted-args" // Warn if an argument list is modified to match the receiver
    , "-Ywarn-dead-code" // Warn when dead code is identified.
    , "-Ywarn-inaccessible" // Warn about inaccessible types in method signatures.
    , "-Ywarn-infer-any" // Warn when a type argument is inferred to be `Any`.
    , "-Ywarn-nullary-override" // Warn when non-nullary `def f()' overrides nullary `def f'
    , "-Ywarn-nullary-unit" // Warn when nullary methods return Unit.
    , "-Ywarn-numeric-widen" // Warn when numerics are widened.
    //    , "-Ywarn-unused" // Warn when local and private vals, vars, defs, and types are are unused.
    //, "-Ywarn-unused-import" // Warn when imports are unused.
    , "-Xmax-classfile-name", "200"),
  resolvers ++= Seq(
    "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"
  ),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  ),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := {
    _ => false
  },
  pomExtra := {
    <url>https://github.com/j5ik2o/rakuten-api</url>
      <licenses>
        <license>
          <name>The MIT License</name>
          <url>http://opensource.org/licenses/MIT</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:j5ik2o/rakuten-api.git</url>
        <connection>scm:git:github.com/j5ik2o/rakuten-api</connection>
        <developerConnection>scm:git:git@github.com:j5ik2o/rakuten-api.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
        </developer>
      </developers>
  },
  credentials := Def.task {
    val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
    val result = Credentials(ivyCredentials) :: Nil
    result
  }.value
) ++ scalariformSettings

lazy val itemSearch = (project in file("item-search"))
  .settings(commonSettings)
  .settings(
    name := "scala-rakuten-item-search-api",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-testkit" % "10.0.2" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.1" % Test,
      "commons-codec" % "commons-codec" % "1.10",
      "com.typesafe.akka" %% "akka-http-core" % "10.0.2",
      "com.typesafe.akka" %% "akka-http" % "10.0.2",
      "com.typesafe.akka" %% "akka-http-xml" % "10.0.2",
      "org.scalaz" %% "scalaz-core" % "7.2.8",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
    )
  )

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "rakuten-api-project"
  ).aggregate(itemSearch)

