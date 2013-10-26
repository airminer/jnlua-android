package org.maxraskin.jnlua_android_test;

import java.io.IOException;
import java.io.InputStream;

import org.maxraksin.jnlua_android_testapp.R;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public class MainActivity extends Activity {

	private static final String TAG = "LuaTestApp";
	LuaState luaState = new LuaState();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

			luaState.openLibs();
			registerSimple(luaState);

			AssetManager asset = getAssets();
			InputStream is = null;
			
			try {
				is = asset.open("script.lua");
				luaState.load(is, "=jnlua_android");
			} catch (IOException e) {
				Log.e(TAG, "error loading script", e);
				return;
			}

			// Evaluate the script
			luaState.call(0, 0);
			
//			// Trigger main
			luaState.getGlobal("main");
			luaState.call(0, 1);
			
			String output = luaState.toString(1); // convert stack to string
			luaState.pop(1); // Pop result from stack
			TextView outputWidget = (TextView)findViewById(R.id.output);
			outputWidget.setText(output);

		} catch (LuaRuntimeException e) {
			Log.e(TAG, e.getMessage());
			e.printLuaStackTrace();

		} finally {
			luaState.close();
		}
	}

	class Divide implements NamedJavaFunction {
		@Override
		public int invoke(LuaState luaState) {
			// Get arguments using the check APIs; these throw exceptions with
			// meaningful error messages if there is a mismatch
			double number1 = luaState.checkNumber(1);
			double number2 = luaState.checkNumber(2);

			// Do the calculation (may throw a Java runtime exception)
			double result = number1 / number2;

			// Push the result on the Lua stack
			luaState.pushNumber(result);

			// Signal 1 return value
			return 1;
		}

		@Override
		public String getName() {
			return "divide";
		}
	}

	public void registerSimple(LuaState luaState) { 
        // Register the module 
        luaState.register("simple", new NamedJavaFunction[] { new Divide() }); 

        // Set a field 'VERSION' with the value 1 
        luaState.pushInteger(1); 
        luaState.setField(-2, "VERSION"); 

        // Pop the module table 
        luaState.pop(1);
	}
	
}
