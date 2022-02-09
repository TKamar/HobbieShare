package com.example.hobbieshare.Classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hobbieshare.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Hobby_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<Hobby> allHobbies = new ArrayList<>();
    private HobbyEventItemClickListener hobbyEventItemClickListener;

    public interface HobbyEventItemClickListener {
        void hobbyItemClicked(Hobby hobby, int position);

    }

    public Hobby_Adapter(FragmentActivity activity, ArrayList<Hobby> allHobbies){
        this.activity = activity;
        this.allHobbies = allHobbies;
    }

    public Hobby_Adapter setHobbyEventItemClickListener (HobbyEventItemClickListener hobbyEventItemClickListener) {
        this.hobbyEventItemClickListener = hobbyEventItemClickListener;
        return this;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_hobbies, parent, false);
        return new HobbyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HobbyViewHolder hobbyViewHolder = (HobbyViewHolder) holder;
        Hobby hobby = getItem(position);

        hobbyViewHolder.myHobbies_hobby.setText(hobby.getTitle());
        hobbyViewHolder.myHobbies_description.setText(hobby.getDescription());

    }

    @Override
    public int getItemCount() {
        return allHobbies.size();
    }

    private Hobby getItem(int position) {
        return allHobbies.get(position);
    }



    public class HobbyViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView myHobbies_hobby;
        private MaterialTextView myHobbies_description;

        public HobbyViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hobbyEventItemClickListener.hobbyItemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

        private void findViews() {
            myHobbies_hobby = itemView.findViewById(R.id.list_LBL_hobby);
            myHobbies_description = itemView.findViewById(R.id.list_LBL_description);
        }
    }
}
