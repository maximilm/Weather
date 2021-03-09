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
    School school= new School();
    Teacher x = new Teacher("1" , "3", 2, "34", "323r");
    Button docs, elJur, teachers, button_classes_back, button_classes_generate, adding, button_add, button_continue;
    TableLayout docstable, table_eljur;
    String id;
    LinearLayout addingLay, choose_class_lay;
    RelativeLayout startLay, docsLay, tableDocsLay, ElJurLay;
    Spinner spinner_classes, spinner_participants, spinner_clas, spinner_sub;
    EditText text_fullName, text_phone, text_ID, text_position, text_qualification, text_age, text_parent1fio, text_parent1phone, text_parent2fio, text_parent2phone;

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
