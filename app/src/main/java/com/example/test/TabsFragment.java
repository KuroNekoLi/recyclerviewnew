package com.example.test;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TabsFragment extends Fragment {

    private RecyclerView mainRecyclerView;
    private RecyclerView tabsRecyclerView;
    private SchoolViewModel schoolViewModel;
    private TabsAdapter tabsAdapter;
    private List<String> tabs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        mainRecyclerView = view.findViewById(R.id.main_recycler_view);
        tabsRecyclerView = view.findViewById(R.id.tabs_recycler_view);

        // 設置 mainRecyclerView 的 LayoutManager 為垂直方向
        LinearLayoutManager mainLayoutManager = new LinearLayoutManager(requireContext());
        mainRecyclerView.setLayoutManager(mainLayoutManager);

        schoolViewModel = new ViewModelProvider(requireActivity()).get(SchoolViewModel.class);
        schoolViewModel.initializeSchoolStudents();

        setupTabs();

        // 觀察選中的班級名單的變化
        schoolViewModel.getSelectedClassStudents().observe(getViewLifecycleOwner(), students -> {
            MainAdapter mainAdapter = new MainAdapter(students);
            mainRecyclerView.setAdapter(mainAdapter);
        });

        // 設置默認的選中選項卡
        tabsAdapter.setSelectedPosition(0);

        // 設置按鍵事件監聽器
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                int currentPosition = tabsRecyclerView.getChildAdapterPosition(tabsRecyclerView.getFocusedChild());
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    // 按右鍵時，選擇下一個選項卡
                    if (currentPosition < tabsAdapter.getItemCount() - 1) {
                        tabsAdapter.setSelectedPosition(currentPosition + 1);
                        schoolViewModel.selectClass(tabs.get(currentPosition + 1));
                    }
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    // 按左鍵時，選擇上一個選項卡
                    if (currentPosition > 0) {
                        tabsAdapter.setSelectedPosition(currentPosition - 1);
                        schoolViewModel.selectClass(tabs.get(currentPosition - 1));
                    }
                }
            }
            return false;
        });

        return view;
    }


    private void setupTabs() {
        // 初始化選項卡數據
        tabs = Arrays.asList("1班", "2班", "3班");

        // 創建選項卡適配器
        tabsAdapter = new TabsAdapter(requireContext(), tabs, position -> {
            // 根據選中的選項卡位置，更新選中的班級
            schoolViewModel.selectClass(tabs.get(position));
        });

        // 設置LinearLayoutManager為水平方向
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        tabsRecyclerView.setLayoutManager(layoutManager);

        // 設置選項卡的RecyclerView
        tabsRecyclerView.setAdapter(tabsAdapter);

        // 初始化首個選項卡的數據
        schoolViewModel.selectClass("1班");
    }
}
