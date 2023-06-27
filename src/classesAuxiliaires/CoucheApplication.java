package classesAuxiliaires;

import java.io.*;
import java.util.Arrays;

import static classesAuxiliaires.Constantes.*;

public class CoucheApplication extends Couche
{
    @Override
    public void traite(Requete donnees)
    {
        if(prochaine_couche instanceof CoucheTransport)
        {
            FileInputStream fileInputStream = null;
            try
            {
                File file = new File(NOM_FICHIER);

                if (!file.exists())
                {
                    if (!file.createNewFile())
                    {
                        System.out.println("Création impossible du fichier : " + NOM_FICHIER);
                    }
                }

                fileInputStream = new FileInputStream(NOM_FICHIER);
                donnees.setBytes(fileInputStream.readAllBytes());
                fileInputStream.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println("Couche Application --> Couche Transport");
            prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche == null)
        {
            FileOutputStream fileOutputStream = null;
            try
            {
                fileOutputStream = new FileOutputStream(NOM_FICHIER); //TODO: temporaire
                donnees.setString(donnees.getString() + " (Sortie)");
                fileOutputStream.write(donnees.getBytes());
                fileOutputStream.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println("Couche Application --> FIN");
        }
        else
        {
            System.err.println("Couche Application : traitement impossible");
        }
    }
}
