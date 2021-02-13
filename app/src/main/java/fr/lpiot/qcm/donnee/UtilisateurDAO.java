package fr.lpiot.qcm.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.lpiot.qcm.modele.Utilisateur;

public class UtilisateurDAO {
    private static UtilisateurDAO instance = null;
    protected BaseDeDonnees accesseurBaseDeDonnees;
    protected List<Utilisateur> listeUtilisateurs;

    public static UtilisateurDAO getInstance(){
        if(instance == null){
            instance = new UtilisateurDAO();
        }
        return instance;
    }

    private UtilisateurDAO(){
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listeUtilisateurs = new ArrayList<>();
    }

    public List<Utilisateur> listerUtilisateurs(){
        String LISTER_UTILISATEUR = "SELECT * FROM utilisateur";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_UTILISATEUR, null);
        this.listeUtilisateurs.clear();

        int indexId_utilisateur = curseur.getColumnIndex("id_utilisateur");
        int indexNom = curseur.getColumnIndex("nom");
        int indexMotDePasse = curseur.getColumnIndex("mot_de_passe");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()){
            int id_utilisateur = curseur.getInt(indexId_utilisateur);
            String nom = curseur.getString(indexNom);
            String motDePasse = curseur.getString(indexMotDePasse);
            listeUtilisateurs.add(new Utilisateur(id_utilisateur, nom, motDePasse));
        }
        return listeUtilisateurs;
    }
}
