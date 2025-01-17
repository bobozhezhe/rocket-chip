// See LICENSE.SiFive for license details.

package freechips.rocketchip.devices.tilelink

import Chisel._
import org.chipsalliance.cde.config.Parameters
import freechips.rocketchip.diplomacy._

/** Adds a /dev/null slave that does not raise ready for any incoming traffic.
  * !!! WARNING: This device WILL cause your bus to deadlock for as long as you
  *              continue to send traffic to it !!!
  */
class TLDeadlock(params: DevNullParams, beatBytes: Int = 4)(implicit p: Parameters)
    extends DevNullDevice(params, minLatency = 1, // technically not true but we don't want to add extra logic to handle minLatency = 0
      beatBytes, new SimpleDevice("deadlock-device", Seq("sifive,deadlock0")))
{
  lazy val module = new Impl
  class Impl extends LazyModuleImp(this) {
    val (in, _) = node.in(0)
    in.a.ready := Bool(false)
    in.b.valid := Bool(false)
    in.c.ready := Bool(false)
    in.d.valid := Bool(false)
    in.e.ready := Bool(false)
  }
}
