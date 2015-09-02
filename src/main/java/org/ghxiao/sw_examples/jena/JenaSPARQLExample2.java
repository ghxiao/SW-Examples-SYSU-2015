package org.ghxiao.sw_examples.jena;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JenaSPARQLExample2 {

    public static void main(String[] args) throws IOException {


        System.out.println();

        Path path = Paths.get("src/main/resources/dbpedia_chinese_people.rq");
        String sparql = new String(Files.readAllBytes(path), "UTF-8");

        System.out.println("SPARQL Query:");

        System.out.println(sparql);

        System.out.println();

        Query q = QueryFactory.create(sparql);

        /* connects a SPARQL endpoint*/

        String endpoint = "http://dbpedia.org/sparql";

        System.out.println("Solution over the endpoint: " + endpoint);

        /* !!!! */
        QueryExecution qe = QueryExecutionFactory.sparqlService(endpoint, q);

        ResultSet res = qe.execSelect();
//        while (res.hasNext()) {
//            QuerySolution soln = res.next();
//            System.out.println(soln);
//        }

        res.forEachRemaining(System.out::println);

        qe.close();
    }

}
