package idkjava.thelements.game;

import idkjava.thelements.MainActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
//import android.util.Log;
import android.view.MotionEvent;

public class SandView extends GLSurfaceView
{
	private static final int FINGER_DOWN = 1;
	private static final int FINGER_UP = 0;

    private SandViewRenderer mRenderer; //Declare the renderer
	
    //Constructor
    public SandView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        MainActivity.setDimensions(getWidth(), getHeight());
    	mRenderer = new SandViewRenderer(); //Set up the Renderer for the View
        setRenderer(mRenderer); //Associate it with this view
    }

    //When a touch screen event occurs
    public boolean onTouchEvent(final MotionEvent event)
    {
    	//Set the touch state in JNI
    	if (event.getAction() == MotionEvent.ACTION_DOWN)
    	{
			setFingerState(FINGER_DOWN);
    	}
    	else if(event.getAction() == MotionEvent.ACTION_UP)
    	{
    		setFingerState(FINGER_UP);
    	}
    	
    	//Set the touch position in JNI
    	if(MainActivity.zoomedIn())
    	{
    		//Both x and y are halved because it needs to be zoomed in
    		setMouseLocation((int)event.getX()/2, (int)event.getY()/2);
    	}
    	else
    	{
    		//X and y are normal, because it is zoomed out
    		setMouseLocation((int)event.getX(), (int)event.getY());	
    	}
    	
    	return true;
    }
    
    private static native void setFingerState(int state);
    private static native void setMouseLocation(int xpos, int ypos);
}

class SandViewRenderer implements GLSurfaceView.Renderer
{
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        nativeInit();
    }

    public void onSurfaceChanged(GL10 gl, int w, int h)
    {
        nativeResize(w, h);
        if (MainActivity.loaddemov == true) //loads the demo from the sdcard on first run.
        {
        	MainActivity.loadDemo();
        	MainActivity.loaddemov = false;
        }
    }

    public void onDrawFrame(GL10 gl)
    {
		nativeRender(); //Actual rendering - everything happens here
    }

    private static native void nativeInit(); //Jni init
    private static native void nativeResize(int w, int h); //Jni resize
    private static native void nativeRender(); //Jni rendering function - everything happens here
}