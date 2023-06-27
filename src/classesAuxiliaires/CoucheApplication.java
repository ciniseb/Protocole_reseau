package classesAuxiliaires;

import java.io.*;

import static classesAuxiliaires.Constantes.*;

public class CoucheApplication extends Couche
{
    @Override
    public String traite(Requete donnees)
    {
        if(prochaine_couche instanceof CoucheTransport)
        {
            FileInputStream fileInputStream = null;
            try
            {
                File file = new File(nom_fichier);

                if (!file.exists())
                {
                    if (!file.createNewFile())
                    {
                        System.out.println("Création impossible du fichier : " + nom_fichier);
                    }
                }

                fileInputStream = new FileInputStream(nom_fichier);
                donnees.setBytes(fileInputStream.readAllBytes());
                fileInputStream.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            prochaine_couche.traite(donnees);
            return "Couche Application --> Couche Transport\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche == null)
        {
            FileOutputStream fileOutputStream = null;
            try
            {
                fileOutputStream = new FileOutputStream(nom_fichier); //TODO: temporaire
                donnees.setString(donnees.getString() + " (Sortie)");
                fileOutputStream.write(donnees.getBytes());
                fileOutputStream.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            return "Couche Application --> FIN\n";
        }

        return "Couche Application : traitement impossible";
    }
}
