package org.ghxiao.sw_examples.owlapi;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.io.File;

public class HermiTExample {
    public static void main(String[] args) throws OWLOntologyCreationException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        String ontologyFile = "src/main/resources/univ-bench-dl.owl";
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ontologyFile));

        OWLReasoner hermiTReasoner =
                new Reasoner.ReasonerFactory().createReasoner(ontology);

        OWLDataFactory factory = manager.getOWLDataFactory();

        OWLClass personClass = factory.getOWLClass(IRI.create("http://uob.iodt.ibm.com/univ-bench-dl.owl#Person"));

        //

        //NodeSet<OWLClass> subClasses = hermiTReasoner.getSubClasses(personClass, false);

        //subClasses.forEach(System.out::println);

        OWLClass interestClass = factory.getOWLClass(IRI.create("http://uob.iodt.ibm.com/univ-bench-dl.owl#Interest"));


        NodeSet<OWLNamedIndividual> instances = hermiTReasoner.getInstances(interestClass, false);
        instances.forEach(System.out::println);
    }
}
