package programmeServeur;

import classesAuxiliaires.CoucheApplication;
import classesAuxiliaires.CoucheLiaison;
import classesAuxiliaires.CouchePhysique;
import classesAuxiliaires.CoucheTransport;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurThread extends Thread
{

    /*protected DatagramSocket socket;
    protected BufferedReader in;*/
    protected boolean execution;

    public ServeurThread() throws IOException
    {
        this("ServeurThread");
    }

    public ServeurThread(String name) throws IOException
    {
        super(name);

        //socket = new DatagramSocket(25001);
        execution = true;

        /*try
        {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e)
        {
            System.err.println("Impossible d'ouvrir le fichier");
        }*/
    }

    public void run()
    {
        // Création des couches
        CouchePhysique c_physique = new CouchePhysique();
        CoucheLiaison c_liaison = new CoucheLiaison();
        CoucheTransport c_transport = new CoucheTransport();
        CoucheApplication c_application = new CoucheApplication();

        // Définition de la chaîne de responsabilité
        c_physique.setProchaineCouche(c_liaison);
        c_liaison.setProchaineCouche(c_transport);
        c_transport.setProchaineCouche(c_application);

        while (execution)
        {
            /*// Réception des donnees
            //TODO
            try
            {
                byte[] buf = new byte[1600];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    //dString = getNextQuote();

                buf = dString.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e)
            {
                e.printStackTrace();
                execution = false;
            }*/
            //String donnees = "Données temporaires";

            // Envoi des données au premier maillon de la chaîne (couche physique)
            String reponse = c_physique.traite(null);
            System.out.println(reponse);
        }

        //socket.close();
    }
}