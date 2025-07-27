package net.brightroom.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.brightroom.todo.domain.model.task.*
import net.brightroom.todo.domain.model.task.content.*
import net.brightroom.todo.domain.model.task.created.*
import org.jetbrains.compose.resources.Font
import todo_app.frontend.app.generated.resources.Res
import todo_app.frontend.app.generated.resources.NotoSansJP_Regular

@Composable
fun App() {
    // NotoSansJP-Regular フォントの読み込み（コンパイルエラー対応として全身対応）
    val notoSansJPFont = FontFamily(Font(Res.font.NotoSansJP_Regular))
    
    MaterialTheme {
        TodoApp(fontFamily = notoSansJPFont)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(fontFamily: FontFamily) {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableStateOf(0) } // 0: 未完了, 1: 完了
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var isNavigationCollapsed by remember { mutableStateOf(false) }
    
    // ローカルでタスクを管理するための関数
    fun createTask(title: String, description: String) {
        val newTask = Task(
            id = TaskId(),
            content = Content(
                title = Title(title),
                description = Description(description)
            ),
            status = Status.未完了,
            completedTime = NoSetCompletedTime(),
            createdTime = CreatedTime()
        )
        tasks = tasks + newTask
    }
    
    fun updateTask(taskId: TaskId, title: String, description: String) {
        tasks = tasks.map { task ->
            if (task.id == taskId) {
                task.copy(
                    content = Content(
                        title = Title(title),
                        description = Description(description)
                    )
                )
            } else {
                task
            }
        }
    }
    
    fun toggleTaskCompletion(taskId: TaskId) {
        tasks = tasks.map { task ->
            if (task.id == taskId) {
                if (task.status == Status.未完了) {
                    task.copy(
                        status = Status.完了,
                        completedTime = SetCompletedTime()
                    )
                } else {
                    task.copy(
                        status = Status.未完了,
                        completedTime = NoSetCompletedTime()
                    )
                }
            } else {
                task
            }
        }
    }

    // フィルタリングされたタスクリスト
    val filteredTasks = when (selectedTab) {
        0 -> tasks.filter { it.status == Status.未完了 }
        1 -> tasks.filter { it.status == Status.完了 }
        else -> tasks
    }

    Scaffold(
        topBar = {
            Column {
                // ヘッダー
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TODOアプリ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                }
                
                // エラーメッセージ表示
                errorMessage?.let { message ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clickable { errorMessage = null },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = message,
                                color = Color(0xFFD32F2F),
                                fontFamily = fontFamily,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "×",
                                color = Color(0xFFD32F2F),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    // エラーメッセージを5秒後に自動で消す
                    LaunchedEffect(message) {
                        kotlinx.coroutines.delay(5000)
                        errorMessage = null
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Text(
                    text = "+",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 左側ナビゲーションレール
            NavigationRail(
                modifier = Modifier.fillMaxHeight(),
                header = {
                    if (!isNavigationCollapsed) {
                        IconButton(
                            onClick = { isNavigationCollapsed = true }
                        ) {
                            Text("◀", fontSize = 16.sp)
                        }
                    } else {
                        IconButton(
                            onClick = { isNavigationCollapsed = false }
                        ) {
                            Text("▶", fontSize = 16.sp)
                        }
                    }
                }
            ) {
                NavigationRailItem(
                    icon = { Text("📝", fontSize = 20.sp) },
                    label = if (!isNavigationCollapsed) { { Text("未完了", fontFamily = fontFamily) } } else null,
                    selected = selectedTab == 0,
                    onClick = { 
                        selectedTab = 0
                        selectedTask = null
                    }
                )
                NavigationRailItem(
                    icon = { Text("✅", fontSize = 20.sp) },
                    label = if (!isNavigationCollapsed) { { Text("完了", fontFamily = fontFamily) } } else null,
                    selected = selectedTab == 1,
                    onClick = { 
                        selectedTab = 1
                        selectedTask = null
                    }
                )
            }
            // メインコンテンツエリア
            Box(
                modifier = Modifier
                    .weight(if (selectedTask != null) 0.6f else 1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                if (filteredTasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (selectedTab == 0) "未完了のタスクがありません" else "完了したタスクがありません",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = fontFamily
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredTasks) { task ->
                            TaskItem(
                                task = task,
                                fontFamily = fontFamily,
                                onToggleComplete = { taskToToggle ->
                                    toggleTaskCompletion(taskToToggle.id)
                                },
                                onTaskClick = { clickedTask ->
                                    selectedTask = clickedTask
                                }
                            )
                        }
                    }
                }
            }
            
            // サイドバー
            selectedTask?.let { task ->
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                TaskDetailSidebar(
                    task = task,
                    fontFamily = fontFamily,
                    onClose = { selectedTask = null },
                    onToggleComplete = { taskToToggle ->
                        toggleTaskCompletion(taskToToggle.id)
                    },
                    onUpdateTask = { taskId, title, description ->
                        updateTask(taskId, title, description)
                    },
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                )
            }
        }
    }

    // タスク追加ダイアログ
    if (showAddDialog) {
        TaskDialog(
            task = null,
            fontFamily = fontFamily,
            onDismiss = { showAddDialog = false },
            onSave = { title, description ->
                createTask(title, description)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    fontFamily: FontFamily,
    onToggleComplete: (Task) -> Unit,
    onTaskClick: (Task) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.status == Status.完了) Color(0xFFE8F5E8) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 完了チェックボックス
            Button(
                onClick = { onToggleComplete(task) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (task.status == Status.完了) Color(0xFF4CAF50) else Color.Gray
                ),
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = if (task.status == Status.完了) "✓" else "○",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // タスク内容
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.content.title.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily,
                    textDecoration = if (task.status == Status.完了) TextDecoration.LineThrough else null,
                    color = if (task.status == Status.完了) Color.Gray else Color.Black
                )
                if (task.content.description.toString().isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.content.description.toString(),
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        textDecoration = if (task.status == Status.完了) TextDecoration.LineThrough else null,
                        color = if (task.status == Status.完了) Color.Gray else Color(0xFF666666)
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(
    task: Task?,
    fontFamily: FontFamily,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(task?.content?.title?.toString() ?: "") }
    var description by remember { mutableStateOf(task?.content?.description?.toString() ?: "") }
    var titleError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (task == null) "新しいタスク" else "タスクを編集",
                fontFamily = fontFamily
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { 
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { 
                        Text(
                            "タイトル *",
                            fontFamily = fontFamily
                        ) 
                    },
                    isError = titleError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (titleError) {
                    Text(
                        text = "タイトルは必須です",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { 
                        Text(
                            "説明",
                            fontFamily = fontFamily
                        ) 
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(title, description)
                    } else {
                        titleError = true
                    }
                }
            ) {
                Text(
                    if (task == null) "追加" else "更新",
                    fontFamily = fontFamily
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "キャンセル",
                    fontFamily = fontFamily
                )
            }
        }
    )
}

@Composable
fun TaskDetailSidebar(
    task: Task,
    fontFamily: FontFamily,
    onClose: () -> Unit,
    onToggleComplete: (Task) -> Unit,
    onUpdateTask: (TaskId, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditMode by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf(task.content.title.toString()) }
    var editDescription by remember { mutableStateOf(task.content.description.toString()) }
    var titleError by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        // ヘッダー
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "タスク詳細",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                modifier = Modifier.size(32.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "×",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ステータス表示
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (task.status == Status.完了) Color(0xFFE8F5E8) else Color(0xFFFFF3E0)
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (task.status == Status.完了) "✅" else "📝",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (task.status == Status.完了) "完了" else "未完了",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // タイトル
        Text(
            text = "タイトル",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isEditMode) {
            OutlinedTextField(
                value = editTitle,
                onValueChange = { 
                    editTitle = it
                    titleError = it.isBlank()
                },
                label = { 
                    Text(
                        "タイトル *",
                        fontFamily = fontFamily
                    ) 
                },
                isError = titleError,
                modifier = Modifier.fillMaxWidth()
            )
            if (titleError) {
                Text(
                    text = "タイトルは必須です",
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        } else {
            Text(
                text = task.content.title.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily,
                textDecoration = if (task.status == Status.完了) TextDecoration.LineThrough else null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 説明
        Text(
            text = "説明",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isEditMode) {
            OutlinedTextField(
                value = editDescription,
                onValueChange = { editDescription = it },
                label = { 
                    Text(
                        "説明",
                        fontFamily = fontFamily
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        } else {
            Text(
                text = if (task.content.description.toString().isNotBlank()) 
                    task.content.description.toString() 
                else "説明なし",
                fontSize = 16.sp,
                fontFamily = fontFamily,
                textDecoration = if (task.status == Status.完了) TextDecoration.LineThrough else null,
                color = if (task.content.description.toString().isBlank()) Color.Gray else Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 作成日時
        Text(
            text = "作成日時",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = task.createdTime.toString(),
            fontSize = 14.sp,
            fontFamily = fontFamily,
            color = Color.Gray
        )

        // 完了日時（完了している場合のみ）
        if (task.status == Status.完了) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "完了日時",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = fontFamily,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.completedTime.toString(),
                fontSize = 14.sp,
                fontFamily = fontFamily,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // アクションボタン
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isEditMode) {
                // 編集モード時のボタン
                Button(
                    onClick = {
                        if (editTitle.isNotBlank()) {
                            onUpdateTask(task.id, editTitle, editDescription)
                            isEditMode = false
                            titleError = false
                        } else {
                            titleError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(
                        text = "保存",
                        fontFamily = fontFamily,
                        color = Color.White
                    )
                }
                
                Button(
                    onClick = {
                        isEditMode = false
                        editTitle = task.content.title.toString()
                        editDescription = task.content.description.toString()
                        titleError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "キャンセル",
                        fontFamily = fontFamily,
                        color = Color.White
                    )
                }
            } else {
                // 通常モード時のボタン
                Button(
                    onClick = { onToggleComplete(task) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (task.status == Status.完了) Color(0xFFFF9800) else Color(0xFF4CAF50)
                    )
                ) {
                    Text(
                        text = if (task.status == Status.完了) "未完了に戻す" else "完了にする",
                        fontFamily = fontFamily,
                        color = Color.White
                    )
                }
                
                Button(
                    onClick = { 
                        isEditMode = true
                        editTitle = task.content.title.toString()
                        editDescription = task.content.description.toString()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text(
                        text = "編集",
                        fontFamily = fontFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}
