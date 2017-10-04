package com.example.vitorio.jogodavelha2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TelaInicial extends Activity {

    EditText id,nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        findViewById(R.id.buttoncriarpartida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog2, (ViewGroup) findViewById(R.id.rootParent));
                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                nome = (EditText)layout.findViewById(R.id.nome);

                String x;
                final Intent intent= getIntent();
                // custom dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaInicial.this);
                builder.setTitle("Entrar na Partida: ");
                builder.setView(layout);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();

                Button sendButton = (Button) layout.findViewById(R.id.dialogButtonOK);
                Button cancelButton = (Button) layout.findViewById(R.id.dialogButtonCancelar);
                sendButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        AcessoRest ar = new AcessoRest();
                        Intent intentmigrar = new Intent(TelaInicial.this,Principal.class);
                        String sala = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/CriarJogoControler/"+nome.getText().toString());
                        System.out.println("Criando Partida:(Retorno) "+ar);
                        intentmigrar.putExtra("idjogo",sala);
                        intentmigrar.putExtra("nomecriador",nome.getText().toString());
                        intentmigrar.putExtra("nomeparticipante","");
                        intentmigrar.putExtra("entrada","X");
                        intentmigrar.putExtra("jogador",true);
                        Toast.makeText(TelaInicial.this, "Aproveite o Jogo!", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                        startActivity(intentmigrar);
                        finish();



                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        findViewById(R.id.buttonjogaronline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog , (ViewGroup) findViewById(R.id.rootParent));
                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                nome = (EditText)layout.findViewById(R.id.nome);
                id = (EditText) layout.findViewById(R.id.edtsala);
                id.setInputType(InputType.TYPE_CLASS_NUMBER);

                final Intent intent= getIntent();
                // custom dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaInicial.this);
                builder.setTitle("Entrar na Partida: ");
                builder.setView(layout);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();

                Button sendButton = (Button) layout.findViewById(R.id.dialogButtonOK);
                Button cancelButton = (Button) layout.findViewById(R.id.dialogButtonCancelar);
                sendButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                            AcessoRest ar = new AcessoRest();
                            JSONObject object = null;
                            Intent intentmigrar = new Intent(TelaInicial.this,Principal.class);
                            String sala = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/EntrandoEmJogoControler/"+nome.getText().toString()+"/"+ Integer.parseInt(id.getText().toString()));
                            System.out.println("Jogando Online:(Retorno) "+ar);
                             if(sala.equals("false")) {
                                 Toast.makeText(TelaInicial.this, "Sala Indisponivel!", Toast.LENGTH_SHORT).show();
                             } else {
                                Toast.makeText(TelaInicial.this, "Aproveite o Jogo!", Toast.LENGTH_SHORT).show();

                                 try{
                                     JSONArray jogo = new JSONArray(sala);
                                     object = jogo.getJSONObject(0);
                                     intentmigrar.putExtra("nomecriador", object.getString("nomecriador"));
                                 }catch (JSONException e){
                                     Log.i("TelaInicial","Erro");
                                 }
                                 intentmigrar.putExtra("entrada","O");
                                 intentmigrar.putExtra("jogador",false);
                                 intentmigrar.putExtra("nomeparticipante", nome.getText().toString());
                                 intentmigrar.putExtra("idjogo",id.getText().toString());

                                dialog.dismiss();
                                startActivity(intentmigrar);
                                finish();
                             }


                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
