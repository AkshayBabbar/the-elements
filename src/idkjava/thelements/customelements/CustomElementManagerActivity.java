package idkjava.thelements.customelements;

import idkjava.thelements.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class CustomElementManagerActivity extends Activity
{
	//TODO(MASSIVE,ARMORED): This should display all loaded Custom Elements and allow selection,
	//editing, and creation
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.custom_element_manager);
		initElements();
		Window window = getWindow();
	    // Eliminates color banding
	    window.setFormat(PixelFormat.RGBA_8888);
	    
	    ListView elementListView = (ListView) findViewById(R.id.customListView);
	    //Test elements
	    List<CustomElement> elementList= new ArrayList<CustomElement>();
	    char[] charArray = {4, 5};
	    elementList.add(new CustomElement("FirstElement", charArray));
	    CustomElementArrayAdapter arrayAdapter = new CustomElementArrayAdapter(this, R.layout.custom_element_edit_item, elementList);
	    //TODO: Implement storage of elements, opening of elements, and use of them in the c-backend
		elementListView.setAdapter(arrayAdapter);
		
	}
	private void initElements()
	{
		File file = new File("/sdcard/thelements/customelements.lib");
		if(file.exists())
		{
			
			
			
		}
		else
		{
			file.mkdirs();
		}
	}
}