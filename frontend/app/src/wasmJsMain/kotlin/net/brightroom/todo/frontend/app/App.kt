package net.brightroom.todo.frontend.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.brightroom.todo.shared.domain.model.Description
import net.brightroom.todo.shared.domain.model.DueDate
import net.brightroom.todo.shared.domain.model.Task
import net.brightroom.todo.shared.domain.model.TaskId
import net.brightroom.todo.shared.domain.model.Tasks
import net.brightroom.todo.shared.domain.model.Title
import org.jetbrains.compose.resources.Font
import todo_app.frontend.app.generated.resources.NotoSansJP_Regular
import todo_app.frontend.app.generated.resources.Res

/**
 * メインアプリケーションコンポーネント
 */
@Composable
fun App() {
    val notoSansJP = FontFamily(Font(resource = Res.font.NotoSansJP_Regular))

    MaterialTheme {
        var tasks by remember { mutableStateOf(Tasks.empty()) }
        var showCreateDialog by remember { mutableStateOf(false) }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            // ヘッダー
            Text(
                text = "Bright Room Todo",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                fontFamily = notoSansJP,
            )

            // 統計情報
            TaskStatistics(tasks = tasks)

            Spacer(modifier = Modifier.height(16.dp))

            // 操作ボタン
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Button(
                    onClick = { showCreateDialog = true },
                ) {
                    Text("新しいタスクを作成", fontFamily = notoSansJP,)
                }

                Button(
                    onClick = {
                        // サンプルタスクを追加
                        val sampleTask =
                            Task(
                                id = TaskId.generate(),
                                title = Title("サンプルタスク"),
                                description = Description("これはサンプルタスクです"),
                                dueDate = DueDate.parse("2024-12-31"),
                                isCompleted = false,
                            )
                        tasks = Tasks(tasks.list + sampleTask)
                    },
                ) {
                    Text("サンプルタスクを追加", fontFamily = notoSansJP,)
                }
            }

            // タスクリスト
            TaskList(
                tasks = tasks,
                onTaskComplete = { taskId ->
                    val updatedTasks =
                        tasks.list.map { task ->
                            if (task.id == taskId) task.complete() else task
                        }
                    tasks = Tasks(updatedTasks)
                },
                onTaskDelete = { taskId ->
                    val updatedTasks = tasks.list.filter { it.id != taskId }
                    tasks = Tasks(updatedTasks)
                },
            )
        }

        // タスク作成ダイアログ
        if (showCreateDialog) {
            CreateTaskDialog(
                onDismiss = { showCreateDialog = false },
                onConfirm = { title, description, dueDate ->
                    val newTask =
                        Task(
                            id = TaskId.generate(),
                            title = Title(title),
                            description = Description(description),
                            dueDate = DueDate.parse(dueDate),
                            isCompleted = false,
                        )
                    tasks = Tasks(tasks.list + newTask)
                    showCreateDialog = false
                },
            )
        }
    }
}

/**
 * タスク統計情報コンポーネント
 */
@Composable
fun TaskStatistics(tasks: Tasks) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            StatisticItem("総タスク数", tasks.count().toString())
            StatisticItem("完了済み", tasks.completedCount().toString())
            StatisticItem("未完了", tasks.incompleteCount().toString())
            StatisticItem("期限切れ", tasks.overdueCount().toString())
        }
    }
}

/**
 * 統計項目コンポーネント
 */
@Composable
fun StatisticItem(
    label: String,
    value: String,
) {
    val notoSansJP = FontFamily(Font(resource = Res.font.NotoSansJP_Regular))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = notoSansJP,
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = notoSansJP,
        )
    }
}

/**
 * タスクリストコンポーネント
 */
@Composable
fun TaskList(
    tasks: Tasks,
    onTaskComplete: (TaskId) -> Unit,
    onTaskDelete: (TaskId) -> Unit,
) {
    if (tasks.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "タスクがありません",
                modifier = Modifier.padding(32.dp),
                fontSize = 16.sp,
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(tasks.list) { task ->
                TaskItem(
                    task = task,
                    onComplete = { onTaskComplete(task.id) },
                    onDelete = { onTaskDelete(task.id) },
                )
            }
        }
    }
}

/**
 * タスクアイテムコンポーネント
 */
@Composable
fun TaskItem(
    task: Task,
    onComplete: () -> Unit,
    onDelete: () -> Unit,
) {
    val notoSansJP = FontFamily(Font(resource = Res.font.NotoSansJP_Regular))

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = notoSansJP,
                    )
                    if (task.description.toString().isNotEmpty()) {
                        Text(
                            text = task.description.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp),
                            fontFamily = notoSansJP,
                        )
                    }
                    Text(
                        text = "期日: ${task.dueDate}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp),
                        fontFamily = notoSansJP,
                    )
                    if (task.isOverdue()) {
                        Text(
                            text = "期限切れ",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 2.dp),
                            fontFamily = notoSansJP,
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (!task.isCompleted) {
                        Button(
                            onClick = onComplete,
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),
                        ) {
                            Text("完了", fontFamily = notoSansJP,)
                        }
                    } else {
                        Text(
                            text = "完了済み",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            fontFamily = notoSansJP,
                        )
                    }

                    Button(
                        onClick = onDelete,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                            ),
                    ) {
                        Text("削除", fontFamily = notoSansJP,)
                    }
                }
            }
        }
    }
}

/**
 * タスク作成ダイアログコンポーネント
 */
@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("2024-12-31") }

    val notoSansJP = FontFamily(Font(resource = Res.font.NotoSansJP_Regular))

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("新しいタスクを作成") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("タイトル", fontFamily = notoSansJP,) },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("説明", fontFamily = notoSansJP,) },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("期日 (YYYY-MM-DD)", fontFamily = notoSansJP,) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && dueDate.isNotBlank()) {
                        onConfirm(title, description, dueDate)
                    }
                },
            ) {
                Text("作成", fontFamily = notoSansJP,)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル", fontFamily = notoSansJP,)
            }
        },
    )
}
