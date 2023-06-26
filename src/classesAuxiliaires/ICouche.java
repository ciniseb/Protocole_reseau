package classesAuxiliaires;

public interface ICouche
{
    void setProchaineCouche(ICouche prochaine);
    String traite(Requete donnees);
}
