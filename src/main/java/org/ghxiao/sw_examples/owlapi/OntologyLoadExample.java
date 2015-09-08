package org.ghxiao.sw_examples.owlapi;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import java.io.File;

public class OntologyLoadExample {

    public static void main(String[] args) throws OWLOntologyCreationException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        String ontologyFile = "src/main/resources/univ-bench-dl.owl";
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyFile));

        //DLSyntaxObjectRenderer renderer = new DLSyntaxObjectRenderer();

        //System.out.println(renderer.render(ontology));

        ontology.getAxioms().forEach(System.out::println);

    }

}
