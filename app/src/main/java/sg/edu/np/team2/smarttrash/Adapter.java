package sg.edu.np.team2.smarttrash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Creating a Adapter for the HOME NEWS PAGE
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Items> mList;

    public Adapter(ArrayList<Items> List) {
        mList = List;
    }

    //CREATE A VIEW HOLDER TO RENDER THE LIST VIEW..
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    //GET THE POSITION OF NEWS
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items currentItem = mList.get(position);


        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.mImageView.setImageResource(currentItem.getmImageResource());
    }

    //Count the num item in the NEWS PAGE
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //Putting the images and texts together
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }

    }
}
