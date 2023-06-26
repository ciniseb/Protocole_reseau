package classesAuxiliaires;

public class CoucheTransport extends Couche
{

    @Override
    public String traite(String donnees)
    {
        if(prochaine_couche instanceof CoucheLiaison)
        {
            //TODO: Traitement de la couche actuelle

            prochaine_couche.traite(donnees);
            return "Couche Transport --> Couche Liaison\n" + prochaine_couche.traite(donnees);
        }
        else if(prochaine_couche instanceof CoucheApplication)
        {
            //TODO: Traitement de la couche actuelle

            prochaine_couche.traite(donnees);
            return "Couche Transport --> Couche Application\n" + prochaine_couche.traite(donnees);
        }

        return "Couche Transport : traitement impossible";
    }
}
