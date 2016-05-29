package james.com.mag1c_band.ChartFile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import james.com.mag1c_band.R;

public class TableActivity extends Activity {
    private LinearLayout linearLayout;
    private ColorLevelLineUtils mLineUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_table);
        linearLayout = (LinearLayout)findViewById(R.id.table_space);
        mLineUtils = new ColorLevelLineUtils(this.getApplicationContext());
        linearLayout.addView(mLineUtils.initLineGraphView());
    }
}
