package TYOD_SOC

import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.rocket._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util.InOrderArbiter
import freechips.rocketchip.tile._


class CordicRoCC(opcodes: OpcodeSet)(implicit p: Parameters) extends LazyRoCC(opcodes) {
  override lazy val module = new CordicRoCCModule(this)
}

class CordicRoCCModule(outer:CordicRoCC )(implicit p:Parameters) extends LazyRoCCModuleImp(outer) 
  with HasCoreParameters{ 
	val x = RegInit(0.S(17.W))
  val y = RegInit(0.S(17.W))
  val z = RegInit(0.S(17.W))	
	val phase = RegInit(0.U(16.W))
	val funct = RegInit(0.U(1.W))
	val quad = RegInit(0.U(2.W))
	val result = RegInit(0.U(17.W))
	val resp_rd = RegInit(0.U(5.W))
	
	val s_idle :: s_start :: s_0 :: s_1 :: s_2 :: s_3 :: s_4 :: s_5 :: s_6 :: s_7 :: s_8 :: s_9 :: s_10 :: s_11 :: s_12 :: s_13 :: s_14 :: s_15 :: s_16 :: s_done :: Nil = Enum(20) 
	
	val state = RegInit(s_idle)
  val rot = Vec(Array(8192.S,4836.S,2555.S,1297.S,651.S,325.S,163.S,81.S,40.S,20.S,10.S,5.S,3.S,1.S,1.S,0.S))
	
	io.cmd.ready := (state === s_idle)
	io.resp.valid := (state === s_done)

	when(state === s_idle){
		when(io.cmd.fire()){
			state := s_start		//----------rocc begin to caculate when receiving a valid data------------// 
		}
    x := 0.S
		y := 0.S
		z := 0.S
		phase := 0.U
		funct := 0.U
		result := 0.U
		resp_rd := 0.U
		quad := 0.U
	}

	when(io.cmd.fire()){
		funct := io.cmd.bits.inst.funct(0)
		resp_rd := io.cmd.bits.inst.rd
		quad := io.cmd.bits.rs1(15,14)
		phase := io.cmd.bits.rs1(15,0)
	}

//------------------------------PRE_DEAL-------------------------------//
	when(state === s_start){
		state := s_0
    x := "h09B74".U(17.W).asSInt()
		y := 0.S
		when(quad === "b00".U){  
			z := phase.asSInt()
		}.elsewhen(quad === "b01".U){
			z := (phase-"h4000".U(16.W)).asSInt()
		}.elsewhen(quad === "b10".U){
			z := (phase-"h8000".U(16.W)).asSInt()
		}.otherwise{
			z := (phase-"hC000".U(16.W)).asSInt()
		}
	}

//------------------------------LOOP_STRUCTURE-------------------------//
	when(state === s_0){
		state	:= s_1	
		x := x - (y >> 0.U)
		y := y + (x >> 0.U)
		z := z - rot(0)
	}
	when(state === s_1){
		state	:= s_2
		when(z(16) === 0.U){
			x := x - (y >> 1.U)
			y := y + (x >> 1.U)
			z := z - rot(1)
		}.otherwise{
			x := x + (y >> 1.U)
			y := y - (x >> 1.U)
			z := z + rot(1)
		}
	}
	when(state === s_2){
		state	:= s_3
		when(z(16) === 0.U){
			x := x - (y >> 2.U)
			y := y + (x >> 2.U)
			z := z - rot(2)
		}.otherwise{
			x := x + (y >> 2.U)
			y := y - (x >> 2.U)
			z := z + rot(2)
		}
	}
	when(state === s_3){
		state	:= s_4
		when(z(16) === 0.U){
			x := x - (y >> 3.U)
			y := y + (x >> 3.U)
			z := z - rot(3)
		}.otherwise{
			x := x + (y >> 3.U)
			y := y - (x >> 3.U)
			z := z + rot(3)
		}
	}
	when(state === s_4){
		state	:= s_5
		when(z(16) === 0.U){
			x := x - (y >> 4.U)
			y := y + (x >> 4.U)
			z := z - rot(4)
		}.otherwise{
			x := x + (y >> 4.U)
			y := y - (x >> 4.U)
			z := z + rot(4)
		}
	}
	when(state === s_5){
		state	:= s_6
		when(z(16) === 0.U){
			x := x - (y >> 5.U)
			y := y + (x >> 5.U)
			z := z - rot(5)
		}.otherwise{
			x := x + (y >> 5.U)
			y := y - (x >> 5.U)
			z := z + rot(5)
		}
	}
	when(state === s_6){
		state	:= s_7
		when(z(16) === 0.U){
			x := x - (y >> 6.U)
			y := y + (x >> 6.U)
			z := z - rot(6)
		}.otherwise{
			x := x + (y >> 6.U)
			y := y - (x >> 6.U)
			z := z + rot(6)
		}
	}
	when(state === s_7){
		state	:= s_8
		when(z(16) === 0.U){
			x := x - (y >> 7.U)
			y := y + (x >> 7.U)
			z := z - rot(7)
		}.otherwise{
			x := x + (y >> 7.U)
			y := y - (x >> 7.U)
			z := z + rot(7)
		}
	}
	when(state === s_8){
		state	:= s_9
		when(z(16) === 0.U){
			x := x - (y >> 8.U)
			y := y + (x >> 8.U)
			z := z - rot(8)
		}.otherwise{
			x := x + (y >> 8.U)
			y := y - (x >> 8.U)
			z := z + rot(8)
		}
	}
	when(state === s_9){
		state	:= s_10
		when(z(16) === 0.U){
			x := x - (y >> 9.U)
			y := y + (x >> 9.U)
			z := z - rot(9)
		}.otherwise{
			x := x + (y >> 9.U)
			y := y - (x >> 9.U)
			z := z + rot(9)
		}
	}
	when(state === s_10){
		state	:= s_11
		when(z(16) === 0.U){
			x := x - (y >> 10.U)
			y := y + (x >> 10.U)
			z := z - rot(10)
		}.otherwise{
			x := x + (y >> 10.U)
			y := y - (x >> 10.U)
			z := z + rot(10)
		}
	}
	when(state === s_11){
		state	:= s_12
		when(z(16) === 0.U){
			x := x - (y >> 11.U)
			y := y + (x >> 11.U)
			z := z - rot(11)
		}.otherwise{
			x := x + (y >> 11.U)
			y := y - (x >> 11.U)
			z := z + rot(11)
		}
	}
	when(state === s_12){
		state	:= s_13
		when(z(16) === 0.U){
			x := x - (y >> 12.U)
			y := y + (x >> 12.U)
			z := z - rot(12)
		}.otherwise{
			x := x + (y >> 12.U)
			y := y - (x >> 12.U)
			z := z + rot(12)
		}
	}
	when(state === s_13){
		state	:= s_14
		when(z(16) === 0.U){
			x := x - (y >> 13.U)
			y := y + (x >> 13.U)
			z := z - rot(13)
		}.otherwise{
			x := x + (y >> 13.U)
			y := y - (x >> 13.U)
			z := z + rot(13)
		}
	}
	when(state === s_14){
		state	:= s_15
		when(z(16) === 0.U){
			x := x - (y >> 14.U)
			y := y + (x >> 14.U)
			z := z - rot(14)
		}.otherwise{
			x := x + (y >> 14.U)
			y := y - (x >> 14.U)
			z := z + rot(14)
		}
	}
	when(state === s_15){
		state	:= s_16
		when(z(16) === 0.U){
			x := x - (y >> 15.U)
			y := y + (x >> 15.U)
			z := z - rot(15)
		}.otherwise{
			x := x + (y >> 15.U)
			y := y - (x >> 15.U)
			z := z + rot(15)
		}
	}

//------------------------------POST_DEAL-------------------------------//
//---------funct=0,return sin value,funct=1,return cos value------------//
	when(state === s_16){
		state := s_done
		when(funct === "b0".U){									
			when(quad === "b00".U){
				result := y.asUInt()
			}.elsewhen(quad === "b01".U){ 
				result := x.asUInt()
			}.elsewhen(quad === "b10".U){
				result := ~y.asUInt()
			}.otherwise{
				result := ~x.asUInt()
			}
		}.otherwise{
			when(quad === "b00".U){								
				result := x.asUInt()
			}.elsewhen(quad === "b01".U){ 
				result := ~y.asUInt()
			}.elsewhen(quad === "b10".U){
				result := ~x.asUInt()
			}.otherwise{
				result := y.asUInt()
		}
	}		
}
	when(state === s_done){
		state := s_idle
	}

	io.resp.bits.data := result
	io.resp.bits.rd := resp_rd
}





