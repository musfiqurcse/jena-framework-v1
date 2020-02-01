package RDFLibrary;

import org.apache.jena.assembler.Mode;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
//Generate the model class from jena framework
///home/musfiq/Documents/Jena/apache-jena-3.13.1/bin/schemagen  -i Litera.rdf -o ../src/Litera.java
public class MainThird {

    public static void main(String[] args) {
	    System.out.println("Here is the work");
	    Model authors = RDFDataMgr.loadModel("authors.rdf");
	    Model books = RDFDataMgr.loadModel("books.rdf");
//	    RDFDataMgr.write(System.out, authors, RDFFormat.TURTLE);
//	    RDFDataMgr.write(System.out, authors, RDFFormat.RDFXML);
		Model result = authors.union(books);
		RDFDataMgr.write(System.out, result, RDFFormat.TURTLE);
		//Result iterator
		ResIterator authorIterator = result.listResourcesWithProperty(Litera.authorName);
		if(authorIterator.hasNext()){
			while(authorIterator.hasNext()){
				Resource author = authorIterator.nextResource();
				String name = author.getProperty(Litera.authorName).getString();
				System.out.print(name);
				System.out.println(" wrote: ");
				//We iterate the statement
				StmtIterator booklist  = author.listProperties(Litera.hasWritten);
				if(booklist.hasNext()){
					while(booklist.hasNext()){
						Resource book = (Resource) booklist.nextStatement().getObject();
						String bookName = book.getProperty(Litera.bookName).getString();
						System.out.print("\t");
						System.out.println(bookName);
					}
//					System.out.println("\t Some Books");
				}
				else{
					System.out.print("\t No books.");
				}
			}
		}
		else{
			System.out.println("No authors.");
		}
    }
}