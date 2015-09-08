package org.ghxiao.sw_examples.owlapi;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.AutoIRIMapper;

import java.io.File;

public class OntologyAddAxioms {
    public static final IRI pizza_iri = IRI
            .create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
    public static final IRI example_iri = IRI
            .create("http://www.semanticweb.org/ontologies/ont.owl");
    public static OWLDataFactory df = OWLManager.getOWLDataFactory();

    public static OWLOntologyManager create() {
        OWLOntologyManager m =
                OWLManager.createOWLOntologyManager();
        m.addIRIMapper(new AutoIRIMapper(
                new File("materializedOntologies"), true));
        return m; }

    public static void main(String[] args) throws OWLOntologyCreationException {


        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(pizza_iri);
        // class A and class B

        OWLClass clsA = df.getOWLClass(IRI.create(pizza_iri + "#A"));
        OWLClass clsB = df.getOWLClass(IRI.create(pizza_iri + "#B")); // Now create the axiom
        OWLAxiom axiom = df.getOWLSubClassOfAxiom(clsA, clsB);
        // add the axiom to the ontology.
        AddAxiom addAxiom = new AddAxiom(o, axiom);
        // We now use the manager to apply the change m.applyChange(addAxiom);
// remove the axiom from the ontology
        //RemoveAxiom removeAxiom = new RemoveAxiom(o,axiom);
        m.applyChange(addAxiom);

        o.getAxioms().forEach(System.out::println);
    }


}
