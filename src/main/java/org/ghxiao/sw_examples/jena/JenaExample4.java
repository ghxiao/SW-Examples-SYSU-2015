package org.ghxiao.sw_examples.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD;

public class JenaExample4 {

    public static void main(String[] args) {

        // some definitions
        String johnSmithURI = "http://somewhere/JohnSmith";
        String givenName = "John";
        String familyName = "Smith";
        String fullName = givenName + " " + familyName;

// create an empty Model
        Model model = ModelFactory.createDefaultModel();

// create the resource
//   and add the properties cascading style
        Resource johnSmith
                = model.createResource(johnSmithURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        model.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));

// retrieve the John Smith vcard resource from the model
        Resource vcard = model.getResource(johnSmithURI);



        // retrieve the value of the N property
        Resource name = (Resource) vcard.getProperty(VCARD.N)
                .getObject();

        RDFNode retrievedGivenName = name.getProperty(VCARD.Given).getObject();
        System.out.println(retrievedGivenName);

        // retrieve the first name property
        String retrievedFullName = vcard.getProperty(VCARD.FN)
                .getString();

        System.out.println(retrievedFullName);

        vcard.addProperty(VCARD.NICKNAME, "Smithy")
                .addProperty(VCARD.NICKNAME, "Adman");

        model.write(System.out, "TURTLE");

    }
}
