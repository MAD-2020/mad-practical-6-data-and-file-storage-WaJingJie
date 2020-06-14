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
import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    MyDBHandler dbHandler;
    EditText username, password;
    Button signup, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */

        dbHandler = new MyDBHandler(this,null,null,1);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        signup = findViewById(R.id.btnSignup);
        cancel = findViewById(R.id.btnCancel);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                ArrayList<Integer> lvlarray = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
                ArrayList<Integer> scorearray = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
                Log.v(TAG, FILENAME + ": Searching database for email!");
                UserData data = dbHandler.findUser(user);
                if(data != null){
                    Log.v(TAG, FILENAME + ": User already exist during new user creation!");
                    Toast.makeText(Main2Activity.this, "User already exist during new user creation!", Toast.LENGTH_LONG).show();
                }
                else{
                    UserData userdata = new UserData(user, pass, lvlarray, scorearray);
                    //This adds the new user information into the database
                    dbHandler.addUser(userdata);

                    //This enters the data into the public static Userdata object
                    MainActivity.userdata = userdata;

                    Toast.makeText(Main2Activity.this, "New User Created!", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, FILENAME + ": New user created successfully!");

                    Log.v(TAG, FILENAME + ": Go to main page");
                    Intent mainpage = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(mainpage);
                }
            }
        });
    }
    protected void onStop() {
        super.onStop();
        finish();
    }
}
