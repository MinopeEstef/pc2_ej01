package com.example.ejercicio01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name,dateBirth,cash;
     RadioGroup radioGroup;
     Button btnCreate,btnClean;
     TextView result;
     CheckBox checkBox1,checkBox2,checkBox3,checkBox4;

    private String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        dateBirth = findViewById(R.id.dateBirth);
        cash = findViewById(R.id.cash);
        radioGroup = findViewById(R.id.radioGroup);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        btnCreate = findViewById(R.id.btnCreate);
        btnClean = findViewById(R.id.btnClean);
        result = findViewById(R.id.result);

        ArrayList<String> opcionesSeleccionadas = new ArrayList<String>();
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked()) {
                    opcionesSeleccionadas.add(checkBox1.getText().toString());
                } else {
                    opcionesSeleccionadas.remove(checkBox1.getText().toString());
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked()) {
                    opcionesSeleccionadas.add(checkBox2.getText().toString());
                } else {
                    opcionesSeleccionadas.remove(checkBox2.getText().toString());
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox3.isChecked()) {
                    opcionesSeleccionadas.add(checkBox3.getText().toString());
                } else {
                    opcionesSeleccionadas.remove(checkBox3.getText().toString());
                }
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox3.isChecked()) {
                    opcionesSeleccionadas.add(checkBox4.getText().toString());
                } else {
                    opcionesSeleccionadas.remove(checkBox4.getText().toString());
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = name.getText().toString();
                String fecha = dateBirth.getText().toString();
                Double sueldo = Double.parseDouble(cash.getText().toString());

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton:
                        genero = "Masculino";
                        break;
                    case R.id.radioButton2:
                        genero = "Femenino";
                        break;
                    default:
                        result.setText("No ha seleccionado una opción");
                        return;
                }

                String jsonString = null;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", nombre);
                    jsonObject.put("age", getAge(fecha));
                    jsonObject.put("cash", sueldo);
                    jsonObject.put("female", genero);
                    jsonObject.put("hobbies", opcionesSeleccionadas);
                    jsonString = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result.setText(getResult(jsonString));
                opcionesSeleccionadas.clear();
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.getText().clear();
                dateBirth.getText().clear();
                cash.getText().clear();
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                radioGroup.clearCheck();
                result.setText("");
                name.findFocus();
            }
        });

    }

    private String getResult(String  jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String nombre = jsonObject.getString("name");
            int edad = jsonObject.getInt("age");
            Double sueldo = jsonObject.getDouble("cash");
            String genero = jsonObject.getString("female");
            String actividades = jsonObject.getString("hobbies");

            // Mostrar los datos en un TextView
            final String TXT_RESULTADO = "Sr. %s , usted tiene  %s  años, recibe de sueldo %s soles es de genero" +
                    " %s y en sus tiempos libres le gusta %s .";
            return String.format(TXT_RESULTADO, nombre,edad,sueldo,genero,actividades);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getAge(String fechaNacimiento) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNac = sdf.parse(fechaNacimiento);
            Calendar fechaNacCalendar = Calendar.getInstance();
            fechaNacCalendar.setTime(fechaNac);

            Calendar fechaActualCalendar = Calendar.getInstance();

            int edad = fechaActualCalendar.get(Calendar.YEAR) - fechaNacCalendar.get(Calendar.YEAR);
            if (fechaActualCalendar.get(Calendar.MONTH) < fechaNacCalendar.get(Calendar.MONTH) ||
                    (fechaActualCalendar.get(Calendar.MONTH) == fechaNacCalendar.get(Calendar.MONTH) &&
                            fechaActualCalendar.get(Calendar.DAY_OF_MONTH) < fechaNacCalendar.get(Calendar.DAY_OF_MONTH))) {
                edad--;
            }

            return edad;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}