// See LICENSE.SiFive for license details.
package TYOD_SOC

import Chisel._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.subsystem._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.util.DontTouch
import sifive.blocks.devices.uart._

/** TYOD_SOC Top with periphery devices and ports, and a Rocket subsystem */

class TYOD_SOC(implicit p: Parameters) extends RocketSubsystem
    with HasPeripheryBootROM
    with HasPeripheryUART
    with HasSystemErrorSlave {
  override lazy val module = new TYOD_SOCModuleImp(this)
}

class TYOD_SOCModuleImp[+L <: TYOD_SOC](_outer: L) extends RocketSubsystemModuleImp(_outer)
    with HasPeripheryBootROMModuleImp
    with HasPeripheryUARTModuleImp 
    with DontTouch

