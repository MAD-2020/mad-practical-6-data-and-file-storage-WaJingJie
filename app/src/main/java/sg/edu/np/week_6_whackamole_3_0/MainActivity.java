package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";
    public static UserData userdata;

    EditText username, password;
    Button btnlogin;
    TextView register;

    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */
        dbHandler = new MyDBHandler(this,null,null,1);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        btnlogin = findViewById(R.id.btnCancel);

        register = findViewById(R.id.tvRegister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Log.v(TAG, FILENAME + ": Logging in with: " + user + ": " + pass);

                if(user.isEmpty()||pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }

                else if (isValidUser(user, pass) == false){

                    Log.v(TAG, FILENAME + ": Invalid user!");
                }
                else{
                    Toast.makeText(MainActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, FILENAME + ": Valid User! Logging in");
                    Intent homepage = new Intent(MainActivity.this, Main3Activity.class);
                    homepage.putExtra("Username", username.getText().toString());
                    startActivity(homepage);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(signup);
            }
        });
    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String userName, String password){

        /* HINT:
            This method is called to access the database and return a true if user is valid and false if not.
            Log.v(TAG, FILENAME + ": Running Checks..." + dbData.getMyUserName() + ": " + dbData.getMyPassword() +" <--> "+ userName + " " + password);
            You may choose to use this or modify to suit your design.
         */
        //This searches the database for the entered email
        UserData data = dbHandler.findUser(userName);
        //This happens when the user is not found in the database
        if(data == null){
            Log.v(TAG, FILENAME + ": Invalid user used!");
            Toast.makeText(getApplicationContext(), "Invalid user! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        //This occurs where the password entered is not the correct password
        else if(!data.getMyPassword().equals(password)) {
            Log.v(TAG, FILENAME + ": Invalid password used!");
            Toast.makeText(getApplicationContext(), "Invalid password! Please re-enter again.",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        //This occurs when the user is found to exist in the database
        else{
            userdata = data;
            return true;
        }
    }

}
