package android.example.todolistapplication.repository;

import android.app.Activity;
import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.view.TaskAdapter;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

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

    public void deleteTask(RecyclerView.ViewHolder viewHolder, TaskAdapter mAdapter){
        appExecutors.getDiskIO().execute(() -> {
            int position = viewHolder.getAdapterPosition();
            List<Task> tasks = mAdapter.getTasks();
            appDatabase.taskDao().deleteTask(tasks.get(position));
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
