package sg.edu.np.team2.smarttrash;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RewardFragment extends Fragment {
    List<Reward> lstReward;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        view = lf.inflate(R.layout.fragment_reward, container, false);
        Context thiscontext;
        thiscontext = container.getContext();
        lstReward = new ArrayList<>();
        lstReward.add(new Reward("Huawei P30 PRO", 200, R.drawable.huawei));
        lstReward.add(new Reward("IPhone XR", 200, R.drawable.iphone));
        lstReward.add(new Reward("MACBOOK PRO", 200, R.drawable.macbook));
        lstReward.add(new Reward("NIKE SHOE", 200, R.drawable.nike));
        lstReward.add(new Reward("$100 CASH", 200, R.drawable.money));

        RecyclerView myrv = view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(thiscontext, lstReward);
        myrv.setLayoutManager(new GridLayoutManager(thiscontext, 3));
        myrv.setAdapter(myAdapter);
        return view;


    }

}
