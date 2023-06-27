package classesAuxiliaires;

public interface ICouche
{
    void setProchaineCouche(ICouche prochaine);
    void traite(Requete donnees);
}
