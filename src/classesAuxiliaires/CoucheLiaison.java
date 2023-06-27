package classesAuxiliaires;

import java.util.zip.CRC32;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class CoucheLiaison extends Couche
{
    private static final Logger LOGGER = Logger.getLogger(CoucheLiaison.class.getName());
    int erreurCRC = 0;
    int paquetTransmit = 0;
    int paquetRecut = 0;
    int paquetPerdu =0;
    @Override
    public String traite(Requete donnees)
    {
        if(prochaine_couche instanceof CouchePhysique)
        {
            donnees.setBytes(calculerCRC(donnees.getBytes()));

            paquetTransmit ++;

            prochaine_couche.traite(donnees);


            try {
                // Create a file handler to write log messages to a file
                FileHandler fileHandler = new FileHandler("liaisonDeDonne.log");

                // Set the file handler for the logger
                LOGGER.addHandler(fileHandler);


                LOGGER.info("Le client à envoyé un message");

                // Close the file handler
                fileHandler.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Couche Liaison --> Couche Physique\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheTransport) {

            if(verifierCRC(donnees.getBytes(), POLYNOMIAL))
            {
                erreurCRC++;
            }

            if(donnees.getString() != null)
            {
                paquetRecut++;
            }

                paquetPerdu = paquetTransmit- paquetRecut;


            try {
                // Create a file handler to write log messages to a file
                FileHandler fileHandler = new FileHandler("liaisonDeDonne.log");

                // Set the file handler for the logger
                LOGGER.addHandler(fileHandler);


                LOGGER.info("Le serveur à reçut des données");

                // Close the file handler
                fileHandler.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

                prochaine_couche.traite(donnees);
                return "Couche Liaison --> Couche Transport\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Liaison : traitement impossible";
    }


    private static final int POLYNOMIAL = 0xEDB88320;

    public static byte[] calculerCRC(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        long crcValue = crc32.getValue();
        byte[] crcBytes = new byte[4];
        crcBytes[0] = (byte) ((crcValue >>> 24) & 0xFF);
        crcBytes[1] = (byte) ((crcValue >>> 16) & 0xFF);
        crcBytes[2] = (byte) ((crcValue >>> 8) & 0xFF);
        crcBytes[3] = (byte) (crcValue & 0xFF);
        System.out.print(crcBytes);
        return crcBytes;
    }

    public static boolean verifierCRC(byte[] data, long crc) {
        crc ^= 0xFFFFFFFF;
        for (byte b : data) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ POLYNOMIAL;
                } else {
                    crc >>>= 1;
                }
            }
        }
        return crc == 0;
    }

}


