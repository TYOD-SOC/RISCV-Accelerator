package TYOD_SOC

import Chisel._
import freechips.rocketchip.config._
//import freechips.rocketchip.subsystem.{WithRoccExample=> _,_}
//import freechips.rocketchip.system.{DefaultRV32Config,WithJtagDTMSystem}
import freechips.rocketchip.subsystem._
import freechips.rocketchip.system._
import freechips.rocketchip.devices.debug._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket._
import freechips.rocketchip.tile._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util._
import sifive.blocks.devices.uart._

class WithoutCompressed extends Config((site, here, up) => {
  case RocketTilesKey => up(RocketTilesKey, site) map { r =>
    r.copy(core = r.core.copy(useCompressed = false))
  }
})

class DeviceConfig extends Config((site, here, up) => {
  case PeripheryUARTKey => List(
    UARTParams(address = 0x10013000)
  )
})

class WithoutAXIConfig extends Config(
  new WithNoMemPort ++
  new WithNMemoryChannels(0) ++
  new WithNoMMIOPort ++
  new WithNoSlavePort ++
  new WithScratchpadsOnly
)
    
class CordicRoCCConfig extends Config((site, here, up) => {
  case BuildRoCC => List(
    (p: Parameters) => {
        val CordicAccel = LazyModule(new CordicRoCC(OpcodeSet.custom0)(p))
        CordicAccel
    })
})

class MyConfig extends Config(new CordicRoCCConfig ++ new DeviceConfig ++ new WithJtagDTMSystem ++ new 
  WithL1DCacheSets(32) ++new WithL1ICacheSets(8) ++new WithCacheBlockBytes(32) ++ new WithoutAXIConfig 
  ++ new WithoutFPU ++ new  WithoutCompressed ++ new TinyConfig)

/*class MyConfig extends Config(new WithRoccExample ++ new DeviceConfig ++ new WithJtagDTMSystem ++new WithL1DCacheSets(4) ++new WithL1ICacheSets(8) ++new WithCacheBlockBytes(8) ++ new WithoutAXIConfig  ++ new WithoutMulDiv ++ new WithoutFPU ++ new WithoutCompressed  ++ new TinyConfig)*/

        

