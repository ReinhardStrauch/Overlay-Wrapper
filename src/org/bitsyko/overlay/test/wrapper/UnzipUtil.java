package org.bitsyko.overlay.test.wrapper;


import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtil
{
 private String zipFile;
 private String location;
 public int filecount=0;


 public UnzipUtil(String zipFile, String location)
 {
  this.zipFile = zipFile;
  this.location = location;
  
  dirChecker("");
 }

 
 public boolean ready=false;
 
 public int fcnt(){
	 int c=0;
	 
	 try {
	   FileInputStream fin = new FileInputStream(zipFile);
	   ZipInputStream zin = new ZipInputStream(fin);	 
	   ZipEntry ze = null;
	   while ((ze = zin.getNextEntry()) != null)
	   {
	    //Log.v("Decompress", "Unzipping " + ze.getName());
	    
	    if(ze.isDirectory())
	    {
	     dirChecker(ze.getName());
	    }
	    else
	    {
	    	c=c+1;
	    }	
	   }
	 }catch(Exception e){
		 return -1;
		 
	 }
	   
	   
	 return c;
 }
 
 public boolean unzip()
 {
  try
  {
   FileInputStream fin = new FileInputStream(zipFile);
   ZipInputStream zin = new ZipInputStream(fin);
   ZipEntry ze = null;
   while ((ze = zin.getNextEntry()) != null)
   {
    //Log.v("Decompress", "Unzipping " + ze.getName());
    
    if(ze.isDirectory())
    {
     dirChecker(ze.getName());
    }
    else
    {
    	filecount=filecount+1;	 
     FileOutputStream fout = new FileOutputStream(location + ze.getName());     

     byte[] buffer = new byte[8192];
     int len;
     while ((len = zin.read(buffer)) != -1)
     {
      fout.write(buffer, 0, len);
     }
     fout.close();

     zin.closeEntry();

    }

   }
   zin.close();
  }
  catch(Exception e)
  {
   //Log.e("Decompress", "unzip", e);
   return false;
  }
  
  File file = new File(zipFile);
  if(file.exists()) { 
      boolean result = file.delete();
  }   	    			    	  	    				    
 
  
  return true;
 }

 private void dirChecker(String dir)
 {
  File f = new File(location + dir);
  if(!f.isDirectory())
  {
   f.mkdirs();
  }
 }
}