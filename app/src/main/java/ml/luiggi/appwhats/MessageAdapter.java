package ml.luiggi.appwhats;

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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageRecycleViewHolder> {
    private ArrayList<Message> messageList = new ArrayList<>();
    public MessageAdapter(ArrayList<Message> uList){
        messageList = uList;
    }

    @NonNull
    @Override
    public MessageRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null,false);
        /*RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);*/

        MessageRecycleViewHolder urvh = new MessageRecycleViewHolder(layoutView);
        return urvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecycleViewHolder holder, final int position) {
        holder.mMessage.setText(messageList.get(position).getText());
        holder.mSender.setText(messageList.get(position).getSenderId());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageRecycleViewHolder extends RecyclerView.ViewHolder{
        TextView mMessage, mSender;
        LinearLayout mLayout;
        MessageRecycleViewHolder(View view){
            super(view);
            mLayout = view.findViewById(R.id.layout);
            mMessage = view.findViewById(R.id.message);
            mSender = view.findViewById(R.id.sender);
        }
    }
}
