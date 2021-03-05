package test;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordCount{
	
	public static void main(String[] args) throws IOException {  
		int lines = 0;  
		int characters = 0;
		int word = 0;
		long startTime = System.currentTimeMillis();
		File file = new File(args[0]);
		BufferedReader reader = null;
		try{
			InputStreamReader read = new InputStreamReader( new FileInputStream(file),"utf-8");
			reader = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String str = null;

			while((str = reader.readLine()) != null)
			{
				if(! str.equals(""))  //判断行数是否有效
				{
					lines++;			
				}	
				characters += countCharacters(str);  //对字符统计数累加
				word += countWords(str);  //对单词统计数累加
			}	
			int tchar;
			HashMap<String, Integer> wList = new HashMap<String, Integer>();
			while((tchar = read.read()) != -1)
			{
				char words = (char) tchar;
				if((words >= 'A' && words <= 'Z')) 
				{
					words += 32;
				}
				if ((words >= 'a' && words <= 'z')) 
				{
	               sb.append(words);
	            } 
				else if (sb.length() != 0) 
				{
	                String theword = new String(sb);
	                if (wList.containsKey(theword)) 
	                {
	                	wList.put(theword, wList.get(theword) + 1);
	                }
	                else 
	                {
	                	wList.put(theword, 1);
	                }
	                sb.delete(0,sb.length());
	            }
			}
			
			List<Map.Entry<String, Integer>> words = new ArrayList<Map.Entry<String, Integer>>(
			                wList.entrySet());

			        Collections.sort(words, new Comparator<Map.Entry<String, Integer>>() 
			        {
			            @Override
			            public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2)
			            {
			                return - (o1.getValue() - o2.getValue());
			            }
			        });
			System.out.println(characters);
			sb.append("characters:" + characters + "\n");
			System.out.println(lines);
			sb.append("lines:" + lines + "\n");
			System.out.println(word);
			sb.append("word:" + word + "\n");
			for(Map.Entry<String,Integer> node:words)
			{
				int k=0;
				if(k < 10)
				{
					System.out.println(node.getKey() + ":" + node.getValue() + "\n");
				    sb.append(node.getKey() + ":" + node.getValue() + "\n");
				}
				else 
				{
					break;
				}
				k++;
			}
			
			reader.close();
			FileWriter fw = new FileWriter(args[1]);  //写入文件
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			fw.close();
			}catch(Exception e){
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		long Time = endTime - startTime;
		System.out.println("耗时:" + Time + "毫秒");
	}

	/**
	 * 统计字符数
	 * @param str
	 * @return count
	 */
    public static int countCharacters(String str) 
    {
        int count = 0;
        Pattern p = Pattern.compile("[\\x00-\\x7F]");
        Matcher m = p.matcher(str);
        while(m.find()){
            count++;
        }
        return count;
    }
    
    /**
	 * 统计单词数
	 * @param str
	 * @return count
	 */
    public static int countWords(String str)
    {
    	 int count = 0;
    	 String ss = str.toLowerCase();
    	 Pattern p = Pattern.compile("[a-z]{4}[0-9a-z]*");    	 
         Matcher m = p.matcher(ss);
         while(m.find())
         {
             count++;
         }
		return count;
    }
    
}
