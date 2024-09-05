// Copyright (c) 2019-2020 by Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package natchez
package datadog

import cats.effect._
import cats.implicits._
import natchez._
import scala.scalajs.js

final case class DDspan[F[_]: Sync](
  jsSpan: Ref[F, natchez.datadog.Span]
) extends Span.Default[F] {
  def makeSpan(name: String, options: natchez.Span.Options): cats.effect.Resource[F,natchez.Span[F]] = for {

      currentSpan <- Resource.eval(jsSpan.get)

      newJsSpan <- Resource.eval(
        for {
          now <- Sync[F].delay(System.nanoTime())
          span <- Sync[F].delay(
            currentSpan.tracer().startSpan(name, toJsSpanOptions[F](name, now, Some(currentSpan), List.empty))
          )
        } yield span
      )

      newJsSpanRef <- Resource.eval(Ref.of(newJsSpan))

      ddSpan = DDSpan4s(newJsSpanRef)

      cleanup = for {
        _ <- DDSpan4s.finish[F](newJsSpan)
      } yield ()

      spanResource <- cats.effect.Resource.make(ddSpan.pure[F])(_ => cleanup)

  } yield spanResource


  def finish[F[_]: Sync](span: natchez.datadog.Span): F[Unit] =
    Async[F].delay(span.finish())
    
}

   def toJsSpanOptions[F[_]](
    name: String,
    startTime: js.Any,
    parent: Option[js.Any],
    tags: List[(String, String)]
  ): js.Object =
    js.Dynamic
      .literal(
        "operationName" -> name,
        "childOf" -> parent.getOrElse(js.undefined),
        "tags" -> js.Dictionary(
          (tags: _*)
        ),
        "startTime" -> startTime
      )

}
