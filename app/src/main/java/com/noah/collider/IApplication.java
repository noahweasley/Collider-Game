package com.noah.collider;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class IApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static int taskCreatedCount;
    private static int taskResumedCount;
    private static int taskPausedCount;
    private static int taskStartedCount;
    private static int taskStoppedCount;
    private static int taskDestroyedCount;
    private static int tasksActive;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        ++taskCreatedCount;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        ++taskStartedCount;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        ++taskResumedCount;
        if (++tasksActive == 1) {
            Intent intent = new Intent(this.getApplicationContext(), MediaPlaybackService.class);
            startService(intent);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        ++taskPausedCount;
        if (--tasksActive <= 0) {
            stopService(new Intent(this.getApplicationContext(), MediaPlaybackService.class));
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        ++taskStoppedCount;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
     // no implementation
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        ++taskDestroyedCount;
    }

    public static int getTaskCreatedCount() {
        return taskCreatedCount;
    }

    public static void setTaskCreatedCount(int taskCreatedCount) {
        IApplication.taskCreatedCount = taskCreatedCount;
    }

    public static int getTaskResumedCount() {
        return taskResumedCount;
    }

    public void setTaskResumedCount(int taskResumedCount) {
        IApplication.taskResumedCount = taskResumedCount;
    }

    public static int getTaskPausedCount() {
        return taskPausedCount;
    }

    public static void setTaskPausedCount(int taskPausedCount) {
        IApplication.taskPausedCount = taskPausedCount;
    }

    public static int getTaskStartedCount() {
        return taskStartedCount;
    }

    public static void setTaskStartedCount(int taskStartedCount) {
        IApplication.taskStartedCount = taskStartedCount;
    }

    public static int getTaskStoppedCount() {
        return taskStoppedCount;
    }

    public static void setTaskStoppedCount(int taskStoppedCount) {
        IApplication.taskStoppedCount = taskStoppedCount;
    }

    public static int getTaskDestroyedCount() {
        return taskDestroyedCount;
    }

    public static void setTaskDestroyedCount(int taskDestroyedCount) {
        IApplication.taskDestroyedCount = taskDestroyedCount;
    }

    public static int getTasksActive() {
        return tasksActive;
    }

    public static void setTasksActive(int tasksActive) {
        IApplication.tasksActive = tasksActive;
    }
}
