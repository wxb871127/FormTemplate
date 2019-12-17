package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import template.com.templatebusiness.R;
import template.view.TemplateView;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        TemplateView templateView = findViewById(R.id.template_view);
        templateView.addFlags(TemplateView.FLAG_EXCEPTION | TemplateView.FLAG_REFUSE);
        templateView.initTemplate("test.xml");
    }
}
