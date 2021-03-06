package android.example.todolistapplication.viewModel;

import android.app.Activity;
import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.repository.DataExchanger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private DataExchanger dataExchanger;

    public AddTaskViewModel(Context context) {
        this.dataExchanger = DataExchanger.getInstance(context);
    }

    public LiveData<Task> getTask(int taskId){
        return dataExchanger.getTask(taskId);
    }

    public void insertOrUpdateTask(Activity activity, Task task, int taskId){
        dataExchanger.insertOrUpdateTask(activity, task, taskId);
    }
}
