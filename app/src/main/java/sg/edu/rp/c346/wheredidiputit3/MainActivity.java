package sg.edu.rp.c346.wheredidiputit3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etItem;
    EditText etPlace;
    Button btnAdd;
    ListView lvItem;


    ArrayList<Item> alItem = new ArrayList<Item>();
    ArrayAdapter aaItem;
    String item = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItem = (EditText) findViewById(R.id.editTextItem);
        etPlace = (EditText) findViewById(R.id.editTextPlace);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        lvItem = (ListView) findViewById(R.id.listViewItem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strItem = etItem.getText().toString();
                String strPlace = etPlace.getText().toString();

                DBHelper db = new DBHelper(MainActivity.this);

                db.insertItem(strItem,strPlace);
//                db.insertPlace(strPlace);
                db.close();

                DBHelper dbRetrieve = new DBHelper(MainActivity.this);
                alItem = dbRetrieve.getItem();
                dbRetrieve.close();

                aaItem = new ArrayAdapter<Item>(MainActivity.this, android.R.layout.simple_list_item_1, alItem);

                lvItem.setAdapter(aaItem);
                aaItem.notifyDataSetChanged();

//                DBHelper dbRetrievePlace = new DBHelper(MainActivity.this);
//                alPlace = dbRetrievePlace.getPlace();
//                dbRetrievePlace.close();
//
//                aaPlace = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, alPlace);
//
//                lvPlace.setAdapter(aaPlace);
//                aaPlace.notifyDataSetChanged();

                strItem = "";
                strPlace = "";

            }
        });

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                Item data = alItem.get(position);
                intent.putExtra("data",data);
                startActivity(intent);

            }
        });
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Inflate the input.xml layout file
//                LayoutInflater inflater = (LayoutInflater).getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setIcon(android.R.drawable.ic_dialog_alert);


                b.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DBHelper db = new DBHelper(MainActivity.this);
                        db.deleteItem(alItem.get(position).getId());
                        db.close();
                        alItem.remove(position);
                        Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                        aaItem.notifyDataSetChanged();

                    }
                });
                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                b.show();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aaItem.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbRetrieve = new DBHelper(MainActivity.this);
        alItem = dbRetrieve.getItem();
//            alPlace = dbRetrieve.getPlace();
        dbRetrieve.close();
        aaItem = new ArrayAdapter<Item>(MainActivity.this, android.R.layout.simple_list_item_1, alItem);

        lvItem.setAdapter(aaItem);
        aaItem.notifyDataSetChanged();

    }
}
