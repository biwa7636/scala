case class Book(title: String, authors: String*)

object myapp {
    def main(args:Array[String]) = {
        // define the library
        val books: List[Book] = List(
            Book(
                "Programming in Scala",
                "Odersky Martin", "Spoon Lex", "Venners Bill", "Haberman Richard"
            ),
            Book(
                "Unix and Linux system administration handbook",
                "Nemeth Evi", "McClain Ned", "Schweikert David", "Tobi Oetiker"
            ),
            Book(
                "Applied Partial Differential Equations",
                "Haberman Richard"
            ), 
            Book(
                "Solr in action",
                "Trey Grainger", "Timothy Potter"
            )
        )
        println(books)
        println("-"*20)
        
        // query books whose author's last name is Haberman
        val lastName = "Haberman"
        for (b<-books; a<-b.authors; if a.startsWith(lastName)) println(b)
        println("-"*20)
 
        // find the titles of all books that have the string "Program" in their title
        val subString = "Program"
        for (b<-books; if b.title contains subString) println(b.title)
        println("-"*20)
        
        // to find the names of all authors that have written at least two books
        def findAuthor(x:Unit): List[String] = {
            for(b1<-books; b2<-books; if b1!=b2; a1<-b1.authors; a2<-b2.authors; if a1==a2) yield a1
        }
   
        def removeDuplicates[A](xs: List[A]): List[A] = {
           if (xs.isEmpty) xs
           else
               xs.head :: removeDuplicates(
                   xs.tail filter (x => x != xs.head)
               )
        }      
        println(removeDuplicates[String](findAuthor()))

    }
}
