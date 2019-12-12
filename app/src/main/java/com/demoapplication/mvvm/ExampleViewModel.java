package com.demoapplication.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demoapplication.model.Example;

import java.util.List;

public class ExampleViewModel extends ViewModel {

   private LiveData<List<Example>> mList;


}
