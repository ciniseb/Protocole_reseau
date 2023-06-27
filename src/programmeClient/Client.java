package programmeClient;

import classesAuxiliaires.*;

import java.io.*;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        // Création des couches
        CoucheApplication c_application = new CoucheApplication();
        CoucheTransport c_transport = new CoucheTransport();
        CoucheLiaison c_liaison = new CoucheLiaison();
        CouchePhysique c_physique = new CouchePhysique();

        // Définition de la chaîne de responsabilité
        c_application.setProchaineCouche(c_transport);
        c_transport.setProchaineCouche(c_liaison);
        c_liaison.setProchaineCouche(c_physique);

        // Création des données à transférer
        Requete donnees = new Requete(null);

        // Envoi des données au premier maillon de la chaîne (couche application)
        String reponse = c_application.traite(donnees);
        System.out.println(reponse);
    }
}
