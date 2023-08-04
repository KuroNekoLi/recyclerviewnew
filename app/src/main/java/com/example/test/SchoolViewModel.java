package com.example.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SchoolViewModel extends ViewModel {
    private final MutableLiveData<List<Student>> selectedClassStudents = new MutableLiveData<>();
    private final HashMap<String, List<Student>> schoolStudents = new HashMap<>();

    // 初始化全校名單
    public void initializeSchoolStudents() {
        schoolStudents.put("1班", Arrays.asList(new Student("小明", "1"), new Student("小美", "2"), new Student("小華", "3")));
        schoolStudents.put("2班", Arrays.asList(new Student("大明", "1"), new Student("大美", "2"), new Student("小胖", "3"), new Student("小李", "4")));
        schoolStudents.put("3班", Arrays.asList(new Student("小智", "1"), new Student("小祥", "2"), new Student("老胡", "3"), new Student("小芬", "4"), new Student("大胖", "5")));
    }

    // 更新班級名單（例如增加學生）
    public void updateClassStudents(String className, List<Student> newClassStudents) {
        schoolStudents.put(className, newClassStudents);

        // 如果更新的班級是當前選中的班級，則更新LiveData
        if (selectedClassStudents.getValue() == schoolStudents.get(className)) {
            selectedClassStudents.setValue(newClassStudents);
        }
    }

    // 選擇班級（通過選項卡）
    public void selectClass(String className) {
        selectedClassStudents.setValue(schoolStudents.get(className));
    }

    // 獲取選中班級的LiveData
    public LiveData<List<Student>> getSelectedClassStudents() {
        return selectedClassStudents;
    }
}

