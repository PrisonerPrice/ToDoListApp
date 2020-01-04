package android.example.todolistapplication.viewModel;


import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase appDatabase;
    private final int taskId;

    public AddTaskViewModelFactory(Context context, int taskId) {
        this.appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(appDatabase, taskId);
    }
}
