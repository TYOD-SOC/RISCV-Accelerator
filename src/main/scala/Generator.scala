// See LICENSE.SiFive for license details.

package TYOD_SOC

import freechips.rocketchip.subsystem.RocketTilesKey
import freechips.rocketchip.tile.XLen
import freechips.rocketchip.util.GeneratorApp

import scala.collection.mutable.LinkedHashSet

/** A Generator for platforms containing Rocket Subsystemes */
object Generator extends GeneratorApp {
  val longName = names.topModuleProject + "." + names.configs
  generateFirrtl
  generateAnno
  generateTestSuiteMakefrags
  generateROMs
  generateArtefacts
}
