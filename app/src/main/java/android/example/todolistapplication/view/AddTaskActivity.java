package android.example.todolistapplication.view;

import android.content.Intent;
import android.example.todolistapplication.R;
import android.example.todolistapplication.database.Task;
import android.example.todolistapplication.viewModel.AddTaskViewModel;
import android.example.todolistapplication.viewModel.AddTaskViewModelFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "nstanceTaskId";

    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    private static final int DEFAULT_TASK_ID = -1;

    private static final String TAG = AddTaskActivity.class.getSimpleName();

    private EditText mEditText;
    private RadioGroup mRadioGroup;
    private Button mButton;

    private int mTaskId = DEFAULT_TASK_ID;

    private AddTaskViewModelFactory factory = new AddTaskViewModelFactory(this, mTaskId);
    private final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)){
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID){
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);



                viewModel.getTask(mTaskId).observe(this, task -> {
                    viewModel.getTask(mTaskId).removeObserver((Observer<? super Task>) this);
                    populateUI(task);
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    private void initViews(){
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);
        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(view -> onSaveButtonClicked());
    }

    private void populateUI(Task task){
        if (task == null) return;
        mEditText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }

    public void onSaveButtonClicked(){
        String description = mEditText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();

        final Task task = new Task(description, priority, date);
        viewModel.insertOrUpdateTask(this, task, mTaskId);
    }

    private int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    private void setPriorityInViews(int priority) {
        switch (priority){
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
}
