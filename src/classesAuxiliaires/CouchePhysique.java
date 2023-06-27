package classesAuxiliaires;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static classesAuxiliaires.Constantes.ADRESSE_IP_DESTINATION;

public class CouchePhysique extends Couche
{

    @Override
    public String traite(Requete donnees)
    {
        //int port = 25001;

        if(prochaine_couche == null)
        {
            try
            {
                DatagramSocket socket = new DatagramSocket();

                // Préparation du message à envoyer
                /*[] donnees_bytes_envoie = donnees.getBytes();
                InetAddress adresseIP_receveur = InetAddress.getByName("localhost");
                DatagramPacket paquet_envoie = new DatagramPacket(donnees_bytes_envoie, donnees_bytes_envoie.length, adresseIP_receveur, port);*/

                // Envoi du paquet
                socket.send(donnees.getPaquet());

                // Fermeture du socket
                socket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return "Couche Physique --> Couche Physique\n";
        }
        else if(prochaine_couche instanceof CoucheLiaison)
        {
            try
            {

                // Création du socket
                DatagramSocket socket = new DatagramSocket(ADRESSE_IP_DESTINATION); //TODO: temporaire

                // Attente de réception d'un paquet
                byte[] donnees_bytes_reception = new byte[1600];
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

            return "Couche Physique --> Couche Liaison\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Physique : traitement impossible";
    }
}
