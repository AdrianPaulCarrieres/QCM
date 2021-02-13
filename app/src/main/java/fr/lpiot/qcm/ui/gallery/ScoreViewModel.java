package fr.lpiot.qcm.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {

    private final MutableLiveData<String> textScoreActuel;

    public ScoreViewModel() {
        textScoreActuel = new MutableLiveData<>();
        //textScoreActuel.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return textScoreActuel;
    }
}