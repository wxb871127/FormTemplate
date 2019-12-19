package activity.command;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import template.com.templatebusiness.R;

public class XjActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        setContentView(imageView);
        setTitle("小节");
    }
}
