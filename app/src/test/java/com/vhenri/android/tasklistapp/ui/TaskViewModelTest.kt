package com.vhenri.android.tasklistapp.ui

import com.vhenri.android.tasklistapp.data.TaskDate
import com.vhenri.android.tasklistapp.util.TestUtil
import com.vhenri.android.tasklistapp.util.observeOnce
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TaskViewModelTest: BaseTest() {

    private val viewModel: TaskViewModel by lazy { TaskViewModel() }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun ensureInputsReset() {
        viewModel.currentInputTaskTitle.observeOnce {
            assertNull(it)
        }
        viewModel.currentInputTaskDesc.observeOnce {
            assertNull(it)
        }
        viewModel.currentInputTaskDate.observeOnce {
            assertNull(it)
        }
    }

    @Test
    fun addToTaskListTest() {
        val expectedAppStateValue = TestUtil.navDestination_TaskView()
        val emptyTaskItem = TestUtil.defaultTaskItem()
        runBlocking {
            viewModel.addTaskToList()
        }
        viewModel.listOfTasks.observeOnce {
            assertNotNull(it[0])
            assertNotNull(it[0].id)
            assertEquals(emptyTaskItem.title, it[0].title)
            assertEquals(emptyTaskItem.desc, it[0].desc)
            assertEquals(emptyTaskItem.date, it[0].date)
        }
        viewModel.appState.observeOnce {
            assertEquals(expectedAppStateValue, it)
        }
        ensureInputsReset()
    }

    @Test
    fun getTaskDetailTest() {
        val defaultTaskItem = TestUtil.defaultTaskItem()
        val listOfTaskWithDefaultValues = arrayListOf(defaultTaskItem)
        viewModel.listOfTasks.postValue(listOfTaskWithDefaultValues)
        runBlocking {
            viewModel.getTaskDetails("testId")
        }
        viewModel.currentEditedTaskData.observeOnce {
            assertEquals(defaultTaskItem, it)
        }
        viewModel.currentEditedTaskIndex.observeOnce {
            assertEquals(0, it)
        }
        viewModel.currentInputTaskTitle.observeOnce {
            assertEquals(defaultTaskItem.title, it)
        }
        viewModel.currentInputTaskDesc.observeOnce {
            assertEquals(defaultTaskItem.desc, it)
        }
        viewModel.currentInputTaskDate.observeOnce {
            assertEquals(defaultTaskItem.date, it)
        }
    }

    @Test
    fun updateTaskTest() {
        val defaultTaskItem = TestUtil.defaultTaskItem()
        val updatedTaskItem = TestUtil.updatedTaskItem()
        val expectedAppStateValue = TestUtil.navDestination_TaskView()

        viewModel.listOfTasks.postValue(arrayListOf(defaultTaskItem))
        viewModel.currentEditedTaskIndex.postValue(0)
        viewModel.currentEditedTaskData.postValue(updatedTaskItem)
        viewModel.currentInputTaskTitle.postValue(updatedTaskItem.title)
        viewModel.currentInputTaskDesc.postValue(updatedTaskItem.desc)
        viewModel.currentInputTaskDate.postValue(updatedTaskItem.date)

        runBlocking {
            viewModel.updateTask()
        }
        viewModel.appState.observeOnce {
            assertEquals(expectedAppStateValue, it)
        }
        viewModel.listOfTasks.observeOnce {
            assertEquals(arrayListOf(updatedTaskItem), it)
        }
        ensureInputsReset()
    }

    @Test
    fun navigateToCreateNewTaskDetailTest() {
        val expectedValue = TestUtil.navDestination_TaskDetail()
        runBlocking {
            viewModel.navigateToCreateNewTaskDetail()
        }
        viewModel.appState.observeOnce {
            assertEquals(expectedValue, it)
            assertNull(it.bundle)
        }
    }

    @Test
    fun navigateToEditTaskDetailTest() {
        val expectedValue = TestUtil.navDestination_TaskDetail()
        runBlocking {
            viewModel.navigateToEditTaskDetail("test")
        }
        viewModel.appState.observeOnce {
            assertEquals(expectedValue.appState, it.appState)
            assertNotNull(it.bundle)
        }
    }

    @Test
    fun formatDateStringTest() {
        val expectedValue = TestUtil.formattedDate()
        runBlocking {
           val formattedDate =  viewModel.formatDateString(TaskDate(2021,1,1))
            assertEquals(expectedValue, formattedDate)
        }
    }
}