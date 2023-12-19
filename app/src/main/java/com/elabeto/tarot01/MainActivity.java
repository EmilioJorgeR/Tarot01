package com.elabeto.tarot01;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    final int NUMCARTAS = 22;
    private final ImageButton[] ibtnCartas = new ImageButton[NUMCARTAS];
    private final String[] nombreCarta = {"El Loco",
            "El Mago",
            "La Sacerdotisa",
            "La Emperatriz",
            "El Emperador",
            "El Papa",
            "Los Enamorados",
            "El Carro",
            "La Justicia",
            "El Ermitaño",
            "La Rueda de la Fortuna",
            "La Fuerza",
            "El Colgado",
            "La Muerte",
            "La Templanza",
            "El Diablo",
            "La Torre",
            "La Estrella",
            "La Luna",
            "El Sol",
            "El Juicio",
            "El Mundo"};
    private ImageButton ibPrimera;
    private FrameLayout parentLayout;
    private Button btnBarajar;
    private TextView tvNombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(R.id.frameLayout); // Asigna el LinearLayout de tu XML
        ibPrimera = findViewById(R.id.imageButton);
        btnBarajar = findViewById(R.id.button);
        tvNombre = findViewById(R.id.tvNombre);

        crearBaraja();

        btnBarajar.setOnClickListener(bBarajarOnClickListener);
    }

    private void crearBaraja() {

        String nombreCarta = null;

        // Ejemplo de creación e inicialización de ImageButton en una matriz
        for (int i = 0; i < NUMCARTAS; i++) {
            ibtnCartas[i] = new ImageButton(this);
            ibtnCartas[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            ibtnCartas[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            nombreCarta = "carta" + i;

            int resourceId = getResources().getIdentifier(nombreCarta, "drawable", getPackageName());

            // Asigna la imagen al ImageButton si se encontró el recurso
            if (resourceId != 0) {
                ibtnCartas[i].setImageResource(resourceId);
                Log.e("log", "Recurso encontrado para " + nombreCarta);
                ibtnCartas[i].setVisibility(View.INVISIBLE);
                ibtnCartas[i].setBackgroundColor(Color.WHITE);
                // Agrega el ImageButton al layout contenedor
                parentLayout.addView(ibtnCartas[i]);
            } else {
                // Manejo en caso de que el recurso no se encuentre
                Log.e("Error", "Recurso no encontrado para " + nombreCarta);
            }


            // Puedes agregar eventos onClick u otras configuraciones aquí si es necesario
            ibtnCartas[i].setOnClickListener(ibtnCartasOnClickListener);

        }
    }

    // Create OnClickListener
    private final View.OnClickListener bBarajarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // invisivilizar el resto de las cartas
            for (int i = 0; i < NUMCARTAS; i++) {
                ibtnCartas[i].setVisibility(View.INVISIBLE);
            }
            int x = (int) (Math.random() * NUMCARTAS);
            ibtnCartas[x].setVisibility(View.VISIBLE);
            tvNombre.setText(nombreCarta[x]);
            Log.e("Error", "OnClick Boton " + x);
        }
    };

    private final View.OnClickListener ibtnCartasOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageButton ib = (ImageButton) v;
            ib.setVisibility(View.INVISIBLE);
            ibPrimera.setVisibility(View.VISIBLE);
            tvNombre.setText("");
        }
    };


    // Función para alternar la visibilidad de los ImageButtons con animación
    private void cambiarImageButtons(ImageButton ib1, ImageButton ib2) {
        if (ib1.getVisibility() == View.VISIBLE) {
            animateButton(ib1, ib2);
        } else {
            animateButton(ib2, ib1);
        }
    }

    // Función para realizar la animación entre dos ImageButtons
    private void animateButton(View buttonToHide, View buttonToShow) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(buttonToHide, "alpha", 1f, 0f);
        fadeOut.setDuration(500);
        fadeOut.start();

        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttonToHide.setVisibility(View.GONE);
                buttonToShow.setVisibility(View.VISIBLE);
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(buttonToShow, "alpha", 0f, 1f);
                fadeIn.setDuration(500);
                fadeIn.start();
            }
        });
    }
}

