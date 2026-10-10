package com.gamespacexarena;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import java.util.List;

public class MainActivity extends Activity {

    int fondo = Color.rgb(10, 10, 18);
    int azul = Color.rgb(35, 125, 255);
    int morado = Color.rgb(145, 65, 255);
    LinearLayout contenido;

    GradientDrawable forma(int color, int borde) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(24);
        g.setStroke(2, borde);
        return g;
    }

    TextView texto(String s, int size, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    void espacio(LinearLayout parent, int alto) {
        View v = new View(this);
        parent.addView(v, new LinearLayout.LayoutParams(1, alto));
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(fondo);
        getWindow().setNavigationBarColor(fondo);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(fondo);

        contenido = new LinearLayout(this);
        contenido.setOrientation(LinearLayout.VERTICAL);
        contenido.setPadding(22, 24, 22, 28);
        scroll.addView(contenido);

        TextView marca = texto("GAME SPACE", 14, azul);
        marca.setTypeface(null, Typeface.BOLD);
        contenido.addView(marca);

        TextView titulo = texto("XARENA", 34, Color.WHITE);
        titulo.setTypeface(null, Typeface.BOLD);
        contenido.addView(titulo);

        TextView subtitulo = texto(
            "TU CENTRO DE JUEGOS", 12, 0xFFAAAAC8);
        contenido.addView(subtitulo);

        espacio(contenido, 24);

        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setPadding(18, 18, 18, 18);
        tarjeta.setBackground(forma(0xFF171729, morado));
        contenido.addView(tarjeta);

        TextView monitor = texto("CENTRO DE RENDIMIENTO", 16,
            Color.WHITE);
        monitor.setTypeface(null, Typeface.BOLD);
        tarjeta.addView(monitor);

        espacio(tarjeta, 10);

        TextView bateria = texto("Batería: " +
            getBatteryLevel() + "%", 15, 0xFFB9C9FF);
        tarjeta.addView(bateria);

        espacio(tarjeta, 8);

        TextView memoria = texto("Memoria RAM: consulta del sistema",
            13, 0xFFB9C9FF);
        tarjeta.addView(memoria);

        espacio(contenido, 24);

        TextView juegos = texto("MIS JUEGOS", 21, Color.WHITE);
        juegos.setTypeface(null, Typeface.BOLD);
        contenido.addView(juegos);

        espacio(contenido, 12);

        Button buscar = new Button(this);
        buscar.setText("BUSCAR JUEGOS INSTALADOS");
        buscar.setTextColor(Color.WHITE);
        buscar.setBackground(forma(0xFF182441, azul));
        contenido.addView(buscar);

        buscar.setOnClickListener(v -> mostrarJuegos());

        espacio(contenido, 12);

        TextView nota = texto(
            "Selecciona un juego para abrirlo. " +
            "Algunas funciones dependen de Android.",
            13, 0xFFAAAAC8);
        contenido.addView(nota);

        espacio(contenido, 24);

        Button herramientas = new Button(this);
        herramientas.setText("HERRAMIENTAS GAMER");
        herramientas.setTextColor(Color.WHITE);
        herramientas.setBackground(forma(0xFF24183A, morado));
        contenido.addView(herramientas);

        herramientas.setOnClickListener(v ->
            new android.app.AlertDialog.Builder(this)
                .setTitle("Game Space Xarena")
                .setMessage(
                    "Primera versión en construcción.\n\n" +
                    "Panel flotante, monitor avanzado y " +
                    "captura se añadirán en siguientes pasos.")
                .setPositiveButton("ENTENDIDO", null)
                .show()
        );

        setContentView(scroll);
    }

    int getBatteryLevel() {
        android.content.IntentFilter filtro =
            new android.content.IntentFilter(
                Intent.ACTION_BATTERY_CHANGED);
        Intent bateria = registerReceiver(null, filtro);
        if (bateria == null) return 0;
        return bateria.getIntExtra("level", 0);
    }

    void mostrarJuegos() {
        contenido.removeAllViews();

        TextView titulo = texto("JUEGOS INSTALADOS", 24, Color.WHITE);
        titulo.setTypeface(null, Typeface.BOLD);
        contenido.addView(titulo);

        Button volver = new Button(this);
        volver.setText("VOLVER AL INICIO");
        contenido.addView(volver);
        volver.setOnClickListener(v -> recreate());

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps =
            pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {
            Intent lanzamiento =
                pm.getLaunchIntentForPackage(app.packageName);

            if (lanzamiento == null ||
                app.packageName.equals(getPackageName())) {
                continue;
            }

            Button juego = new Button(this);
            juego.setText(pm.getApplicationLabel(app).toString());
            juego.setTextColor(Color.WHITE);
            juego.setAllCaps(false);
            juego.setBackground(forma(0xFF171729, azul));

            LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                    -1, -2);
            lp.setMargins(0, 5, 0, 5);
            contenido.addView(juego, lp);

            juego.setOnClickListener(v -> {
                try {
                    startActivity(lanzamiento);
                } catch (Exception e) {
                    android.widget.Toast.makeText(
                        this, "No se pudo abrir el juego",
                        android.widget.Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
          }
