package activity.command;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ZytzActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setBackground(getResources().getDrawable(template.com.form.R.mipmap.ic_launcher));
        setContentView(imageView);
        setTitle("中医体质");
    }
}
