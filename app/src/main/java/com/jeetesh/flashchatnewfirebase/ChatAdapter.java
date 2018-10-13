package com.jeetesh.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Jeetesh on 7/9/2018.
 */


public class ChatAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotArrayList;
    private ChildEventListener mListener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("lets","on child added");
            mSnapshotArrayList.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public ChatAdapter(Activity act,DatabaseReference mdb,String dp){
        mActivity=act;
        mDatabaseReference=mdb.child("messages");
        mDatabaseReference.addChildEventListener(mListener);Log.d("lets","chatadap contructor");
        mDisplayName=dp;
        mSnapshotArrayList=new ArrayList<>();
    }

    static class ViewHolder{//innerclass
        TextView sender;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
       return mSnapshotArrayList.size();
    }

    @Override
    public Messenger getItem(int position) {
       DataSnapshot snapshot= mSnapshotArrayList.get(position);
         Log.d("lets","getitem");
        return snapshot.getValue(Messenger.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.chat_msg_row,parent,false);
            final ViewHolder holder=new ViewHolder();
            holder.sender=(TextView) convertView.findViewById(R.id.author);
            holder.body=(TextView) convertView.findViewById(R.id.message);
            holder.params=(LinearLayout.LayoutParams)holder.sender.getLayoutParams();
            convertView.setTag(holder);
        }
        final Messenger message=getItem(position);
      final  ViewHolder holder=(ViewHolder) convertView.getTag();
      boolean mai=message.getSender().equals(mDisplayName);
      setAppearance(mai,holder);
      String author=message.getSender();
      holder.sender.setText(author);
      String msg=message.getMsg();
      holder.body.setText(msg);

        return convertView;
    }
    public void clean(){
        mDatabaseReference.removeEventListener(mListener);
    }
    private void setAppearance(boolean who,ViewHolder holder){

        if(who){
            holder.params.gravity= Gravity.END;
            holder.sender.setTextColor(Color.GREEN);
            holder.body.setBackgroundResource(R.drawable.bubble2);
        }
        else {
            holder.params.gravity=Gravity.START;
            holder.sender.setTextColor(Color.BLUE);
            holder.body.setBackgroundResource(R.drawable.bubble1);
        }
        holder.sender.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }
}
