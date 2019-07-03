package cs2901.utec.chat_mobile;

import android.annotation.SuppressLint;
import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.content.Intent;
import org.json.JSONException;
import android.view.View;

public class IndexActivity extends AppCompatActivity {
    private List<JSONObject> contactslist = new ArrayList<>();
    private RecyclerView recyclerView;
    private Contacts cAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //Current User
        whoiam();
        //Other Users
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.contacts);

        cAdapter = new Contacts(contactslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        allusers();

    }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void whoiam() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/current",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        try {
                            String message = response.getString("username");
                            final TextView name = findViewById(R.id.currentUser);
                            name.setText(message);
                        }catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                },
                null
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    public void allusers() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/users",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contacts = response.getJSONArray("data");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject cont0 = contacts.getJSONObject(i);
                                contactslist.add(cont0);
                            }
                            cAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                },
                null
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
