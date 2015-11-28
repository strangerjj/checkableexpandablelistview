package com.example.checkableexpandablelistview;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListView;

//import com.example.aexpandablelist.R;


public class MainActivity extends AppCompatActivity {

    ArrayList<Group> groups;
    ExpandableListView listView;
    EListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groups = new ArrayList<Group>();
        getJSONObject();
        listView = (ExpandableListView) findViewById(R.id.listView);
        adapter = new EListAdapter(this, groups);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(adapter);
    }

    /** 解悉 JSON 字串 */
    private void getJSONObject() {
        String jsonStr = "{'CommunityUsersResult':[{'CommunityUsersList':[{'fullname':'a111','userid':11,'username':'a1'}"
                + ",{'fullname':'b222','userid':12,'username':'b2'}],'id':1,'title':'人事部'},{'CommunityUsersList':[{'fullname':"
                + "'c333','userid':13,'username':'c3'},{'fullname':'d444','userid':14,'username':'d4'},{'fullname':'e555','userid':"
                + "15,'username':'e5'}],'id':2,'title':'開發部'}]}";

        try {
            JSONObject CommunityUsersResultObj = new JSONObject(jsonStr);
            JSONArray groupList = CommunityUsersResultObj.getJSONArray("CommunityUsersResult");

            for (int i = 0; i < groupList.length(); i++) {
                JSONObject groupObj = (JSONObject) groupList.get(i);
                Group group = new Group(groupObj.getString("id"), groupObj.getString("title"));
                JSONArray childrenList = groupObj.getJSONArray("CommunityUsersList");

                for (int j = 0; j < childrenList.length(); j++) {
                    JSONObject childObj = (JSONObject) childrenList.get(j);
                    Child child = new Child(childObj.getString("userid"), childObj.getString("fullname"),
                            childObj.getString("username"));
                    group.addChildrenItem(child);
                }

                groups.add(group);
            }
        } catch (JSONException e) {
            Log.d("allenj", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}


