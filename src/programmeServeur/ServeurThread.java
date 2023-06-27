package programmeServeur;

import classesAuxiliaires.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurThread extends Thread
{
    public ServeurThread() throws IOException
    {
        this("ServeurThread");
    }

    public ServeurThread(String name) throws IOException
    {
        super(name);
    }

    public void run()
    {
        // Création des couches
        Couche c_physique = new CouchePhysique();
        Couche c_liaison = new CoucheLiaison();
        Couche c_transport = new CoucheTransport();
        Couche c_application = new CoucheApplication();

        // Définition de la chaîne de responsabilité
        c_physique.setProchaineCouche(c_liaison);
        c_liaison.setProchaineCouche(c_transport);
        c_transport.setProchaineCouche(c_application);

        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            // Création des données à recevoir
            Requete donnees = new Requete(null);

            // Envoi des données au premier maillon de la chaîne (couche physique)
            String reponse = c_physique.traite(donnees);
            System.out.println(reponse);
        }
    }
}