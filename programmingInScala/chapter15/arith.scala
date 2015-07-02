object arith{
    def main(args: Array[String]) = {


        abstract class Expr
        case class Var(name: String) extends Expr
        case class Number(num: Double) extends Expr
        case class UnOp(operator: String, arg: Expr) extends Expr
        case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
 
        println(Var("one"))
        println(Number(1.2))    
        
        def simplifyTop(expr: Expr): Expr = expr match {
            case UnOp("-", UnOp("-", e)) => e
            case BinOp("*",Number(1),e) => e
            case BinOp("*",e,Number(1)) => e
            case BinOp("+",Number(0),e) => e
            case BinOp("+",e,Number(0)) => e
            case _ => expr
        }
        
        println(simplifyTop(Var("variable")))
        println(simplifyTop(BinOp("+", Number(0), Number(2))))
        println(simplifyTop(BinOp("+", Number(2), Number(0))))
        println(simplifyTop(UnOp("-", UnOp("-", Number(2)))))
   

    }
}
