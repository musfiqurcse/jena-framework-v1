package RDFLibrary;
import java.io.*;

import org.apache.commons.codec.Resources;
import org.apache.jena.assembler.Mode;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;

public class Main {
    public static void main(String args[]){
        /* START >> Only print the author rdf Task 1 */
        // Model authorModel = RDFDataMgr.loadModel("resources/authors.rdf");
        // RDFDataMgr.write(System.out, authorModel, Lang.RDFJSON);

        /* END >> Print the author model of RDF end */

        /* START >> Display which author wrote which book. */
        Model bookModel = RDFDataMgr.loadModel("resources/books.rdf");
        Model authorModel = RDFDataMgr.loadModel("resources/authors.rdf");
        Model translatorModel = RDFDataMgr.loadModel("resources/translators.rdf");
        Model locationModels = RDFDataMgr.loadModel("resources/locations.rdf");
        Model all = bookModel.union(authorModel);
        all = all.union(translatorModel);
        all = all.union(locationModels);

        /* Iterating the authorIterators */
        ResIterator authorIterator = all.listResourcesWithProperty(Litera.hasWritten);
        if(authorIterator.hasNext()){
            while(authorIterator.hasNext()){
                Resource author = (Resource) authorIterator.nextResource();
                System.out.println(author.getProperty(Litera.authorName).getString());
                System.out.println(" \n lives in: "+author.getProperty(Litera.livesIn).getString()+"\n");
                StmtIterator booklist = author.listProperties(Litera.hasWritten);
                if (booklist.hasNext()){
                    while(booklist.hasNext() ) {
                        Resource books = (Resource) booklist.nextStatement().getObject();
                        System.out.println(" "+ books.getProperty(Litera.bookName).getString());
                        printTranslators(all, books);
                    }
                }
                else {
                    System.out.println("No Books");
                }
            }
        }
        else{
            System.out.println("No Authors.");
        }
        /* END >> */
    }
    /* Printing the book, by whoom it was translated (Can be zero or more ) */
    private static void printTranslators(Model all, Resource books){
        StmtIterator translatorsIterator = all.listStatements(null ,Litera.hasTranslated, books);
        if(translatorsIterator.hasNext()){
            while(translatorsIterator.hasNext()){
                Resource translateItem = (Resource) translatorsIterator.nextStatement().getSubject();
                System.out.println(" has translated by: "+ translateItem.getProperty(Litera.authorName).getString());
            }
        }
        else{
            System.out.println("not translated");
        }
    }

}
