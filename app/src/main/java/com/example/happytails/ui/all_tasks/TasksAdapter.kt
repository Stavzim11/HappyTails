package il.co.syntax.firebasemvvm.ui.all_tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happytails.data.models.Item
import il.co.syntax.firebasemvvm.databinding.TaskLayoutBinding

class TasksAdapter(private val callBack:TaskListener) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val dogs = ArrayList<Item>()

    fun setTasks(tasks: Collection<Task>) {
        this.tasks.clear()
        this.tasks.addAll(tasks)
        notifyDataSetChanged()
    }

    interface TaskListener {
        fun onTaskClicked(task: Task)
        fun onTaskLongClicked(task: Task)
    }

    inner class TaskViewHolder(private val biding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(biding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            biding.root.setOnClickListener(this)
            biding.root.setOnLongClickListener(this)
        }

        fun bind(task: Task) {
            biding.missionTv.text = task.title
            biding.missionCb.isChecked = task.finished
        }

        override fun onClick(p0: View?) {
            callBack.onTaskClicked(tasks[adapterPosition])
        }

        override fun onLongClick(p0: View?): Boolean {
            callBack.onTaskLongClicked(tasks[adapterPosition])
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder(
        TaskLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
        holder.bind(tasks[position])

    override fun getItemCount() = tasks.size

}