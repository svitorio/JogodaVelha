package com.example.vitorio.jogodavelha2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Principal extends Activity {


    TextView id,criador;
    //Procura maneira certa
    static TextView participante;
    String entrada;
    Boolean jogador;
    Button[][]b = new Button[3][3];
    AlertDialog alerts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princal);

        //pegando id View
        id = (TextView)findViewById(R.id.idjogo);
        criador = (TextView)findViewById(R.id.idnomecriador);
        participante = (TextView)findViewById(R.id.idparticipante);

        //matriz de buttons

        //recebendo informações da Activity/classe Tela Inicial
        Intent intent = getIntent();
        entrada = intent.getStringExtra("entrada");
        jogador = intent.getBooleanExtra("jogador",false);
        id.setText(intent.getStringExtra("idjogo"));
        criador.setText(intent.getStringExtra("nomecriador"));
        participante.setText(intent.getStringExtra("nomeparticipante"));

        System.out.println("Jogador: "+jogador);
        //Recebendo id View dos Buttões
        b[0][0]= (Button)findViewById(R.id.b11);
        b[0][1]= (Button)findViewById(R.id.b12);
        b[0][2]= (Button)findViewById(R.id.b13);
        b[1][0]= (Button)findViewById(R.id.b21);
        b[1][1]= (Button)findViewById(R.id.b22);
        b[1][2]= (Button)findViewById(R.id.b23);
        b[2][0]= (Button)findViewById(R.id.b31);
        b[2][1]= (Button)findViewById(R.id.b32);
        b[2][2]= (Button)findViewById(R.id.b33);

        //setando uma string vazia em cada botão e dastivando toque;
        setarTudovazio();
        bloquearTudo();

        if (participante.getText().toString().equals("")) {
            new Thread(pegarnome).start();
        }
        else {
            cronometro();
        }

    }

    public void cronometro(){
        System.out.println(b[0][0]);
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(Principal.this,"Iniciando em "+millisUntilFinished/1000,Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                System.out.println("AAAAA: "+jogador);
                if(jogador) {
                    Toast.makeText(Principal.this,"Você Começa!",Toast.LENGTH_SHORT).show();
                    liberarjogo();
                    setarbutton();
                }
                else {
                    System.out.println("É para entrar no metodo ouvir agora!");
                    new Thread(ouvir).start();
                }
            }
        }.start();
    }

    public void liberarjogo() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(b[i][j].equals(""))
                    b[i][j].setClickable(true);
                //b[i][j].setTextSize(R.dimen.txt_size);
            }
        }
    }

    private Runnable pegarnome = new Runnable() {
        @Override
        public void run() {
            boolean x = false;
            Looper.prepare();
            while (!x) {
                AcessoRest ar = new AcessoRest();
                Intent intent = getIntent();
                String a = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/" + id.getText().toString());
                System.out.println("OQUE TEM EM A:  "+a);
                try {
                    JSONArray jogo = new JSONArray(a);
                    final JSONObject object = jogo.getJSONObject(0);
                    System.out.println("Nome Participante: "+object.getString("nomeparticipante"));
                    if(!object.getString("nomeparticipante").equals("null")) {
                        participante.post(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    participante.setText(object.getString("nomeparticipante"));
                                    Toast.makeText(Principal.this,object.getString("nomeparticipante")+" entrou no jogo",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Principal.this,"Boa Sorte!",Toast.LENGTH_SHORT).show();
                                }catch (JSONException e){}
                            }
                        });
                        x=true;
                    }
                } catch (JSONException e) {
                    Log.i("hag", "hg");
                }
            }
            cronometro();
            Looper.loop();


        }
    };

    private void setarbutton() {
        findViewById(R.id.b11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[0][0].setText(entrada);
                System.out.println("Teste de Botão: "+b[0][0].getText().toString());
                bloquearbutton(b[0][0]);
                setarJogada("11");

            }
        });
        findViewById(R.id.b12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[0][1].setText(entrada);
                bloquearbutton(b[0][1]);
                setarJogada("12");

            }
        });
        findViewById(R.id.b13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b[0][2].setText(entrada);
                bloquearbutton(b[0][2]);
                setarJogada("13");
            }
        });
        findViewById(R.id.b21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[1][0].setText(entrada);
                bloquearbutton(b[1][0]);
                setarJogada("21");
            }
        });
        findViewById(R.id.b22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[1][1].setText(entrada);
                bloquearbutton(b[1][1]);
                setarJogada("22");

            }
        });
        findViewById(R.id.b23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[1][2].setText(entrada);
                bloquearbutton(b[1][2]);
                setarJogada("23");
            }
        });
        findViewById(R.id.b31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[2][0].setText(entrada);
                bloquearbutton(b[2][0]);
                setarJogada("31");
            }

        });
        findViewById(R.id.b32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[2][1].setText(entrada);
                bloquearbutton(b[2][1]);
                setarJogada("32");

            }
        });
        findViewById(R.id.b33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b[2][2].setText(entrada);
                bloquearbutton(b[2][2]);
                setarJogada("33");
            }
        });
    }

    static private void bloquearbutton(Button button) {
        button.setClickable(false);
    }

    private void setarventrada(String posicao, String entra){

        switch (posicao){
            case "11":
                b[0][0].setText(entra);
                break;
            case "12":
                b[0][1].setText(entra);
                break;
            case "13":
                b[0][2].setText(entra);
                break;
            case "21":
                b[1][0].setText(entra);
                break;
            case "22":
                b[1][1].setText(entra);
                break;
            case "23":
                b[1][2].setText(entra);
                break;
            case "31":
                b[2][0].setText(entra);
                break;
            case "32":
                b[2][1].setText(entra);
                break;
            case "33":
                b[2][2].setText(entra);
                break;
        }
    }

    private void setarJogada(String s) {
        bloquearTudo();
        System.out.println("Entrada setarEntrada: "+entrada);
        final AcessoRest ar = new AcessoRest();
        String re = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/InserindoJogadasControler/"+entrada+"/"+s+"/"+id.getText().toString()+"/"+jogador);
        System.out.println("Respostas do Metodo setar: "+re);
        if(re.equals("true")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean xd = false;
                    while (!xd) {
                        String x = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/ListaJogadaControler/"+id.getText().toString());
                        System.out.println("Ouvir do Metodo SetarJogada!"+x);
                        try {
                            JSONArray jogo = new JSONArray(x);
                            System.out.println("Tamanho do JSON: "+jogo.length());
                            final JSONObject obejeto = jogo.getJSONObject(jogo.length()-1);
                            if (!obejeto.getString("vez").equals(jogador.toString())){

                                System.out.println("Entrou no metodo escolher?");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            setarventrada(obejeto.getString("posicao"),obejeto.getString("entrada"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        liberarjogo();
                                        setarbutton();
                                        Toast.makeText(Principal.this,"Sua vez",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                xd = true;
                            }
                            else
                                System.out.println("Não    Entrou no metodo escolher?");
                        } catch (JSONException e) {
                            Log.i("jksahd", "Erro no JSONArray SetarEntrada");
                        }
                    }
                }
            }).start();
        }else{
            String a = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/" + id.getText().toString());
            try {
                JSONArray jogo = new JSONArray(a);
                JSONObject objeto = jogo.getJSONObject(jogo.length());
                if(objeto.getString("vencedor").equals(criador.getText().toString()) && jogador)
                    resultado("Parabéns Você Ganhou!");

                else if(objeto.getString("vencedor").equals(criador.getText().toString()) && !jogador)
                    resultado("Você Precisar Treinar Mais!");
                else if(objeto.getString("vencedor").equals(participante.getText().toString()) && !jogador)
                    resultado("Parabéns Você Ganhou!");
                else
                    resultado("Você Prescisar Treinar Mais");

            }catch (JSONException e){
                System.out.println(""+re);
            }

        }
    }

    private void resultado(String re) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
            builder.setMessage(re);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alerts.dismiss();
                    Intent intent = new Intent(Principal.this,TelaInicial.class);
                    startActivity(intent);
                    finish();
                }
            });
            alerts = builder.create();
            alerts.show();
    }


    private Runnable ouvir = new Runnable() {
            @Override
            public void run() {
                boolean xd = false;
                AcessoRest ar = new AcessoRest();
                while (!xd) {
                    String x = ar.chamadaGet("http://10.180.23.98:8080/FazendaWebService/webresources/Fazenda/Usario/JogoVelha/ListaJogadaControler/" + id.getText().toString());
                    System.out.println(x);
                    if(x.isEmpty()){
                        System.out.println("Metodo Ouvir If!");
                    }
                    else {
                        try {
                            System.out.println("Metodo Ouvir else!");
                            JSONArray jogo = new JSONArray(x);
                            final JSONObject obejeto = jogo.getJSONObject(jogo.length()-1);
                            if (!obejeto.getString("vez").equals(jogador)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            setarventrada(obejeto.getString("posicao"), obejeto.getString("entrada"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        liberarjogo();
                                        setarbutton();
                                        Toast.makeText(Principal.this, "Sua vez", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                xd = true;
                            }
                        } catch (JSONException e) {
                            Log.i("jksahd", "Erro no JSONArray");
                        }
                    }
                }
            }
   };

    private void setarEntradaButton(){}
    private void setarTudovazio(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                b[i][j].setText("");
                //b[i][j].setTextSize(R.dimen.txt_size);
            }
        }
    }
    private void bloquearTudo() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                b[i][j].setClickable(false);
                //b[i][j].setTextSize(R.dimen.txt_size);
            }
        }
    }
}
/*

 String chamadaWs = "http://10.180.21.81:8080/FazendaWebService/webresources/Fazenda/Usario/list";
                String resultado = ar.chamadaGet(chamadaWs);
                Log.v("Reusltado: ", resultado);

                try {
                    JSONArray usuario = new JSONArray(resultado);
                    list = new ArrayList<String>();
                    for (int i = 0; i < usuario.length(); i++) {

                        JSONObject Obejeto = usuario.getJSONObject(i);
                        Log.d("Resultado: ", Obejeto.getString("nome"));
                        list.add(Obejeto.getString("nome")+"\n"+Obejeto.getString("fone")+"\n"+Obejeto.getString("email"));
                        //a.setText(Obejeto.getString("nome"));

                    }
                    adapter = new ArrayAdapter<String>(Pincipal.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
                    listItem.setAdapter(adapter);

                    listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String itemvalue = (String)listItem.getItemAtPosition(position);
                            Toast.makeText(Pincipal.this, itemvalue, Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
 */