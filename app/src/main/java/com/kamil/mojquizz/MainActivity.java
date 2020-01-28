package com.kamil.mojquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    TextView questionLabel, questionIloscLabel, punktyLabel;
    EditText answerEdit;
    Button potwierdzButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArraylist;


    int aktualnaPozycja = 0;
    int iloscPoprawnychOdpowiedzi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionIloscLabel = findViewById(R.id.numberQuestion);
        questionLabel = findViewById(R.id.question);
        punktyLabel = findViewById(R.id.punkty);

        answerEdit = findViewById(R.id.answer);
        potwierdzButton = findViewById(R.id.potwierdz);
        progressBar = findViewById(R.id.progress);

        questionModelArraylist = new ArrayList<>();

        setUpQuestion();

        setData();

        potwierdzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer();
            }
        });

        answerEdit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                Log.e("event.getAction()",event.getAction()+"");
                Log.e("event.keyCode()",keyCode+"");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    checkAnswer();
                    return true;
                }
                return false;
            }
        });

    }
    public void checkAnswer(){
        String answerString  = answerEdit.getText().toString().trim();




        if(answerString.equalsIgnoreCase(questionModelArraylist.get(aktualnaPozycja).getAnswer())){
            iloscPoprawnychOdpowiedzi++;



            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Brawo!")
                    .setContentText("To poprawna odpowiedź")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            aktualnaPozycja++;

                            setData();
                            answerEdit.setText("");
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();


        }else {

            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Zła odpowiedź!")
                    .setContentText("Poprawna odpowiedź to : "+questionModelArraylist.get(aktualnaPozycja).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            aktualnaPozycja++;

                            setData();
                            answerEdit.setText("");
                        }
                    })
                    .show();
        }





        int x = ((aktualnaPozycja +1) * 100) / questionModelArraylist.size();

        progressBar.setProgress(x);



    }




    public void setUpQuestion(){


        questionModelArraylist.add(new QuestionModel("Kij ma dwa końce. Ile końców ma 7,5 kija? ","16"));
        questionModelArraylist.add(new QuestionModel("Ewa ma 6 lat i jest dwa razy starsza od Pawła. Ile lat będzie miał Paweł, gdy Ewa będzie mieć 20 lat? ","17"));
        questionModelArraylist.add(new QuestionModel("Po stole chodzi dziesięć much. Trzy zostały zabite. Ile much zostało na stole? ","3"));
        questionModelArraylist.add(new QuestionModel("2+2*2= ","6"));
        questionModelArraylist.add(new QuestionModel("Ile razy można odjąć 1 od 100?","1"));
        questionModelArraylist.add(new QuestionModel("Kupujesz do jedzenia, lecz nigdy tego nie jesz?(Należy wpisać bez znaków polskich)","Sztucce"));
        questionModelArraylist.add(new QuestionModel("Tata Kasi ma pięć córek: nunu, nano, nani, nono. Jak nazywa się piąta córka?","Kasia"));
        questionModelArraylist.add(new QuestionModel("Co ma miejsce raz w minucie dwa razy w momencie i ani razu w  tysiącu lat?","M"));
        questionModelArraylist.add(new QuestionModel("Ile to jest 6:2(1+2) = ?","1"));
        questionModelArraylist.add(new QuestionModel("Matka miała 5 synów. Każdy z synów miał siostrę. Ile matka miała dzieci? ","6"));



    }

    public void setData(){


        if(questionModelArraylist.size()> aktualnaPozycja) {

            questionLabel.setText(questionModelArraylist.get(aktualnaPozycja).getQuestionString());

            punktyLabel.setText("Punkty :" + iloscPoprawnychOdpowiedzi + "/" + questionModelArraylist.size());
            questionIloscLabel.setText("Numer pytania : " + (aktualnaPozycja + 1));


        }else{


            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Brawo! Ukończyłeś/aś quiz ")
                    .setContentText("Twoja liczba punktów to : "+ iloscPoprawnychOdpowiedzi + "/" + questionModelArraylist.size())
                    .setConfirmText("Ponów")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            aktualnaPozycja = 0;
                            iloscPoprawnychOdpowiedzi = 0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })
                    .setCancelText("Zamknij aplikację")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();

        }

    }



}
