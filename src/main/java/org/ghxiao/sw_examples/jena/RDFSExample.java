package org.ghxiao.sw_examples.jena;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

public class RDFSExample {
    public static void main(String[] args) {
        String NS = "urn:x-hp-jena:eg/";

        // Build a trivial example data set
        Model rdfsExample = ModelFactory.createDefaultModel();
        Property p = rdfsExample.createProperty(NS, "p");
        Property q = rdfsExample.createProperty(NS, "q");
        Property r = rdfsExample.createProperty(NS, "r");
        rdfsExample.add(p, RDFS.subPropertyOf, q);
        rdfsExample.add(q, RDFS.subPropertyOf, r);

        // assert a triple (a, p, foo)
        rdfsExample.createResource(NS+"a").addProperty(p, "foo");
        rdfsExample.createResource(NS+"a").addProperty(q, "bar");


        InfModel inf = ModelFactory.createRDFSModel(rdfsExample);  // [1]

        //ModelFactory.createDefaultModel(rdfsExample);

//        Reasoner reasoner = RDFSRuleReasonerFactory.theInstance().create(null);
//        InfModel inf = ModelFactory.createInfModel(reasoner, rdfsExample);

//
//        Resource a = inf.getResource(NS + "a");
//        System.out.println("Statement: " + a.getProperty(q));

        //String sparql = String.format("SELECT * WHERE { ?X <%s> ?Y .}", q.getURI());
        String sparql = String.format(
                "SELECT * WHERE { ?X <%s> ?Y .}", RDFS.subPropertyOf);

        System.out.println("SPARQL Query:");

        System.out.println(sparql);

        System.out.println();

        Query query = QueryFactory.create(sparql);

        System.out.println("Solutions under Simple RDF reasoning:");

        try (QueryExecution qe = QueryExecutionFactory.create(query, rdfsExample)) {
            ResultSet res = qe.execSelect();
            res.forEachRemaining(System.out::println);
        }

        System.out.println();

        System.out.println("Solutions under RDFS reasoning:");

        try (QueryExecution qe = QueryExecutionFactory.create(query, inf)) {
            ResultSet res = qe.execSelect();
            res.forEachRemaining(System.out::println);
        }

    }
}
