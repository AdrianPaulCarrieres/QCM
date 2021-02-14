package fr.lpiot.qcm.donnee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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

    public UtilisateurDAO(){
        accesseurBaseDeDonnees = BaseDeDonnees.getInstance();
        listeUtilisateurs = new ArrayList<>();
        listerUtilisateurs();
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
        Log.d("userDAO", listeUtilisateurs.get(0).toString());
        curseur.close();
        return listeUtilisateurs;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO utilisateur(id_utilisateur, nom ,mot_de_passe) VALUES(null, ?, ?)");
        query.bindString(1, utilisateur.getNom());
        query.bindString(2, utilisateur.getMotDePasse());
        query.execute();

        listerUtilisateurs();
    }

    public Utilisateur chercherUtilisateurParNom(String nom){
        for(Utilisateur utilisateurRecherche : this.listeUtilisateurs){
            if(utilisateurRecherche.getNom().equals(nom)) return utilisateurRecherche;
        }
        return null;
    }

    public void modifierUtilisateur(Utilisateur utilisateur){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("UPDATE utilisateur SET nom = ?, mot_de_passe = ? where id_utilisateur = ?");
        query.bindString(1, utilisateur.getNom());
        query.bindString(2, utilisateur.getMotDePasse());
        query.bindString(3, String.valueOf(utilisateur.getId_utilisateur()));

        query.execute();
    }

    public Utilisateur getUtilisateurConnecte(){
        Utilisateur utilisateur = null;
        String LISTER_UTILISATEUR = "SELECT * FROM token";
        Cursor curseur = accesseurBaseDeDonnees.getReadableDatabase().rawQuery(LISTER_UTILISATEUR, null);

        //int indexId_token = curseur.getColumnIndex("id_token");
        int indexNomUtilisateur = curseur.getColumnIndex("nom_utilisateur");

        for(curseur.moveToFirst(); !curseur.isAfterLast(); curseur.moveToNext()){
            //int id_token = curseur.getInt(indexId_token);
            String nom = curseur.getString(indexNomUtilisateur);
            utilisateur = this.chercherUtilisateurParNom(nom);
        }
        curseur.close();
        return utilisateur;
    }

    public void ajouterToken(Utilisateur utilisateur){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO token(id_token, nom_utilisateur) VALUES(null, ?)");
        query.bindString(1, utilisateur.getNom());
        query.execute();
    }

    public void retirerToken(Utilisateur utilisateur){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("DELETE FROM token WHERE nom_utilisateur = ?");
        query.bindString(1, utilisateur.getNom());
        query.execute();
    }
}
