package android.example.todolistapplication.viewModel;

import android.app.Application;
import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.repository.AppExecutors;
import android.example.todolistapplication.repository.DataExchanger;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private DataExchanger dataExchanger;

    public MainViewModel(@NonNull Application application, Context context) {
        super(application);
        dataExchanger = new DataExchanger(context);
    }

    public LiveData<List<Task>> getTasks(){
        return dataExchanger.getTaskList();
    }

    public void deleteTaskById(final Task task){
        dataExchanger.deleteTask(task);
    }
}
