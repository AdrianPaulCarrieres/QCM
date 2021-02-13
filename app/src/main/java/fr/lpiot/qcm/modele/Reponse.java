package fr.lpiot.qcm.modele;

public class Reponse {


    private String reponse;
    private boolean bool;

    public Reponse(String s, boolean b) {
        reponse =s;
        bool =b;
    }
    public String getReponse() {
        return reponse;
    }

    public boolean isBool() {
        return bool;
    }
}
