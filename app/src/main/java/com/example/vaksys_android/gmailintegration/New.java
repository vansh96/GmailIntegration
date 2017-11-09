package com.example.vaksys_android.gmailintegration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class New extends AppCompatActivity {

    Button whatsapp_btn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        editText = (EditText) findViewById(R.id.text_view);

        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clipData = android.content.ClipData.newPlainText("label", "text");
        clipboardManager.setPrimaryClip(clipData);

        whatsapp_btn = (Button) findViewById(R.id.whatsapp_btn);
        whatsapp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("plain Text", Intent.EXTRA_TEXT);
                intent.setType("text/plain");
                intent.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(intent, ""));
                startActivity(intent);
            }
        });

    }
}
