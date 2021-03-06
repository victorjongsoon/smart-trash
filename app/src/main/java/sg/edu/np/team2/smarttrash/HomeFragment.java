package sg.edu.np.team2.smarttrash;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TextView usernameTextView;
    private FirebaseAuth auth;
    private String username;
    private View view;
    private DatabaseReference databaseReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_home, container, false); //pass the correct layout name for the fragment
        Context thiscontext;
        thiscontext = container.getContext();
        usernameTextView = view.findViewById(R.id.usernameTextView);
        //11
        ArrayList<Items> List = new ArrayList<>();
        List.add(new Items(R.drawable.ic_android1, "Recycling Methods", "How many recycling methods do you know? Click here to find out now!"));
        List.add(new Items(R.drawable.ic_android2, "What is Recycling?", "Why do we need to recycle?"));
        List.add(new Items(R.drawable.ic_android3, "Global Warming", "What are the cause of global warming?"));
        List.add(new Items(R.drawable.ic_android4, "How global warming is like Nuclear war?", "Click here to find out now!"));
        List.add(new Items(R.drawable.ic_android5, "UPDATE VERSION 1.5", "Read the updates now!"));
        List.add(new Items(R.drawable.ic_android6, "We are all responsible", "We should all do our part. Click here to find out more now!"));
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(thiscontext);
        mAdapter = new Adapter(List);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        //11
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(auth.getCurrentUser().getUid()).child("name");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = (String) dataSnapshot.getValue();

                usernameTextView.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;
    }
}
