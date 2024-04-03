package com.example.weightconversion;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner sourceUnitSpinner, destUnitSpinner;
    EditText valueToConvert;
    Button convertButton;
    TextView convertedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destUnitSpinner = findViewById(R.id.destUnitSpinner);
        valueToConvert = findViewById(R.id.valueToConvert);
        convertButton = findViewById(R.id.convertButton);
        convertedValue = findViewById(R.id.convertedValue);

        // Populate spinners with unit options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        destUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        // Get the selected units and the value to convert from the UI elements
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destUnit = destUnitSpinner.getSelectedItem().toString();
        String valueString = valueToConvert.getText().toString();

        // Check if the input value is empty or not a valid number
        if (valueString.isEmpty()) {
            Toast.makeText(this, "Please enter a value to convert.", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(valueString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform the conversion
        double result = convertValue(sourceUnit, destUnit, value);

        // Display the result
        convertedValue.setText(String.format("%.2f", result) + " " + destUnit);
    }

    private double convertValue(String sourceUnit, String destUnit, double value) {
        switch (sourceUnit) {
            case "Inch":
                if (destUnit.equals("Cm")) return value * 2.54;
                break;
            case "Foot":
                if (destUnit.equals("Cm")) return value * 30.48;
                break;
            case "Yard":
                if (destUnit.equals("Cm")) return value * 91.44;
                break;
            case "Mile":
                if (destUnit.equals("Km")) return value * 1.60934;
                break;
            case "Pound":
                if (destUnit.equals("Kg")) return value * 0.453592;
                break;
            case "Ounce":
                if (destUnit.equals("G")) return value * 28.3495;
                break;
            case "Ton":
                if (destUnit.equals("Kg")) return value * 907.185;
                break;
            case "Celsius":
                if (destUnit.equals("Fahrenheit")) return (value * 1.8) + 32;
                if (destUnit.equals("Kelvin")) return value + 273.15;
                break;
            case "Fahrenheit":
                if (destUnit.equals("Celsius")) return (value - 32) / 1.8;
                break;
            case "Kelvin":
                if (destUnit.equals("Celsius")) return value - 273.15;
                break;
        }
        return value; // Return the input value by default if no conversion is applicable
    }


}
