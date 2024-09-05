// Copyright (c) 2019-2020 by Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package natchez
package datadog

import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSImport("dd-trace", JSImport.Namespace)
object DDjsClient extends js.Object {
  def init(options: js.UndefOr[js.Object] = js.undefined): Tracer = js.native
}

/** https://github.com/DataDog/dd-trace-js/blob/bc2d6a147336755cdd755bc76499af3ec00a30b6/packages/dd-trace/src/opentracing/tracer.js#L22
  */
@js.native
trait Tracer extends js.Object {
  def startSpan(name: String, options: js.Object): Span
}

/** https://github.com/DataDog/dd-trace-js/blob/bc2d6a147336755cdd755bc76499af3ec00a30b6/packages/dd-trace/src/opentracing/span.js#L56
  */
@js.native
trait Span extends js.Object {
  def finish(): Unit
  def setBaggageItem(key: String, value: String): Span
  def setTag(key: String, value: js.Any): Span
  def toString(): String
  def tracer(): Tracer
  def context(): DatadogSpanContext
}

@js.native
@JSGlobal("DatadogSpanContext")
class DatadogSpanContext(props: js.UndefOr[js.Dictionary[js.Any]] = js.undefined) extends js.Object {
  var _traceId: js.UndefOr[js.Any] = js.native
  var _spanId: js.UndefOr[js.Any] = js.native
  var _isRemote: Boolean = js.native
  var _parentId: js.UndefOr[js.Any] = js.native
  var _name: js.UndefOr[String] = js.native
  var _isFinished: Boolean = js.native
  var _tags: js.Dictionary[js.Any] = js.native
  var _sampling: js.Dictionary[js.Any] = js.native
  var _spanSampling: js.UndefOr[js.Any] = js.native
  var _baggageItems: js.Dictionary[js.Any] = js.native
  var _traceparent: js.UndefOr[js.Any] = js.native
  var _tracestate: js.UndefOr[js.Any] = js.native
  var _noop: js.UndefOr[js.Any] = js.native
  var _trace: js.Dictionary[js.Any] = js.native
  var _otelSpanContext: js.UndefOr[js.Any] = js.native

  def toTraceId(get128bitId: Boolean = false): String = js.native
  def toSpanId(get128bitId: Boolean = false): String = js.native
  def toTraceparent(): String = js.native
}

@js.native
trait SpanOptions extends js.Object {
  var childOf: js.UndefOr[js.Any] = js.undefined // Can be Span or SpanContext
  var references: js.UndefOr[js.Array[Reference]] = js.undefined
  var tags: js.UndefOr[js.Dictionary[js.Any]] = js.undefined
  var startTime: js.UndefOr[Double] = js.undefined
}

object SpanOptions {
  def rootSettings: SpanOptions = js.Dynamic
    .literal(
      "childOf" -> js.undefined
    )
    .asInstanceOf[SpanOptions]
}

@js.native
trait SpanContext extends js.Object

@js.native
trait Reference extends js.Object
