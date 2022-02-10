package com.example.hobbieshare.Classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hobbieshare.R;
import com.google.android.material.imageview.ShapeableImageView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hobby_list, parent, false);
        return new HobbyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HobbyViewHolder hobbyViewHolder = (HobbyViewHolder) holder;
        Hobby hobby = getItem(position);

        hobbyViewHolder.myHobbies_hobby.setText(hobby.getTitle());
        hobbyViewHolder.myHobbies_description.setText(hobby.getDescription());
        Glide.with(activity).load(getImageCase(hobby)).into(hobbyViewHolder.myHobbies_img);

    }

    public String getImageCase(Hobby hobby){
        String category = hobby.getCategoryOfEvent();
        String url = "";
        switch (category){
            case "ספורט":
                url ="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Sport_balls.svg/800px-Sport_balls.svg.png";
                break;
            case "מוזיקה":
                url = "data:image/jpeg;base64,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";
                break;
            case "אוכל":
                url ="https://cdn2.vectorstock.com/i/1000x1000/24/66/fast-food-icons-vector-28132466.jpg";
                break;
            case "משחקים":
                url ="https://i.pinimg.com/originals/5e/22/86/5e2286e02a8d3a65558ad3adf7534670.jpg";
                break;
            case "אומנות":
                url ="https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Circle-icons-art.svg/1024px-Circle-icons-art.svg.png";
                break;
            case "אוספים":
                url ="http://images.mini-ielts.com/images/1/11/collecting-hobbies.jpg";
                break;
            case "למידה ופיתוח אישי":
                url ="https://miro.medium.com/max/800/1*1kitK_m3VWlmaxZivkkFQQ.jpeg";
                break;
            case "אחר":
                url ="https://in2english.net/wp-content/uploads/2020/11/hobbies-and-interests2.jpg";
                break;
            default:
                break;

        }
        return url;
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
        private ShapeableImageView myHobbies_img;

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
            myHobbies_img = itemView.findViewById(R.id.list_IMG_image);
        }
    }

    @Override
    public String toString() {
        return "Hobby_Adapter{" +
                "activity=" + activity +
                ", allHobbies=" + allHobbies +
                ", hobbyEventItemClickListener=" + hobbyEventItemClickListener +
                '}';
    }
}
