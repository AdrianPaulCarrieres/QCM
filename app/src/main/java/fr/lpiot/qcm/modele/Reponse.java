package fr.lpiot.qcm.modele;

import com.google.gson.annotations.SerializedName;

public class Reponse {
    private String reponse;
    private boolean bool;

    public Reponse(String reponse, boolean bool) {
        this.reponse = reponse;
        this.bool = bool;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "reponse='" + reponse + '\'' +
                ", bool=" + bool +
                '}';
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
