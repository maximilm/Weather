import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {
    jghfughjghrughfugh
    asdfghjkl
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        docs = findViewById(R.id.docs);
        adding = findViewById(R.id.adding);
        elJur = findViewById(R.id.elJur);
        ElJurLay = findViewById(R.id.ElJurLay);
        startLay = findViewById(R.id.startLay);
        docsLay = findViewById(R.id.docsLay);
        addingLay = findViewById(R.id.addingLay);
        choose_class_lay = findViewById(R.id.choose_class_lay);
        tableDocsLay = findViewById(R.id.tableDocsLay);
        teachers = findViewById(R.id.teachers);
        startLay.setVisibility(View.VISIBLE);
        /*docs.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startLay.setVisibility(View.GONE);
                docsLay.setVisibility(View.VISIBLE);
            }
        });*/

    }
}
