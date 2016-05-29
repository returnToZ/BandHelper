package james.com.mag1c_band.ChartFile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import james.com.mag1c_band.R;
import james.com.mag1c_band.UI.ChatActivity;

public class ChartActivity extends Activity{
    private Button chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        chart = (Button)findViewById(R.id.chart);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DynamicChart.class);
                startActivity(intent);
            }
        });
    }
}
