import scala.collection.mutable.Map
import java.sql.Timestamp
import scala.util.Random
import scala.math.exp
import java.util.Date

object hbase_worksheet {
	println("Hello World")

	def func_build( prefix:String = "PREFIX", number:Int = 3 ) : Array[String] = {
		val myArray = new Array[String](number)
		for(i <- 0 to (number-1) ){
			myArray(i) = prefix + "_" + i.toString
		}
		return myArray
	}


	def func_build_weights( myArray:Array[String], weight:Int): Map[String, Int] = {
		val result = Map[String, Int]()
		for(i <- 0 until myArray.length){
			result += (myArray(i) -> weight * i)
		}
		return result
	}

	def func_rand_date(ts_start_str:String = "2001-01-01 00:00:00", ts_end_str:String = "2015-01-01 00:00:00"): String = {
		val ts_start = Timestamp.valueOf(ts_start_str).getTime()
		val ts_end = Timestamp.valueOf(ts_end_str).getTime()
		val diff = ts_end - ts_start
	 	val ts_rand = new Timestamp(ts_start + (Random.nextFloat() * diff).toLong)
	 	return ts_rand.toString
	}

	def func_calc_month_diff(start:String = "2001-01-01 00:00:00", end:String = "2001-02-01 00:00:00"): Int = {
	  // there is a 1 month difference between 20010131 and 20010201
	  // which might concern some people for short term pricing prediction
		val date_start = Timestamp.valueOf(start)
		val date_end = Timestamp.valueOf(end)
		val months = date_end.getYear * 12 + date_end.getMonth - (date_start.getYear * 12 + date_start.getMonth)
		return months
	}


	val myMap = func_build_weights(Array("MODEL0", "MODEL1", "MODEL2"),	100)

	val CATEGORY = Array("LAPTOP", "NOTEBOOK", "DESKTOP")
	val CATEGORY_weight = Map("LAPTOP"->300, "NOTEBOOK"->200, "DESKTOP"->100)
	val CHANNEL = Array("WHOLESALE", "RETAIL")
	val CHANNEL_weight = Map("WHOLESALE"->0.8, "RETAIL"->1.0)

	val BRAND = func_build("BRAND", 4)
	val BRAND_weight = func_build_weights(BRAND, 50)
	
	val MODEL = func_build("MODEL", 4)
	val MODEL_weight = func_build_weights(MODEL, 40)
	
	val CPU = func_build("CPU", 4)
	val CPU_weight = func_build_weights(CPU, 30)
		
	val RAM = Array(1, 2, 4, 8, 16, 32)
	val RAM_weight = Map[Int, Int]()
	for (elem <- RAM){
		RAM_weight += (elem -> 10 * elem)
	}

	val DISKTYPE = Array("SSD", "HDD")
	val DISKTYPE_weight = Map("SSD"-> 300, "HDD"-> 100)
	val lookup_release_date = Map[String, String]()
	for (myBRAND <- BRAND; myMODEL <- MODEL) {
		lookup_release_date += (myBRAND + "|" + myMODEL -> func_rand_date())
	}

	def func_pricing(month:Int, category:String, channel:String, brand:String, model:String, cpu:String, ram:Int, disktype:String): Float = {
		val price_initial = CATEGORY_weight(category) + BRAND_weight(brand) + MODEL_weight(model) + CPU_weight(cpu) + RAM_weight(ram) + DISKTYPE_weight(disktype)
		val depreciation_rate = exp(-month.toFloat/12)
		val price = price_initial * depreciation_rate * CHANNEL_weight(channel)
		return price.toFloat
	}
	
	 
	
	/****************************************************************
		Generate the Pricing for One Record Given the Transaction ID
	*****************************************************************/
	
	val id = 1
	val myCATEGORY = CATEGORY(Random.nextInt(CATEGORY.size))
	val myCHANNEL = CHANNEL(Random.nextInt(CHANNEL.size))
	val myBRAND = BRAND(Random.nextInt(BRAND.size))
	val myMODEL = MODEL(Random.nextInt(MODEL.size))
	val myCPU = CPU(Random.nextInt(CPU.size))
	val myRAM = RAM(Random.nextInt(RAM.size))
	val myDISKTYPE = DISKTYPE(Random.nextInt(DISKTYPE.size))
    
	val date_release = lookup_release_date(myBRAND + "|" + myMODEL)
	val date_transaction = func_rand_date(date_release, new Timestamp(new Date().getTime()).toString())

	val month = func_calc_month_diff(date_release, date_transaction)
	
	val price = func_pricing(month, myCATEGORY, myCHANNEL, myBRAND, myMODEL, myCPU, myRAM, myDISKTYPE)
	
}
