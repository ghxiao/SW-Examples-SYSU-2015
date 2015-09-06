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

public class SPARQLExample1 {

    public static void main(String[] args) throws IOException {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        String inputFileName = "src/main/resources/example.ttl";
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the TURTLE file
        model.read(in, null, "TURTLE");

        System.out.println("RDF Graph:");

        model.write(System.out, "TURTLE");

        System.out.println();

        Path path = Paths.get("src/main/resources/q1.sparql");
        String sparql = new String(Files.readAllBytes(path), "UTF-8");

        System.out.println("SPARQL Query:");

        System.out.println(sparql);

        System.out.println();

        Query q = QueryFactory.create(sparql);

        System.out.println("Solution:");

        try (QueryExecution qe = QueryExecutionFactory.create(q, model)) {
            ResultSet res = qe.execSelect();
//            while (res.hasNext()) {
//                QuerySolution soln = res.next();
//                RDFNode v = soln.get("?name");
//                System.out.println(v);
//            }

//            res.forEachRemaining(querySolution -> {
//                System.out.println(querySolution.get("?name"));
//            });

            res.forEachRemaining(System.out::println);
        }


    }

}
