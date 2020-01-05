package android.example.todolistapplication.viewModel;

import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.repository.DataExchanger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private DataExchanger dataExchanger;

    public AddTaskViewModel(Context context) {
        this.dataExchanger = new DataExchanger(context);
    }

    public LiveData<Task> getTask(int taskId){
        return dataExchanger.getTask(taskId);
    }
}
