package com.example.haryono.lowkost.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.haryono.lowkost.Model.Message;
import com.example.haryono.lowkost.Model.PhotoModel;
import com.example.haryono.lowkost.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class ChatActivity extends AppCompatActivity {


    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<Message> adapter;
    RelativeLayout activity_chat;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.menu_sign_out)
//        {
//            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Snackbar.make(activity_main,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//        }
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(activity_chat,"Successfully signed in.Welcome!", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            }
            else{
                Snackbar.make(activity_chat,"We couldn't sign you in.Please try again later", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity_chat = (RelativeLayout)findViewById(R.id.activity_main);

        //Add Emoji
        emojiButton = (ImageView)findViewById(R.id.emoji_button);
        submitButton = (ImageView)findViewById(R.id.submit_button);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getApplicationContext(),activity_chat,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().push().setValue(new Message(emojiconEditText.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });

        //Check if not sign-in then navigate Signin page
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else
        {
            Snackbar.make(activity_chat,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            //Load content
            displayChatMessage();
        }


    }



    private void displayChatMessage() {

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<Message>(this,Message.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference())
        {
            @Override
            protected void populateView(View v, Message model, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }
}
//    private EditText editMessage;
//    private DatabaseReference mDatabase;
//    private RecyclerView mMessageList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        editMessage = (EditText) findViewById(R.id.editMessage);
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
//        mMessageList = (RecyclerView)findViewById(R.id.messageRec);
//        mMessageList.setHasFixedSize(true);
//        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(this);
//        linerLayoutManager.setStackFromEnd(true);
//        mMessageList.setLayoutManager(linerLayoutManager);
//    }
//
//    public void sendButtonClicked(View view){
//        final String messageValue= editMessage.getText().toString().trim();
//        if (!TextUtils.isEmpty(messageValue)){
//            final DatabaseReference newPost = mDatabase.push();
//            newPost.child("content").setValue(messageValue);
//        }
//    }
//
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Message,MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
//
//                Message.class, R.layout.singlemessagelayout,MessageViewHolder.class,mDatabase
//        ){
//            @Override
//            protected void populateViewHolder (MessageViewHolder viewHolder, Message model, int position){
//                viewHolder.setContent(model.getContent());
//            }
//        };
//        mMessageList.setAdapter(FBRA);
//    }
//
//    public static class MessageViewHolder extends RecyclerView.ViewHolder{
//
//        View mView;
//        public MessageViewHolder(View itemView) {
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setContent(String content){
//            TextView message_content = (TextView) mView.findViewById(R.id.messageText);
//            message_content.setText(content);
//        }
//        public void setUsername(String email){
//            TextView message_content = (TextView) mView.findViewById(R.id.usernameText);
//            message_content.setText(email);
//        }
//    }
//}
