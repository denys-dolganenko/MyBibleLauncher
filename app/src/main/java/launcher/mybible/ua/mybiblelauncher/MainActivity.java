package launcher.mybible.ua.mybiblelauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private EditText moduleAbbreviationEditText;
  private EditText bookNumberEditText;
  private EditText chapterNumberEditText;
  private EditText verseNumberEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    moduleAbbreviationEditText = findViewById(R.id.edit_text_abbreviation);
    bookNumberEditText = findViewById(R.id.edit_text_book_number);
    chapterNumberEditText = findViewById(R.id.edit_text_chapter_number);
    verseNumberEditText = findViewById(R.id.edit_text_verse_number);
    configureGoButton();
  }

  private void configureGoButton() {
    findViewById(R.id.button_show).setOnClickListener(
        (view) -> {

          final String moduleAbbreviation =
              moduleAbbreviationEditText.getText().length() == 0 ? null :
                  moduleAbbreviationEditText.getText().toString();
          final short bookNumber = getValue(bookNumberEditText);
          final short chapterNumber = getValue(chapterNumberEditText);
          final short verseNumber = getValue(verseNumberEditText);

          Intent launchIntent = getPackageManager().getLaunchIntentForPackage("ua.mybible");
          if (launchIntent != null) {
            final Bundle bundle = new Bundle();

            if ( moduleAbbreviation != null ) {
              // Optional Bible module abbreviation.
              // If not specified, a Bible module currently selected in an active Bible window
              // will be used.
              bundle.putString("module_abbreviation", moduleAbbreviation);
            }

            // A book number, shall be greater than 0.
            // See https://docs.google.com/document/d/12rf4Pqy13qhnAW31uKkaWNTBDTtRbNW0s7cM0vcimlA/edit#heading=h.55b1k11igp72
            bundle.putShort("book", bookNumber);

            // A chapter number, shall be greater than 0.
            bundle.putShort("chapter", chapterNumber);

            // A verse number, shall be greater than 0.
            bundle.putShort("verse", verseNumber);

            launchIntent.putExtra("position", bundle);
            startActivity(launchIntent);
          }
        }
    );
  }

  short getValue(@NonNull final EditText editText) {
    short value = 0;
    try {
      value = Short.parseShort(editText.getText().toString());
    } catch (Exception ignore) {

    }
    return value;
  }
}
