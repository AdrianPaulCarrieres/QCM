package fr.lpiot.qcm.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> textScoreActuel;

    public GalleryViewModel() {
        textScoreActuel = new MutableLiveData<>();
        //textScoreActuel.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return textScoreActuel;
    }
}