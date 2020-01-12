package android.example.todolistapplication.viewModel;

import android.app.Application;
import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.repository.AppExecutors;
import android.example.todolistapplication.repository.DataExchanger;
import android.example.todolistapplication.view.TaskAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private DataExchanger dataExchanger;

    public MainViewModel(@NonNull Application application, Context context) {
        super(application);
        dataExchanger = DataExchanger.getInstance(context);
    }

    public LiveData<List<Task>> getTasks(){
        return dataExchanger.getTaskList();
    }

    public void deleteTask(RecyclerView.ViewHolder viewHolder, TaskAdapter mAdapter){
        dataExchanger.deleteTask(viewHolder, mAdapter);
    }
}
