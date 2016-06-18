package projects.bubalex.ru.webpagecodeviewer;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends Activity  {

    Button getCodeButton;
    EditText pageName;
    TextView webPageCode;
    RadioButton httpRadio;
    RadioButton httpsRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    public void start()
    {
        getCodeButton = (Button) findViewById(R.id.getPageCode);

        pageName = (EditText) findViewById(R.id.pageName);
        webPageCode = (TextView) findViewById(R.id.webPageCode);

        httpRadio   = (RadioButton) findViewById(R.id.radioHttp);
        httpsRadio   = (RadioButton) findViewById(R.id.radioHttps);

    }

    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.radioHttp:
                pageName.setText("http://");
                break;

            case R.id.radioHttps:
                pageName.setText("https://");
                break;

            case R.id.getPageCode:
                String siteName = pageName.getText().toString();
                CodeGetter codeGetter = new CodeGetter(webPageCode);
                codeGetter.execute(siteName);
                break;
        }


    }
}
