package org.bitsyko.overlay.test.wrapper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.res.TypedArray;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.app.ProgressDialog;


public class MainActivity extends Activity {

	private ProgressDialog progressDialog;
	public static final int MSG_READY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView longInfo = (TextView) findViewById(R.id.longInfo);
		longInfo.setMovementMethod(new ScrollingMovementMethod());
		
		TextView push = (TextView) findViewById(R.id.pushInfo);
		push.setOnClickListener(new OnClickListener(){
			 public void onClick(View v)
		      {
				 
		      }
		});
		
		
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		img.setOnClickListener(new OnClickListener()
		//Button button = (Button) findViewById(R.id.button1);
	    //button.setOnClickListener(new OnClickListener()
	    {
	      public void onClick(View v)
	      {
	    	  //progressDialog = ProgressDialog.show(MainActivity.this, "", "extract overkay files...");
	    	 //new Thread() {
	    	 //	  public void run() {
	    	 		  copyFileFromAssets();
	    	 //		  try {
			 //			sleep(2000);
			 //		} catch (InterruptedException e) {
						// TODO Auto-generated catch block
			 //			e.printStackTrace();
			 //		}
	    	 // 	  }
	    	 //}.start();
	    	 		  
	    	 			 
	    	  //progressDialog.dismiss();
	    			  int fcount=0;
	    			  
	    			  String zipFile = "/sdcard/bitSyko-overlays.zip";
	    			  String unzipLocation = "/sdcard/"; 	    	  
	    			  UnzipUtil uz = new UnzipUtil(zipFile, unzipLocation);
	    			  fcount=uz.fcnt();

	    		        Resources res = getResources();
	    		        String pushMsg1 = (res.getString(R.string.pushMsgStart));
	    		        String pushMsg2 = (res.getString(R.string.pushMsgEnd));
	    		        
	    	  progressDialog = ProgressDialog.show(MainActivity.this, "", pushMsg1+" "+fcount+" "+pushMsg2);
	    	  new Thread() {
	    		  
	    	  	  public void run() {
  		    	     String zipFile = "/sdcard/bitSyko-overlays.zip";
		          	 String unzipLocation = "/sdcard/"; 	    	  		  
	    	  		 UnzipUtil uz = new UnzipUtil(zipFile, unzipLocation); 
	    			  try {
	    				    //sleep(10000);
	    		    	boolean ret;
	    		        ret=uz.unzip();	
	    		            //File file = getBaseContext().getFileStreamPath("/sdcard/bitSyko-overlays.zip");
	    		            //if(file.exists()) { 
	    		            //    boolean result = file.delete();
	    		            //}   	    			    	  	    				    
	    				    
	    			  } catch (Exception e) {
	    				  Log.e("tag", e.getMessage());
	    			  }
	    			  
	    			  handler.sendEmptyMessage(MSG_READY);

	    		  }
	    	  }.start();
	    	  
	      	  //while (uz.ready==false){	  
	      	  //}
	    	  //		try {
	    	  //			showDialog();
	    	  //		} catch (Exception e) {
	    	  //			// TODO Auto-generated catch block
	    	  // 			e.printStackTrace();
	    	  //		}  
	    		    	  
	      }
	    });	
		

	}
	
	private Handler handler = new Handler() {

	    @Override
	    public void handleMessage(Message msg) {
	        switch (msg.what) {
	            case MSG_READY:
	                
	                // What to do when ready, example:
	    			//dismiss the progress dialog
	    			  progressDialog.dismiss();
	    			  	
	    			  try {
						showDialogDone();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	                
	                break;
	            }
	        }
	  };
	
	
	
	
	private void showDialogDone() throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        Resources res = getResources();
        String title = (res.getString(R.string.app_name));
        String msgDone = (res.getString(R.string.msg_Done));
        builder.setTitle(title);
        
        builder.setMessage(msgDone);       

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int which) 
            {
            	finish();
            	System.exit(0);
                dialog.dismiss();
                
                
            }
        });

        builder.setNegativeButton("", new DialogInterface.OnClickListener() 
        {   
            @Override
            public void onClick(DialogInterface dialog, int which) 
            {
                dialog.dismiss();
            }
        });

        builder.show();
    }

	

   
    
    private void copyFileFromAssets() {
    	AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
            
        }
        for(String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open(filename);
              //File outFile = new File(getExternalFilesDir(null), filename);
              out = new FileOutputStream("/sdcard/" + filename);
              
              copyFile(in, out);
              
              
          
              
              
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }     
            
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            } 
            boolean ret;
            
       }
        
    return;    
    }
	
    
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }   
   
   
    
   

	
//##########################################################

	

}
