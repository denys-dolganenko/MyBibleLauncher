package launcher.mybible.ua.mybiblelauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private EditText moduleAbbreviationEditText;
  private EditText bookNumberEditText;
  private EditText chapterNumberEditText;
  private EditText verseNumberEditText;
  private CheckBox russianNumberingCheckBox;
  private EditText verseReferenceEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    moduleAbbreviationEditText = findViewById(R.id.edit_text_abbreviation);
    bookNumberEditText = findViewById(R.id.edit_text_book_number);
    chapterNumberEditText = findViewById(R.id.edit_text_chapter_number);
    verseNumberEditText = findViewById(R.id.edit_text_verse_number);
    russianNumberingCheckBox = findViewById(R.id.check_box_russian_numbering);
    verseReferenceEditText = findViewById(R.id.edit_text_verse_reference);
    configureOpenByNumberingButton();
    configureOpenByReferenceStringButton();
  }

  private void configureOpenByNumberingButton() {
    findViewById(R.id.button_open_by_numbering).setOnClickListener(
        (view) -> {

          final String moduleAbbreviation =
              moduleAbbreviationEditText.getText().length() == 0 ? null :
                  moduleAbbreviationEditText.getText().toString();
          final short bookNumber = getValue(bookNumberEditText);
          final short chapterNumber = getValue(chapterNumberEditText);
          final short verseNumber = getValue(verseNumberEditText);
          final boolean russianNumbering = russianNumberingCheckBox.isChecked();

          Intent launchIntent = getPackageManager().getLaunchIntentForPackage("ua.mybible");
          if (launchIntent != null) {
            final Bundle bundle = new Bundle();

            if (moduleAbbreviation != null) {
              // Optional Bible module abbreviation.
              // If not specified, a Bible module currently selected in an active Bible window
              // will be used.
              bundle.putString("module_abbreviation", moduleAbbreviation);
            }

            // A book number, shall be greater than 0.
            // See the table of books in the MyBible Modules Format document.
            bundle.putShort("book", bookNumber);

            // A chapter number, shall be greater than 0.
            bundle.putShort("chapter", chapterNumber);

            // A verse number, shall be greater than 0.
            bundle.putShort("verse", verseNumber);

            // An indication whether a provided numbering for Psalms is Russian.
            bundle.putBoolean("russian_numbering", russianNumbering);

            launchIntent.putExtra("position", bundle);
            startActivity(launchIntent);
          }
        }
    );
  }

  private void configureOpenByReferenceStringButton() {
    findViewById(R.id.button_open_by_reference_string).setOnClickListener(
        (view) -> {

          final String referenceString = verseReferenceEditText.getText().toString();
          final boolean russianNumbering = russianNumberingCheckBox.isChecked();

          Intent launchIntent = getPackageManager().getLaunchIntentForPackage("ua.mybible");
          if (launchIntent != null) {
            final Bundle bundle = new Bundle();

            // A reference string.
            // See the MyBible Auto-Hyperlinks Support document.
            bundle.putString("reference_string", referenceString);

            // An indication whether a provided numbering for Psalms is Russian.
            bundle.putBoolean("russian_numbering", russianNumbering);

            launchIntent.putExtra("reference", bundle);
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
