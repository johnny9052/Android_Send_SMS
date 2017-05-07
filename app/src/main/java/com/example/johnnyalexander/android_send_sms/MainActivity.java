package com.example.johnnyalexander.android_send_sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/*https://www.sitepoint.com/requesting-runtime-permissions-in-android-m-and-n/*/
public class MainActivity extends AppCompatActivity {

    /*Orden aleatorio, de como van a aparecer los permisos en el dispositivo*/
    /*El 0x1....0x2....0x3 genera numeros aleatorios*/
    static final Integer MESSAGE = 0x1;
    static final Integer LOCATION = 0x2;
    static final Integer CALL = 0x3;
    static final Integer WRITE_EXST = 0x4;
    static final Integer READ_EXST = 0x5;
    static final Integer CAMERA = 0x6;
    static final Integer ACCOUNTS = 0x7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        /*Se instancia el objeto que permite tener acceso a toda la configuracion del
        * dispositivo*/
        PackageManager pm = getApplicationContext().getPackageManager();

        /*Se valida si tiene o no permiso para enviar mensajes de texto, el cual retorna
        * un codigo entero si tiene el permiso*/
        int tienePermiso = pm.checkPermission(
                Manifest.permission.SEND_SMS,
                getApplicationContext().getPackageName());

        /*Se valida si el codigo retornado es igual al codigo global del dispositivo de
        * permisos otorgados*/
        if (tienePermiso == PackageManager.PERMISSION_GRANTED) {
            /*Se instancia el objeto para el envio de mensajes*/
            SmsManager sms = SmsManager.getDefault();
            /*Se envia el mensaje (numeroTelefonico, configuracion de envio de mensajes - si de
            manda null se deja la que tenga la SIM CARD por defecto, Mensaje, intent que se
            ejecuta cuando se envia satisfactoriamente el mensaje, intent que se ejecuta cuando
            sale error al enviar el mensaje)*/
            sms.sendTextMessage("3217501560", null, "Te amo maumaitus", null, null);
            Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No tienes permisos para esto", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission(String permission, Integer requestCode) {

        /*El permiso que quiero activar ya se encuentra activado?*/
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            /*Se muestra un mensaje en pantalla solicitando el permiso*/
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                /*Si le da permitir se activa el permiso*/
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            } else {
                /*Si le da rechazar igual se manda pero de manera negativa*/
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " Ya tiene activado este permiso", Toast.LENGTH_SHORT).show();
        }
    }


    /*Recibe por parametro el boton que activo la funcion*/
    public void ask(View v){
        /*Se valida con un case el id del boton presionado, y segun dicho boton se solicita un
        * determinado permiso*/
        switch (v.getId()){
            case R.id.message:
                askForPermission(Manifest.permission.SEND_SMS,MESSAGE);
                break;
            case R.id.location:
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
                break;
            case R.id.call:
                askForPermission(Manifest.permission.CALL_PHONE,CALL);
                break;
            case R.id.write:
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
                break;
            case R.id.read:
                askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
                break;
            case R.id.camera:
                askForPermission(Manifest.permission.CAMERA,CAMERA);
                break;
            case R.id.accounts:
                askForPermission(Manifest.permission.GET_ACCOUNTS,ACCOUNTS);
                break;
            default:
                break;
        }
    }




}
