package com.example.annexe19;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DecimalFormat;

public class PlacementActivity extends AppCompatActivity {

    private EditText champMontant;
    private NumberPicker numberPicker;
    private TextView labelReponse;
    private Button bouton;

    public DecimalFormat d = new DecimalFormat("0.00$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        champMontant =  findViewById(R.id.champMontant);
        numberPicker = findViewById(R.id.numberPicker);
        labelReponse =  findViewById(R.id.labelReponse);
        bouton = findViewById(R.id.bouton);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 12;
                return "" + temp;
            }
        };


        numberPicker.setFormatter(formatter);
        
        // 3 étapes
        Ecouteur ec = new Ecouteur();
        bouton.setOnTouchListener(ec);
    }


    //pour créer une boite de dialogue simple
    public void creerAlertDialog(String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(PlacementActivity.this);

        //on peut faire ca !!
        builder.setMessage(message)
                .setTitle("Erreur");


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class Ecouteur implements View.OnTouchListener {
        Placement placement;
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                try {
                    // peut lancer un NumberFormatException si on entre du texte au lieu d'un nombre
                    placement = new Placement(Double.parseDouble(champMontant.getText().toString()), (numberPicker.getValue())*12);
//                labelReponse.setText(" " + (placement.calculerMontantFinal()));
                    labelReponse.setText(d.format (placement.calculerMontantFinal()));

                } catch (NumberFormatException nfe) {
                    creerAlertDialog("Recommencer en entrant un montant valide?!");
                    champMontant.setText("");
                    champMontant.requestFocus();
                    champMontant.setHint("Entrer un nombre exemple: 1000");
                    labelReponse.setText("");
                } finally {
                    // toujours executer qu'il y ait une exception, qu'ils y en ait une encore dans les airs
                    // quil y en ait pas.
                }
            }

            return true;
        }
    }
}

// Convertion
// Double.parseDouble
// Integer.parseInt

// String.valueOf() ou toString()






