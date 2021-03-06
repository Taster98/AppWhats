package ml.luiggi.appwhats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListRecycleViewHolder> {
    private ArrayList<Chat> chatList = new ArrayList<>();
    public ChatListAdapter(ArrayList<Chat> uList){
        chatList = uList;
    }

    @NonNull
    @Override
    public ChatListRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,null,false);
        /*RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);*/

        ChatListRecycleViewHolder urvh = new ChatListRecycleViewHolder(layoutView);
        return urvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatListRecycleViewHolder holder, final int position) {
        holder.mTitle.setText(chatList.get(position).getChatId());

        holder.mLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatId", chatList.get(holder.getAdapterPosition()).getChatId());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatListRecycleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public LinearLayout mLayout;
        public ChatListRecycleViewHolder(View view){
            super(view);
            mTitle = view.findViewById(R.id.title);
            mLayout = view.findViewById(R.id.layout);
        }
    }
}
