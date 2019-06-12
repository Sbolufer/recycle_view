package esmyfirstandroidproject.covalco.recycle_view_example;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recycleViewUser;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        Log.e("HelloMelo", getClass().getName() + " Melón !!!!!!");

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean("key_name", true); // Storing boolean - true/false
        editor.putString("key_name", "string value"); // Storing string
        //editor.putInt("key_name", 1); // Storing integer
        //editor.putFloat("key_name", 2); // Storing float
        //editor.putLong("key_name", 3); // Storing long

        editor.commit(); // commit changes
        String sh="";
        try {
            sh = pref.getString("key_name", null); // getting String

        } catch (Exception ex)
        {
            Log.e("Error-MainActivity", ex.getMessage());
        }

        Toast toast = Toast.makeText(getApplicationContext(),BuildConfig.FLAVOR + " " +
                      sh + " " + BuildConfig.ENDPOINT, Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();
        recycleViewUser = (RecyclerView) findViewById(R.id.reyclerViewUser);

        recycleViewUser.setHasFixedSize(true);

        recycleViewUser.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new UserAdapter(getData());
        recycleViewUser.setAdapter(mAdapter);
    }

    public List<UserModel> getData() {
        List<UserModel> userModels = new ArrayList<>();
        userModels.add(new UserModel("Gustavo"));
        userModels.add(new UserModel("Daniel"));
        userModels.add(new UserModel("Laia"));
        userModels.add(new UserModel("Emmma"));
        userModels.add(new UserModel("Sue"));
        userModels.add(new UserModel("Sue"));
        userModels.add(new UserModel("King"));
        userModels.add(new UserModel("Duna"));

        for (int i = 1; i < 15; i++){
            userModels.add(new UserModel("Name" + i));
        }
        return userModels;
    }
}
