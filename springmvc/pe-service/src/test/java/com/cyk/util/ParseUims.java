package com.cyk.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


import com.common.utils.ParamsUtil;
import net.sf.json.JSONObject;

public class ParseUims {
	
	
	public static void main(String[] args) throws Exception {
		
		String fdir = "D:\\长宁\\UIM拆分\\dow\\rdata.txt";
		fdir = "D:\\长宁\\UIM拆分\\dow\\1.txt";
		
		String deFlp = "D:\\长宁\\UIM拆分\\dow\\sql-";
		
		
		 //指定读取文件所在位置
        File file = new File(fdir);
        if(!file.exists()) {
        	System.out.println("文件不存在");
        	return;
        }
        FileChannel fileChannel = new RandomAccessFile(file,"r").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        
        int total = 0;// 记录数
        int suc = 0;// 有效json数
        int fss = 0;// 每1000条一个文件
        int fno = 1;//SQL文件索引
        //使用temp字节数组用于存储不完整的行的内容
        byte[] temp = new byte[0];
        while(fileChannel.read(byteBuffer) != -1) {
            byte[] bs = new byte[byteBuffer.position()];
            byteBuffer.flip();
            byteBuffer.get(bs);
            byteBuffer.clear();
            int startNum=0;
            //判断是否出现了换行符，注意这要区分LF-\n,CR-\r,CRLF-\r\n,这里判断\n
            boolean isNewLine = false;
            for(int i=0;i < bs.length;i++) {
                if(bs[i] == 32) {
                    isNewLine = true;
                    startNum = i;
                }
            }
 
            if(isNewLine) {
                //如果出现了换行符，将temp中的内容与换行符之前的内容拼接
                byte[] toTemp = new byte[temp.length+startNum];
                System.arraycopy(temp,0,toTemp,0,temp.length);
                System.arraycopy(bs,0,toTemp,temp.length,startNum);
                String one = new String(toTemp, "UTF-8");
                if(one.lastIndexOf(",")>one.lastIndexOf("}")){
                	one = one.substring(0, one.lastIndexOf(","));
                }
				System.out.println(one);
                // 解析json转换为sql
                total++;
                try {
					JSONObject oneJson = JSONObject.fromObject(one);
					String user = parse2SimpleUser(oneJson);
					if(!ParamsUtil.checkNull(user)){
						suc++;
						if(fss>=1000){
							fno++;
							fss = 0;
						}
						appendCtx2File(deFlp+fno+".sql", user);
						fss++;						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                //将换行符之后的内容(去除换行符)存到temp中
                temp = new byte[bs.length-startNum-1];
                System.arraycopy(bs,startNum+1,temp,0,bs.length-startNum-1);
                //使用return即为单行读取，不打开即为全部读取
//                return;
            } else {
                //如果没出现换行符，则将内容保存到temp中
                byte[] toTemp = new byte[temp.length + bs.length];
                System.arraycopy(temp, 0, toTemp, 0, temp.length);
                System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                temp = toTemp;
            }
 
        }
        if(temp.length>0) {
            System.out.println(new String(temp));
        }
		System.out.println(String.format("总记录数=%s,\tjson转换数=%s,\tsql文件数=%s", total, suc, fno));
		
		
		
	}
	
	
	static String parse2SimpleUser(JSONObject obj) {
		if(obj.containsKey("cGuid")) return obj.getString("cGuid");


		return "";
	}

	
	/**
	 * 写文件
	 */
	 public static void appendCtx2File(String path, String ctx) {
		 PrintWriter pw = null;
		 try  {
			 //如果文件存在，则追加内容；如果文件不存在，则创建文件
			 pw = new PrintWriter(new FileWriter(new File(path), true));
			 pw.println(ctx);
			 pw.flush();
		} catch (Exception e) {
			System.out.println(path+" ex:"+e.getMessage());
		}finally {
			if(null !=pw){
				try {
					pw.close();
				} catch (Exception e2) {
				}
			}
		}
	        
	}
	

}
