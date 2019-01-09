package template.com.formtemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import activity.TemplateActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, TemplateActivity.class);
        if(v.getId() == R.id.gxy_zx)
            intent.putExtra("templateName", "高血压专项");
        else if(v.getId() == R.id.gxy_pg)
            intent.putExtra("templateName", "高血压评估");
        else if(v.getId() == R.id.gxy_sf)
            intent.putExtra("templateName", "高血压随访");
        startActivity(intent);
    }
}
