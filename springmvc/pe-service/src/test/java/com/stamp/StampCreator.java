package com.stamp;

import java.io.BufferedInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;  
import java.io.IOException;  

/**
* @brief StampCreator
*/
public class StampCreator
{
	public static native	int unlock(String param1, String param2);

	public static native	byte[] make(float stamp_size, float star_size, float border_size, String text, String font, float font_size, float angle_range, float dist_text, int color);

	public static native	byte[] makePersional(float stamp_size, float border_size, String text, String font, float font_size, int color);

	public static void main(String[] args) {

	try {
		byte[] stamp_bytes = StampCreator.makePersional(12.69999f, 0.2f, "宋某",
				"D:\\simsun.ttc", 40.0f, 0xFFFF0000);

		if (stamp_bytes != null)
		{
			FileOutputStream fos = new FileOutputStream("C:\\Users\\49762\\Desktop\\stamp_java2.png");
			fos.write(stamp_bytes, 0, stamp_bytes.length);
 			fos.flush();
 			fos.close();
			System.out.println("stamp create success!!!");
		}
		else
		{
			System.out.println("stamp create failed!!!");
		}
		}
		catch (IOException e)
        {
				System.out.println("IOException e");
        }  
		finally  
        {  
        }
	}

	static {
		System.out.println(System.getProperty("os.arch"));
//		System.loadLibrary("stampcreator");
		System.load("D:\\stampcreator.dll");

		int u = StampCreator.unlock("B33EEA3B-20CB-4179-B872-78FCCE7A386C", "3967DC49-EA68-42AC-A369-96078740F076");
		System.out.println(String.format(" StampCreator.unlock = %d", u));
	}
}
