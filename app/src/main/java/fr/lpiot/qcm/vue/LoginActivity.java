package fr.lpiot.qcm.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.lpiot.qcm.R;
import fr.lpiot.qcm.donnee.BaseDeDonnees;
import fr.lpiot.qcm.donnee.UtilisateurDAO;
import fr.lpiot.qcm.modele.Utilisateur;

public class LoginActivity extends AppCompatActivity {

    //View
    protected TextView textViewIdentifiant;
    protected TextView textViewMotDePasse;

    //UtilisateurDAO
    protected UtilisateurDAO accesseurUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BaseDeDonnees.getInstance(getApplicationContext());

        accesseurUtilisateur = UtilisateurDAO.getInstance();

        textViewIdentifiant = findViewById(R.id.identifiant);
        textViewMotDePasse = findViewById(R.id.mdp);

        Utilisateur utilisateurConnectePeutEtreNull = accesseurUtilisateur.getUtilisateurConnecte();

        if(utilisateurConnectePeutEtreNull != null){
            naviguerVersMenu(utilisateurConnectePeutEtreNull.getNom());
        }
    }

    public void buttonLoginClick(View v){
        String nom = textViewIdentifiant.getText().toString();
        String motDePasse = textViewMotDePasse.getText().toString();

        Utilisateur utilisateur = accesseurUtilisateur.chercherUtilisateurParNom(nom);

        if(utilisateur == null) {
            afficherToast("Mauvais identifiant");
        }
        else if(utilisateur.getMotDePasse().equals(motDePasse)){
            afficherToast("Utilisateur connecté");
            accesseurUtilisateur.ajouterToken(utilisateur);
            naviguerVersMenu(utilisateur.getNom());
        } else {
            afficherToast("Mauvais mot de passe");
        }
    }

    public void buttonClickNaviguerNouvelUtilisateur(View v){
        Intent intent = new Intent(this, NouvelUtilisateurActivity.class);
        startActivity(intent);
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