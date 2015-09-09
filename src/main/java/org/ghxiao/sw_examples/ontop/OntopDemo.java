package org.ghxiao.sw_examples.ontop;

import it.unibz.krdb.obda.exception.InvalidMappingException;
import it.unibz.krdb.obda.exception.InvalidPredicateDeclarationException;
import it.unibz.krdb.obda.io.ModelIOManager;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.owlrefplatform.core.QuestConstants;
import it.unibz.krdb.obda.owlrefplatform.core.QuestPreferences;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWL;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLConnection;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLFactory;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLResultSet;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLStatement;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OntopDemo {

    /*
     * Use the sample database using H2 from
     * https://github.com/ontop/ontop/wiki/InstallingTutorialDatabases
     *
     * Please use the pre-bundled H2 server from the above link
     *
     */
    final String owlFile = "src/main/resources/example/books/exampleBooks.owl";
    final String obdaFile = "src/main/resources/example/books/exampleBooks.obda";
    final String sparqlFile = "src/main/resources/example/books/q1.rq";

    /**
     * Main client program
     */
    public static void main(String[] args) {
        try {
            OntopDemo example = new OntopDemo();
            example.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run() throws Exception {
        OWLOntology ontology = loadOWLOntology(owlFile);
        OBDAModel obdaModel = loadOBDA(obdaFile);

		/*
         * Prepare the configuration for the Quest instance. The example below shows the setup for
		 * "Virtual ABox" mode
		 */
        QuestPreferences preference = new QuestPreferences();
        preference.setCurrentValueOf(QuestPreferences.ABOX_MODE, QuestConstants.VIRTUAL);

		/*
		 * Create the instance of Quest OWL reasoner.
		 */
        QuestOWLFactory factory = new QuestOWLFactory();
        factory.setOBDAController(obdaModel);
        factory.setPreferenceHolder(preference);


		/*
		 * Prepare the data connection for querying.
		 */

        String sparqlQuery = loadSPARQL(sparqlFile);


        try (QuestOWL reasoner = factory.createReasoner(ontology, new SimpleConfiguration());
             QuestOWLConnection conn = reasoner.getConnection();
             QuestOWLStatement st = conn.createStatement();
             QuestOWLResultSet rs = st.executeTuple(sparqlQuery)
        ) {
            int columnSize = rs.getColumnCount();
            while (rs.nextRow()) {
                for (int idx = 1; idx <= columnSize; idx++) {
                    OWLObject binding = rs.getOWLObject(idx);
                    System.out.print(binding.toString() + ", ");
                }
                System.out.print("\n");
            }
            rs.close();

			/*
			 * Print the query summary
			 */

            String sqlQuery = st.getUnfolding(sparqlQuery);

            System.out.println();
            System.out.println("The input SPARQL query:");
            System.out.println("=======================");
            System.out.println(sparqlQuery);
            System.out.println();

            System.out.println("The output SQL query:");
            System.out.println("=====================");
            System.out.println(sqlQuery);

        }
    }

    private OBDAModel loadOBDA(String obdaFile)
            throws IOException, InvalidPredicateDeclarationException, InvalidMappingException {
    /*
     * Load the OBDA model from an external .obda file
     */
        OBDADataFactory fac = OBDADataFactoryImpl.getInstance();
        OBDAModel obdaModel = fac.getOBDAModel();
        ModelIOManager ioManager = new ModelIOManager(obdaModel);
        ioManager.load(obdaFile);
        return obdaModel;
    }

    private String loadSPARQL(String sparqlFile) throws IOException {
        String sparqlQuery = "";

        BufferedReader br = new BufferedReader(new FileReader(sparqlFile));
        String line;
        while ((line = br.readLine()) != null) { // while loop begins here
            sparqlQuery += line + "\n";
        }
        return sparqlQuery;
    }

    /*
     * Load the ontology from an external .owl file.
     */
    private OWLOntology loadOWLOntology(String owlFile) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager.loadOntologyFromOntologyDocument(new File(owlFile));
    }


}

