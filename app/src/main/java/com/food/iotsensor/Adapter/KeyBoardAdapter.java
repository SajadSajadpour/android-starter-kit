package com.food.iotsensor.Adapter;



import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.food.iotsensor.InterFaces.KeyBoardCallBackListener;
import com.food.iotsensor.R;
import com.food.iotsensor.ViewUtility.FontTextView;


/**
 * @author Sajad Sajadpour
 * sajad.sajadpour@gmail.com
 * @since 08/06/2021
 */
public class KeyBoardAdapter extends RecyclerView.Adapter<KeyBoardAdapter.SimpleViewHolder> {
    public static final String[] keyBoardList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "CANCEL", "0","DEL"};
    public static final String[] characterList = {"", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ", "CANCEL", "0","DEL"};
    private Activity activity;
    private KeyBoardCallBackListener keyBoardCallBack;

    public KeyBoardAdapter(Activity activity, KeyBoardCallBackListener keyBoardCallBack) {
        this.activity = activity;
        this.keyBoardCallBack = keyBoardCallBack;

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View enterCommentView = LayoutInflater.from(activity).inflate(R.layout.item_keyboard, parent, false);
        return new SimpleViewHolder(enterCommentView);
    }


    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.keyboard_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyBoardCallBack.click(position);
            }
        });
        if(position==9){
            holder.keyboard_cancel.setVisibility(View.VISIBLE);
            holder.keyboard_cancel.setText("CANCEL");
            holder.text.setVisibility(View.GONE);
        }
        else if(position==10) {
            holder.number.setText(keyBoardList[position]);
            holder.character.setVisibility(View.GONE);
        }
        else if(position==11){
            holder.keyboard_del.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.GONE);

        }else{
            holder.number.setText(keyBoardList[position]);
            holder.character.setText(characterList [position]);
        }


    }

    @Override
    public int getItemCount() {
        return keyBoardList.length;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }


    class SimpleViewHolder extends RecyclerView.ViewHolder {


        TextView number;
        RelativeLayout keyboard_item;
        FontTextView character,keyboard_cancel;
        ImageView keyboard_del;
        LinearLayout text;

        SimpleViewHolder(View itemView) {
            super(itemView);
            number = (TextView)itemView.findViewById(R.id.number);
            character = (FontTextView)itemView.findViewById(R.id.character);
            keyboard_item=(RelativeLayout) itemView.findViewById(R.id.keyboard_item);
            keyboard_del=(ImageView)itemView.findViewById(R.id.keyboard_del);
            text=(LinearLayout)itemView.findViewById(R.id.text);
            keyboard_cancel = (FontTextView)itemView.findViewById(R.id.keyboard_cancel);
        }
    }
}


