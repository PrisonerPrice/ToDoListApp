package android.example.todolistapplication.viewModel;

import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private LiveData<Task> task;
    private AppDatabase appDatabase;

    public AddTaskViewModel(AppDatabase appDatabase, int id) {
        this.appDatabase = appDatabase;
        task = appDatabase.taskDao().loadTaskById(id);
    }

    public LiveData<Task> getTask(){
        return task;
    }
}
