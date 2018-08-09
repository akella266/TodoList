package by.intervale.akella266.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.todolist.adapters.TasksAdapter;
import by.intervale.akella266.todolist.data.local.specifications.GetCompletedTaskSpecification;
import by.intervale.akella266.todolist.data.local.specifications.GetCurrentTasksSpecification;
import by.intervale.akella266.todolist.utils.Initializer;

public class RecyclerFragment extends Fragment {

    public static final String BUNDLE_TYPE = "type";

    private Unbinder unbinder;


    @BindView(R.id.fragment_recycler)
    RecyclerView mRecycler;

    public RecyclerFragment() {}

    public static RecyclerFragment newInstance(TypeData type){
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_TYPE, type);
        RecyclerFragment fragment = new RecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        TypeData type = (TypeData) getArguments().getSerializable(BUNDLE_TYPE);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        TasksAdapter adapter = new TasksAdapter(getActivity(), null, null);
        switch (type){
            case ACTIVE:{
                adapter.setTasks(Initializer.getTasksLocal().query(new GetCurrentTasksSpecification()));
                break;
            }
            case COMPLETED:{
                adapter.setTasks(Initializer.getTasksLocal().query(new GetCompletedTaskSpecification()));
                break;
            }
        }

        mRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
