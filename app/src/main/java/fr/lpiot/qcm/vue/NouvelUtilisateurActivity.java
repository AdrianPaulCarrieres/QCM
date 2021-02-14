package fr.lpiot.qcm.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.donnee.UtilisateurDAO;
import fr.lpiot.qcm.modele.Utilisateur;

public class NouvelUtilisateurActivity extends AppCompatActivity {

    //View
    protected TextView textViewIdentifiant;
    protected TextView textViewMotDePasse;

    //UtilisateurDAO
    protected UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvel_utilisateur);

        accesseurUtilisateur = UtilisateurDAO.getInstance();

        textViewIdentifiant = findViewById(R.id.identifiant);
        textViewMotDePasse = findViewById(R.id.mdp);
    }

    public void buttonAjouterNouvelUtilisateur(View v){
        String nom = textViewIdentifiant.getText().toString();
        String motDePasse = textViewMotDePasse.getText().toString();

        if(nom == null || motDePasse == null){
            afficherToast("Les deux champs sont requis.");
        } else{
            Utilisateur utilisateur = new Utilisateur(nom, motDePasse);
            accesseurUtilisateur.ajouterUtilisateur(utilisateur);
            afficherToast("Nouvel utilisateur inscrit !");
            accesseurUtilisateur.ajouterToken(utilisateur);
            naviguerVersMenu(utilisateur.getNom());
        }
    }

    private void afficherToast(String message){
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
        Log.i("login", "toasted message : " + message);
    }

    private void naviguerVersMenu(String nomUtilisateur){
        //Passer au prochain écran -> pour le test on va faire la création
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("nomUtilisateur", nomUtilisateur);
        startActivity(intent);
    }
}