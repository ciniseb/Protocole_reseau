package classesAuxiliaires;

import java.util.zip.CRC32;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static classesAuxiliaires.Constantes.LOGGER;
import static classesAuxiliaires.Constantes.POLYNOMIAL;


public class CoucheLiaison extends Couche
{
    int erreurCRC = 0;
    int paquetTransmit = 0;
    int paquetRecut = 0;
    int paquetPerdu =0;
    private static final int POLYNOME = 0xEDB88320;


    @Override
    public void traite(Requete donnees)
    {
        if(prochaine_couche instanceof CouchePhysique)
        {
            donnees.setBytes(calculerCRC(donnees.getBytes()));

            paquetTransmit ++;

            try
            {
                FileHandler fileHandler = new FileHandler("liaisonDeDonneEnvoie.log");

                LOGGER.addHandler(fileHandler);

                LOGGER.info("Le client à envoyé un message");

                fileHandler.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            System.out.println("Couche Liaison --> Couche Physique");
            prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheTransport)
        {

            if(verifierCRC(donnees.getBytes(), POLYNOME))
            {
                erreurCRC++;
            }

            if(donnees.getString() != null)
            {
                paquetRecut++;
            }

                paquetPerdu = paquetTransmit- paquetRecut;


            try
            {
                FileHandler fileHandler = new FileHandler("liaisonDeDonneReception.log");

                LOGGER.addHandler(fileHandler);


                LOGGER.info("Le serveur à reçut des données");

                fileHandler.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            //TODO : retirer l'en-tête

            System.out.println("Couche Liaison --> Couche Transport");
            prochaine_couche.traite(donnees);
        }
        else
        {
            System.err.println("Couche Liaison : traitement impossible");
        }
    }

    public static byte[] calculerCRC(byte[] donnees) {
        CRC32 crc = new CRC32();
        crc.update(donnees);
        long valeurCrc = crc.getValue();
        byte[] crcTable = new byte[4];
        crcTable[0] = (byte) ((valeurCrc >>> 24) & 0xFF);
        crcTable[1] = (byte) ((valeurCrc >>> 16) & 0xFF);
        crcTable[2] = (byte) ((valeurCrc >>> 8) & 0xFF);
        crcTable[3] = (byte) (valeurCrc & 0xFF);
        return crcTable;
    }

    public static boolean verifierCRC(byte[] donnees, long crc) {
        crc ^= 0xFFFFFFFF;
        for (byte b : donnees) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOME;
                } else {
                    crc >>>= 1;
                }
            }
        }
        return crc == 0;
    }

}


