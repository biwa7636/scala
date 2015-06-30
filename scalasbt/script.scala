import java.util.Date
import org.joda.time.DateTime

object OMG {
    
    def main(args:Array[String]) = {
        println("Start")
        
        val juDate = new Date()
        val dt = new DateTime(juDate)
        println(dt.toString)
        println(dt.getYear())
        println(juDate.toString)        

        val a = 1
        
        def myfunc(x:Int):Int={
            return x+1
        }

        println(myfunc(a))
        println("End")
    }
}
