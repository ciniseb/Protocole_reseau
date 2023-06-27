package programmeClient;

import classesAuxiliaires.*;

import java.io.*;
import java.net.*;

import static classesAuxiliaires.Constantes.adresseIP_destination;
import static classesAuxiliaires.Constantes.nom_fichier;


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

        /*String premiereQuote = "";
        if (args.length != 1)
        {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[1600];
        InetAddress address = InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25001);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        if (premiereQuote.equals("true"))
        {
            premiereQuote = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Titre: " + premiereQuote);
        }
        else
        {
            if(received.equals("End of file, Goodbye!"))
            {
                System.out.println("Quote of the Moment: " + received);
                return;
            }
            else
            {
                System.out.println("Quote of the Moment: " + received);
            }

        }

        socket.close();*/
    }
}
