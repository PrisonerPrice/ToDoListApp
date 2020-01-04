package android.example.todolistapplication.viewModel;

import android.app.Application;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.repository.AppExecutors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Task>> tasks;
    private AppDatabase appDatabase;
    private AppExecutors appExecutors;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
        appExecutors = AppExecutors.getInstance();
        tasks = appDatabase.taskDao().loadTaskList();
    }

    public LiveData<List<Task>> getTasks(){
        return tasks;
    }

    public void deleteTaskById(final Task task){
        appExecutors.getDiskIO().execute(() -> { appDatabase.taskDao().deleteTask(task); });
    }
}
