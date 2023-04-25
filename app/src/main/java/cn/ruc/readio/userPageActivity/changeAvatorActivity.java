package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import cn.ruc.readio.R;

import cn.ruc.readio.databinding.ActivityChangeAvatorBinding;
public class changeAvatorActivity extends AppCompatActivity {

    private ActivityChangeAvatorBinding binding;
    private String[] spinnerArray = new String[]{"hi1","hi2","hi3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAvatorBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_change_avator);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerView.setAdapter(arrayAdapter);

        binding.spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("天啦噜！","出问题辣！");
                String res = parent.getItemAtPosition(position).toString();
                Toast.makeText(changeAvatorActivity.this,res,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}