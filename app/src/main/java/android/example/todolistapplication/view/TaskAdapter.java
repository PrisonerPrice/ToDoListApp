package android.example.todolistapplication.view;

import android.content.Context;
import android.example.todolistapplication.R;
import android.example.todolistapplication.database.Task;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private List<Task> mTaskEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    final private ItemClickListener mItemClickListener;

    public TaskAdapter(Context context, ItemClickListener mItemClickListener) {
        this.mContext = context;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = mTaskEntries.get(position);
        String description = task.getDescription();
        int priority = task.getPriority();
        String updatedAt = dateFormat.format(task.getUpdatedAt());

        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        String priorityString = "" + priority;
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    private int getPriorityColor(int priority){
        int priorityColor = 0;
        switch (priority){
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) return 0;
        return mTaskEntries.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

    public interface ItemClickListener{
        void onItemClickListener(int itemId);
    }
}
