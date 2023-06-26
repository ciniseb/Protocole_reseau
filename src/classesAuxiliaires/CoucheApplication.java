package classesAuxiliaires;

public class CoucheApplication extends Couche
{

    @Override
    public String traite(String donnees)
    {
        if(prochaine_couche instanceof CoucheTransport)
        {
            //TODO: Traitement de la couche actuelle

            prochaine_couche.traite(donnees);
            return "Couche Application --> Couche Transport\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche == null)
        {
            //TODO: Traitement de la couche actuelle

            return "Couche Application --> FIN\n";
        }

        return "Couche Application : traitement impossible";
    }
}
