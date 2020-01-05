package android.example.todolistapplication.repository;

import android.app.Activity;
import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataExchanger {

    private AppDatabase appDatabase;
    private AppExecutors appExecutors;
    private static int DEFAULT_TASK_ID = -1;

    public DataExchanger(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        appExecutors = AppExecutors.getInstance();
    }

    public LiveData<Task> getTask(int id){
        return appDatabase.taskDao().loadTaskById(id);
    }

    public LiveData<List<Task>> getTaskList(){
        return appDatabase.taskDao().loadTaskList();
    }

    public void deleteTask(Task task){
        appExecutors.getDiskIO().execute(() -> {
            appDatabase.taskDao().deleteTask(task);
        });
    }

    public void insertTask(Task task){
        appDatabase.taskDao().insertTask(task);
    }

    public void insertOrUpdateTask(Activity activity, Task task, int mTaskId){
        appExecutors.getDiskIO().execute(() -> {
            if (mTaskId == DEFAULT_TASK_ID){
                appDatabase.taskDao().insertTask(task);
            } else {
                task.setId(mTaskId);
                appDatabase.taskDao().updateTask(task);
            }
            activity.finish();
        });
    }
}
