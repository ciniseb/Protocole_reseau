package programmeClient;

import classesAuxiliaires.*;

import java.io.*;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        // Création des couches
        Couche c_application = new CoucheApplication();
        Couche c_transport = new CoucheTransport();
        Couche c_liaison = new CoucheLiaison();
        Couche c_physique = new CouchePhysique();

        // Définition de la chaîne de responsabilité
        c_application.setProchaineCouche(c_transport);
        c_transport.setProchaineCouche(c_liaison);
        c_liaison.setProchaineCouche(c_physique);

        // Création des données à transférer
        Requete donnees = new Requete(null);

        // Envoi des données au premier maillon de la chaîne (couche application)
        c_application.traite(donnees);
    }
}
