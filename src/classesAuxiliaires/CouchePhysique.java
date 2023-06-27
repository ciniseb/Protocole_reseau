package classesAuxiliaires;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import static classesAuxiliaires.Constantes.ADRESSE_IP_DESTINATION;

public class CouchePhysique extends Couche
{

    @Override
    public void traite(Requete donnees)
    {
        //int port = 25001;

        if(prochaine_couche == null)
        {
            try
            {
                DatagramSocket socket = new DatagramSocket();
                socket.send(donnees.getPaquet());
                socket.close();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            System.out.println("Couche Physique --> Couche Physique");
        }
        else if(prochaine_couche instanceof CoucheLiaison)
        {
            try
            {

                // Création du socket
                DatagramSocket socket = new DatagramSocket(ADRESSE_IP_DESTINATION); //TODO: temporaire

                // Attente de réception d'un paquet
                byte[] donnees_bytes_reception = new byte[200];
                DatagramPacket paquet_reception = new DatagramPacket(donnees_bytes_reception, donnees_bytes_reception.length); //TODO: temporaire
                socket.receive(paquet_reception);

                // Traitement du paquet reçu
                donnees.setPaquet(paquet_reception);

                // Fermeture du socket
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("Couche Physique --> Couche Liaison");
            prochaine_couche.traite(donnees);
        }
        else
        {
            System.err.println("Couche Physique : traitement impossible");
        }
    }
}
