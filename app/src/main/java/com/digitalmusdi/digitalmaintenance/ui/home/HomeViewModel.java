package com.digitalmusdi.digitalmaintenance.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> date;
    Calendar calendar = Calendar.getInstance();
    Date currentTime = calendar.getTime();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");


    public HomeViewModel() {
        date = new MutableLiveData<>();

        date.setValue(simpleDateFormat.format(currentTime));

    }

    public LiveData<String> getDate(){
        return date;
    }


}