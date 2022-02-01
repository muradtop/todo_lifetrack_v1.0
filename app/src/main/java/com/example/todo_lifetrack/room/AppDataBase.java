package com.example.todo_lifetrack.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todo_lifetrack.model.TaskModel;


@Database(entities = {TaskModel.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
