package com.yink.sardar;

import android.app.Activity;
import android.util.Log;

public class VideoCompress {

	private Activity context;
	String progess = "";
	String cmd;
	Compressor com;
	
	public VideoCompress(Activity c)
	{
		this.context = c;
	}
	
	public void convertVideo(String fileInput, String outFile)
	{
		cmd = "-y -i " + fileInput + " -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 "+outFile;
		
		com = new Compressor(context);
		
		com.loadBinary(new InitListener() {
            @Override
            public void onLoadSuccess() {
                com.execCommand(cmd,new CompressListener() {
                    @Override
                    public void onExecSuccess(String message) {
                        Log.i("success",message);
                        progess = "done";
                    }

                    @Override
                    public void onExecFail(String reason) {
                        Log.i("fail",reason);
                        //Toast.makeText(getApplicationContext(), "Se jodio:"+reason, Toast.LENGTH_LONG).show();
                        progess = "fail";
                    }

                    @Override
                    public void onExecProgress(String message) {
                    	if (message.contains("size="))
                    	{
                    		//Log.i("progress",message.substring(message.indexOf("size="),message.indexOf(" bitrate")));
                    		Log.i("progress",message.substring(message.indexOf("time=")+5,message.indexOf(" bitrate")));
                    		progess = message.substring(message.indexOf("time=")+5,message.indexOf(" bitrate"));
                    	}
                        //Toast.makeText(getApplicationContext(), "Progreso:"+message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLoadFail(String reason) {
                Log.i("fail",reason);
                progess = "Error Load";
            }
        });
	}
	
	public String getProgress(int tiempo)
	{
		if (progess.equals(""))
			return "";
		else if (progess.equals("done"))
			return "100";
		else if (progess.equals("fail"))
			return "fail";
		else
		{
			int time = 0;
			int porcent = 0;
			try
			{
				time = Integer.parseInt(progess.trim().substring(6, 8));
				porcent = (time * 100) / tiempo;
			}
			catch (Exception ex)
			{
				return "0";
			}
			
			return porcent+"";
		}
	}
}
