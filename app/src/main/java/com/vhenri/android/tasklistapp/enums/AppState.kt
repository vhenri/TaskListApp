package com.vhenri.android.tasklistapp.enums

import android.os.Bundle

enum class AppState {
    TASK_VIEW,
    TASK_DETAIL
}

data class NavDestinationData(var appState: AppState, var bundle: Bundle? = null)