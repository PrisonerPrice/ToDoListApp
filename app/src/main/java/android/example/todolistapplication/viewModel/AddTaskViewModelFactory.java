package android.example.todolistapplication.viewModel;


import android.content.Context;
import android.example.todolistapplication.database.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int taskId;
    private Context context;

    public AddTaskViewModelFactory(Context context, int taskId) {
        this.taskId = taskId;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(context);
    }
}
