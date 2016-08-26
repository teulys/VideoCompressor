package com.yink.sardar;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = "VideoEncodding";
	public Compressor com;
    String cmd;
    TextView text;
    VideoCompress vieCompress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        text = (TextView)findViewById(R.id.tvText);
        
        vieCompress = new VideoCompress(this);
        vieCompress.convertVideo("/sdcard/DCIM/Camera/VID_20160826_181349.mp4", "/sdcard/Video/VIDEO.mp4");
        
        Thread t = new Thread() {

        	  @Override
        	  public void run() {
        	    try {
        	      while (!isInterrupted()) {
        	        Thread.sleep(1000);
        	        runOnUiThread(new Runnable() {
        	          @Override
        	          public void run() {
        	            text.setText(vieCompress.getProgress(30));
        	          }
        	        });
        	      }
        	    } catch (InterruptedException e) {
        	    }
        	  }
        	};

        	t.start();
        /*
        cmd = "-y -i /sdcard/DCIM/Camera/VID_20160826_181349.mp4 -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 /sdcard/Video/VIDEO.mp4";
        //cmd = "-y -i /mnt/sdcard/videokit/in1.mp4 -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 /mnt/sdcard/videokit/out8.mp4";
        com = new Compressor(this);

        com.loadBinary(new InitListener() {
            @Override
            public void onLoadSuccess() {
                com.execCommand(cmd,new CompressListener() {
                    @Override
                    public void onExecSuccess(String message) {
                        Log.i("success",message);
                        //Toast.makeText(getApplicationContext(), "Cono al fin", Toast.LENGTH_LONG).show();
                        text.setText("Cono al fin");
                    }

                    @Override
                    public void onExecFail(String reason) {
                        Log.i("fail",reason);
                        //Toast.makeText(getApplicationContext(), "Se jodio:"+reason, Toast.LENGTH_LONG).show();
                        text.setText("Se jodio:"+reason);
                    }

                    @Override
                    public void onExecProgress(String message) {
                    	if (message.contains("size="))
                    		Log.i("progress",message.substring(message.indexOf("size="),message.indexOf(" bitrate")));
                        //Toast.makeText(getApplicationContext(), "Progreso:"+message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLoadFail(String reason) {
                Log.i("fail",reason);
                //Toast.makeText(getApplicationContext(), "Se jodio:"+reason, Toast.LENGTH_LONG).show();
            }
        });
        */
    }

}