package by.intervale.akella266.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.intervale.akella266.todolist.R;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.Initializer;
import by.intervale.akella266.todolist.data.interfaces.Repository;
import by.intervale.akella266.todolist.data.local.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.data.local.LocalRepo;

public class FragmentToday extends Fragment {

    private Repository mRepo;
    private RecyclerView mCurrentRecycler;
    private RecyclerView mCompletedRecycler;
    private TasksAdapter mCurrentAdapter;
    private TasksAdapter mCompletedAdapter;
    private CardView cardCurrent;
    private CardView cardComplited;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        mRepo = Initializer.getTasksLocal();

        cardCurrent = (CardView) view.findViewById(R.id.card_current_tasks);
        cardComplited = (CardView) view.findViewById(R.id.card_complited_tasks);
        mCurrentRecycler = (RecyclerView) view.findViewById(R.id.recyclerView_current_tasks);
        mCurrentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCompletedRecycler = view.findViewById(R.id.recyclerView_completed_tasks);
        mCompletedRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        if(mCurrentAdapter == null){
            mCurrentAdapter = new TasksAdapter(mRepo.query(new GetCurrentTasksSpecification()));
            mCurrentRecycler.setAdapter(mCurrentAdapter);
        }
        if(mCompletedAdapter == null){
            mCompletedAdapter = new TasksAdapter(mRepo.query(new GetCompletedTaskSpecification()));
            mCompletedRecycler.setAdapter(mCompletedAdapter);
        }

        mCurrentAdapter.setTasks(mRepo.query(new GetCurrentTasksSpecification()));
        mCompletedAdapter.setTasks(mRepo.query(new GetCompletedTaskSpecification()));
        mCurrentAdapter.notifyDataSetChanged();
        mCompletedAdapter.notifyDataSetChanged();
    }
}
