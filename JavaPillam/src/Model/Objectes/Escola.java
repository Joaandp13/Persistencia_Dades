package Model.Objectes;

import java.util.Objects;

public class Escola {
    private int idEscola;
    private String nom;
    private String poblacio;
    private String aproximacio;
    private String popularitat;


    public Escola(int idEscola, String nom, String poblacio, String aproximacio, String popularitat) {
        setIdEscola(idEscola);
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }
    public Escola(String nom, String poblacio, String aproximacio, String popularitat) {
        setNom(nom);
        setPoblacio(poblacio);
        setAproximacio(aproximacio);
        setPopularitat(popularitat);
    }
    //GETTERS
    public int getIdEscola() {return idEscola;}
    public String getNom() {return nom;}
    public String getPoblacio() {return poblacio;}
    public String getAproximacio() {return aproximacio;}
    public String getPopularitat() {return popularitat;}

    //SETTERS
    public void setIdEscola(int idEscola) {
        if (idEscola < 0)
            throw new IllegalArgumentException("L'id no pot ser negativa");
        this.idEscola = idEscola;
    }

    public void setNom(String nom){
        if(nom == null || nom.isBlank())
            throw new IllegalArgumentException("El nom no pot estar buit");
        this.nom = nom;
    }

    public void setPoblacio(String poblacio){
        if(poblacio == null || poblacio.isBlank())
            throw new IllegalArgumentException("El poblacio no estar buit");
        this.poblacio = poblacio;
    }

    public void setAproximacio(String aproximacio) {
        this.aproximacio = aproximacio; // opcional, sense validació
    }


    public void setPopularitat(String popularitat) {
        if (popularitat != null &&
                !popularitat.equalsIgnoreCase("baixa") &&
                !popularitat.equalsIgnoreCase("mitjana") &&
                !popularitat.equalsIgnoreCase("alta"))
            throw new IllegalArgumentException("Ha de ser Baixa, Mitjana o Alta!");
        this.popularitat = popularitat;
    }
}
