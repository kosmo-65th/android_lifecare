package com.example.lifecare.Chating;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;
import com.example.lifecare.VO.MessageItem;
import com.example.lifecare.VO.Roomkey;
import com.example.lifecare.VO.UserVO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    EditText et;
    ListView listView;

    UserVO uservo = UserVO.getInstance();
    MessageItem messageItem = new MessageItem();

    ArrayList<MessageItem> MessageItem = new ArrayList<>();
    ArrayList<Roomkey> key = new ArrayList<>();
    ChatAdapter adapter;

    //Firebase Database 관리 객체참조변수
    FirebaseDatabase firebaseDatabase;

    //'chat'노드의 참조객체 참조변수
    DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //제목줄 제목글시를 닉네임으로(또는 채팅방)
        getSupportActionBar().setTitle(uservo.getId());

        et = findViewById(R.id.et);
        listView = findViewById(R.id.listview);
        adapter = new ChatAdapter(MessageItem,getLayoutInflater());
        listView.setAdapter(adapter);

        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference(uservo.getId());


        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                MessageItem messageItem = dataSnapshot.getValue(MessageItem.class);

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                MessageItem.add(messageItem);

                //리스트뷰를 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(MessageItem.size()-1); //리스트뷰의 마지막 위치로 스크롤 위치 이동

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*상단바 숨기기*/
        getSupportActionBar().hide();
    }

    public void clickSend(View view) {

        //firebase DB에 저장할 값들( 닉네임, 메세지, 시간)
        System.out.println(MessageItem.toString());

        //로그인된 아이디로 저장
        String nickName = uservo.getId();

        String message = et.getText().toString();

        //메세지 작성 시간 문자열로..
        Calendar calendar= Calendar.getInstance(); //현재 시간을 가지고 있는 객체
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE); //14:16

        //firebase DB에 저장할 값(MessageItem객체) 설정
        MessageItem messageItem = new MessageItem(nickName,message,time);

        //'char'노드에 MessageItem객체를 통해
        chatRef.push().setValue(messageItem);

        //EditText에 있는 글씨 지우기
        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
}