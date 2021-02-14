# LPIOT — Prog Android : QCM

Par Clément Lavallée et Adrian-Paul Carrières

## Lien vidéo

[https://youtu.be/f8daBZK18Wg](https://youtu.be/f8daBZK18Wg)

## Code intéressant

### Menu (Drawer)

Après que l'utilisateur se soit connecté, il arrive sur la page de Menu qui est composé de différents fragments. Le principal est le fragment Home sur lequel on trouve le bouton pour commencer un quiz. Il y a aussi un fragment Score avec la liste des scores triés par ordre descendant et un fragment Friends qui n'a pas encore d'utilité. 

En haut à droite du menu se trouve un bouton pour se déconnecter. Lors de la déconnexion, on retire le token qui a été créé lorsque l'utilisateur s'est connecté et on retourne sur la page de connexion.

```jsx
public void deconnecterUtilisteur(MenuItem item) {
        Utilisateur utilisateur = accesseurDAO.chercherUtilisateurParNom(nomUtilisateur);
        accesseurDAO.retirerToken(utilisateur);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
```

![LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190455.png](LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190455.png)

![LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190529.png](LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190529.png)

![LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190544.png](LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190544.png)

### QCM

Les questions étant une liste de 5 questions (modifiable via la constante), on doit pouvoir les consommer. Nous avons décidé d'utiliser pour cela une stack, afin de pop les questions les unes après les autres.

On les récupère pour cela à la création de l'affichage grâce à l'API :

```java
private void callApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<ApiResponse> call = service.getQuestions(NOMBRE_QUESTIONS, TYPE_QUESTIONS);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                //Questions
                Question[] questions = response.body().getResults();
                for (Question question : questions) {
                    stackQuestions.push(question);
                }

                nouvelleQuestion();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                afficherToast("Une erreur est survenue.");
            }
        });
    }
```

On va ensuite gérer le fait de passer à une nouvelle question (ou à la première !).

Chaque question étant minutée, il faut annuler le timer en cours (pour éviter que la fonction onFinish se déclenche pour rien) et remettre à 30 l'afficheur et la progress bar à 0. 

```java
private void nouvelleQuestion() {
				//Utile dans le cas de la première question vu qu'il n'y a pas encore eu de timer
        if (timer != null) {
            timer.cancel();
            temps_timer = 30;
            pbar.setProgress(0);
        }
```

On gère le cas où la stack serait vide (c'est à dire qu'il n'y a plus de questions)

S'il reste des questions, on en récupère une, on fait une liste de réponse dans laquelle on commenc par ajouter la bonne réponse puis la liste des mauvais réponses. On mélange le tout.

On gère l'affichage de la question et des réponses dans les boutons.

```java
if (!stackQuestions.empty()) {
            questionActuelle = stackQuestions.pop();
            reponses = new ArrayList<>();

            reponses.add(questionActuelle.getBonneReponse());
            reponses.addAll(Arrays.asList(questionActuelle.getListeFaussesReponses()));
            Collections.shuffle(reponses);

            for (int i = 0; i < reponses.size(); i++) {
                boutons[i].setText(reponses.get(i));
            }

            textViewQuestion.setText(questionActuelle.getQuestion());
```

On crée un minuteur qui va changer l'affichage du temps restant et de la progress bar, et, en arrivant à 0 passe à la question suivante (on revient donc au début de cette fonction).

```java
timer = new CountDownTimer(30000, 1000) {
                String s;

                @Override
                public void onTick(long millisUntilFinished) {
                    pbar.incrementProgressBy(1);
                    --temps_timer;
                    s = "" + temps_timer;
                    tps_restant.setText(s);
                }

                @Override
                public void onFinish() {
                    pbar.incrementProgressBy(1);
                    temps_timer = 30;
                    s = "" + temps_timer;
                    tps_restant.setText(s);
                    afficherToast("Too slow");
                    nouvelleQuestion();
                }

            }.start();
```

Dans le cas où il ne reste plus de question, on va maintenant gérer le score.

On va le persister dans la base de donnée et mettre un minuteur pour gérer le fait d'attendre une seconde avant de revenir au menu.

```java
} else {
            //Passer au score !
            accesseurScore.ajouterScore(new Score(nomUtilisateur, score));

            CountDownTimer timerFinal = new CountDownTimer(1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                   afficherToast("Your score : "+score);
                }
            }.start();

            this.finish();
        }
    }
```

Quant aux réponses, on change la couleur en fonction de si c'est une bonne réponse ou non (par rapport au texte du bouton et de la question actuelle).

On a ensuite un minuteur pour permettre à l'utilisateur de voir si c'est une bonne ou une mauvaise réponse. On en profite pour bloquer l'interaction aux boutons pour éviter de sélectionner plusieurs d'entre eux.

```java
public void choixReponse(View v) {
    if (((Button) v).getText() == questionActuelle.getBonneReponse()) {
        v.setBackgroundColor(v.getContext().getResources().getColor(color.green));
        score += temps_timer;
        String scoreAsString = "" + score;
        scoreJ.setText(scoreAsString);
    } else {
        v.setBackgroundColor(v.getContext().getResources().getColor(color.red));
    }

    toggleBouton(false);

    CountDownTimer timer = new CountDownTimer(1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            v.setBackgroundColor(v.getContext().getResources().getColor(color.white));
            toggleBouton(true);
            nouvelleQuestion();
        }
    };
    timer.start();
}

private void toggleBouton(boolean etat){
    for(int i = 0; i < boutons.length; i++){
        boutons[i].setEnabled(etat);
    }
}
```

![LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190943.png](LPIOT%20%E2%80%94%20Prog%20Android%20QCM%20667df3a5bced4250b6a20f3426411a96/Screenshot_20210214-190943.png)

### API

On utilise la librairie [Retrofit](https://square.github.io/retrofit/) pour gérer les requêtes à [l'API](https://opentdb.com/api_config.php).

Je me suis basé sur le tutoriel suivant pour cela :

[Retrofit- A simple Android tutorial](https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23)

On peut modifier les paramètres de l'API via la requête, et cela se fait dans le code en changeant la constante NOMBRE_QUESTIONS de QCMActivity.

### Base de données locale

Dans [BaseDeDonnees.java](http://basededonnees.java) on peut voir notre utilisation de la base de données locale pour persister des informations, dont l'utilisateur, les scores et un genre de token d'identification pour sauter l'écran de connexion.

On y accède via les DAO, un peu de cette manière.

```java
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
```

Un point notable est l'utilisation de Prepared Statement pour se simplifier l'écriture des requêtes :

```java
public void ajouterUtilisateur(Utilisateur utilisateur){
        SQLiteDatabase db = accesseurBaseDeDonnees.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("INSERT INTO utilisateur(id_utilisateur, nom ,mot_de_passe) VALUES(null, ?, ?)");
        query.bindString(1, utilisateur.getNom());
        query.bindString(2, utilisateur.getMotDePasse());
        query.execute();

        listerUtilisateurs();
    }
```