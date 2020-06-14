package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    Context context;
    UserData data;
    String username;
    ArrayList<Integer> levelList = new ArrayList<>();
    ArrayList<Integer> scoreList = new ArrayList<>();

    public CustomScoreAdaptor(Context c, UserData userdata){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        this.data = userdata;
        this.context = c;
        levelList = userdata.getLevels();
        scoreList = userdata.getScores();
        username = userdata.getMyUserName();
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select, parent, false);
        return new CustomScoreViewHolder(v);
    }

    public void onBindViewHolder(CustomScoreViewHolder holder, final int position) {

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */
        String lvl = String.valueOf(levelList.get(position));
        String score = String.valueOf(scoreList.get(position));

        holder.level.setText("Level: " + lvl);
        holder.score.setText("Highest Score: " + score);

        Log.v(TAG, FILENAME + ": Load level " + position + " for: " + username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gamepage = new Intent(context, Main4Activity.class);
                gamepage.putExtra("Level", levelList.get(position));
                gamepage.putExtra("Username", username);
                context.startActivity(gamepage);

            }
        });
    }
    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */
        return levelList.size();
    }
}
