package ml.luiggi.appwhats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class FindUserActivity extends AppCompatActivity {
    private RecyclerView mUserList;
    private RecyclerView.Adapter mUserListAdapter;
    private RecyclerView.LayoutManager mUserListLayoutManager;
    ArrayList<User> userList, contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        contactList = new ArrayList<>();
        userList = new ArrayList<>();
        initializeRecyclerView();
        getContactList();
    }

    @SuppressLint("WrongConstant")
    private void initializeRecyclerView() {
        mUserList = findViewById(R.id.userList);
        //elimino la dimensione fissata e i nested scrolls
        mUserList.setNestedScrollingEnabled(false);
        mUserList.setHasFixedSize(false);

        mUserListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
        mUserList.setLayoutManager(mUserListLayoutManager);

        //Imposto l'adattatore
        mUserListAdapter = new UserListAdapter(userList);
        mUserList.setAdapter(mUserListAdapter);
    }

    private  String getCountryISO(){
        String iso = null;

        TelephonyManager tmngr = (TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        if(tmngr.getNetworkCountryIso() != null){
            if(!tmngr.getNetworkCountryIso().toString().equals(""))
                iso = tmngr.getNetworkCountryIso();
        }
        return Iso2Phone.getPhone(iso);
    }

    private void getContactList(){
        String ISOPrefix = getCountryISO();

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while(phones.moveToNext()){
            String curName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String curNumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            curNumb = curNumb.replace(" ","");
            curNumb = curNumb.replace("-","");
            curNumb = curNumb.replace("(","");
            curNumb = curNumb.replace(")","");

            if(!String.valueOf(curNumb.charAt(0)).equals("+"))
                curNumb=ISOPrefix+curNumb;

            User mContacts = new User("",curName,curNumb);
            contactList.add(mContacts);
            getUserDetails(mContacts);
        }
    }

    private void getUserDetails(User mContacts) {
        DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user");
        Query query = mUserDB.orderByChild("phone").equalTo(mContacts.getPhoneNum());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String phone = "", name="";
                    for(DataSnapshot childSnapshot : snapshot.getChildren()){
                        if(childSnapshot.child("phone").getValue() != null)
                            phone=childSnapshot.child("phone").getValue().toString();
                        if(childSnapshot.child("name").getValue() != null)
                            name=childSnapshot.child("name").getValue().toString();

                        User mUser = new User(childSnapshot.getKey(),name,phone);
                        if(name.equals(phone)){
                            for(User mUserIt : contactList){
                                if(mUserIt.getPhoneNum().equals(mUser.getPhoneNum())){
                                    mUser.setName(mUserIt.getName());
                                }
                            }
                        }

                        userList.add(mUser);
                        mUserListAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}