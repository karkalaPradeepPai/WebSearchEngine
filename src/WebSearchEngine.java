import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class WebSearchEngine {
	
	
	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static Scanner sc = new Scanner(System.in);
	
	public WebSearchEngine()
	{
		try {
			HTMLTextConverter.convertHtmlToText();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int searchWord(File filePath, String p1) {
		int counter=0;
		String data="";
		try
    	{
    		BufferedReader Object = new BufferedReader(new FileReader(filePath));
    		String line = null;
    		
              while ((line = Object.readLine()) != null){
              
            	  data= data+line;
             
             }
              Object.close();
      		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception:"+e);
    	}
		
			
			
			String txt = data;

			int offset1a = 0;

				for (int loc = 0; loc <= txt.length(); loc += offset1a + p1.length()) {

					offset1a = search1(p1, txt.substring(loc)); 
					if ((offset1a + loc) < txt.length()) {
						counter++;
						System.out.println(p1 + " at position " + (offset1a + loc));   //printing position of word
					}
				}
				if(counter!=0)	{			
					System.out.println("\nFound in "+filePath.getName());	
					
					
				}
				return counter;
	}
	
	// Brute force method : Just matches and returns the offset
	public static int search1(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();

		for (int i = 0; i <= N - M; i++) {
			int j;
			for (j = 0; j < M; j++) {
				if (txt.charAt(i + j) != pat.charAt(j))
					break;
			}
			if (j == M)
				return i; // found at offset i
		}
		return N; // not found
	}

	
	// Ranking of Web Pages using merge sort 
	//Collections.sort by default uses merge sort
	 public static void sortValue(Hashtable<?, Integer> t,int occur){
			

	       //Transfer as List and sort it
	       ArrayList<Map.Entry<?, Integer>> l = new ArrayList(t.entrySet());
	       
	      
	       
	       Collections.sort(l, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o1.getValue().compareTo(o2.getValue());
	        }});
	      
	       Collections.reverse(l);
	       
	      
	       if(occur!=0) {
		       System.out.println("\n------Web Page Ranking-----\n");
		       
		       int n = 5;
		       int j = 1;
				while (l.size() > j && n>0){
					System.out.println("("+j+") "+l.get(j)+" times ");
					j++;
					n--;
				}
	       }else {
	    	   
	       }  
	       
	    }
	 
	 /*using regex to find similar string to pattern */
	 public void alternativeWord(String p1) {
			try {
				
				// String to be scanned to find the pattern.
			      String line = " ";
			      String pattern3 = "[a-zA-Z0-9]+";
			      
			     
			      // Create a Pattern object
			      Pattern r3 = Pattern.compile(pattern3);
			      // Now create matcher object.
			      Matcher m3 = r3.matcher(line);
			      int _fileNumber=0;
					try {
						File _directory = new File("C:\\Users\\prade\\eclipse-workspace\\SearchEngine\\src\\ConvertedTextFiles\\");
						File[] _fileArray = _directory.listFiles();
						for(int i=0;i<100;i++)
						{
							findData(_fileArray[i],_fileNumber,m3,p1);
							_fileNumber++;
						}
						
				        Set keys = new HashSet();
				        Integer value =1;
				        Integer val = 0;
				        
				      
				        System.out.println("Did you mean? ");
				        for(Map.Entry entry: numbers.entrySet()){
				        	if(val == entry.getValue()) {
				        		
				        		break;
					           
				            }
				        	else {
				        		
				        		 if(value==entry.getValue()){
						                
						            	System.out.print(entry.getKey()+"  ");
				                
				            }
				            
				        	}}
				        


					} catch (Exception e) {
						System.out.println("Exception:"+e);
					}
					finally
					{
						
					}
				
				
				
			}
			catch(Exception e){
				
			}
		}
			
		//finds strings with similar pattern and calls edit distance() on those strings
		public static void findData(File _sourceFile,int fileNumber,Matcher _m3,String p1) throws FileNotFoundException,ArrayIndexOutOfBoundsException
	    {
	    	try
	    	{
	    		int i = 0;
	    		BufferedReader _rederObject = new BufferedReader(new FileReader(_sourceFile));
	    		String line = null;

	              while ((line = _rederObject.readLine()) != null){
	              _m3.reset(line);
	              while (_m3.find()) {
	            	  key.add(_m3.group());
	                }
	             }
	              _rederObject.close();
	              for(int p = 0; p<key.size(); p++){ 
	              	  numbers.put(key.get(p), editDistance(p1.toLowerCase(),key.get(p).toLowerCase()));
	                }
	    	}     
	    	catch(Exception e)
	    	{
	    		System.out.println("Exception:"+e);
	    	}
	    	
	    }
		
		//Uses Edit distance to compare nearest distance between keyword and similar patterns obtained from regex
		public static int editDistance(String word1, String word2) {
			int len1 = word1.length();
			int len2 = word2.length();
		 
			// len1+1, len2+1, because finally return dp[len1][len2]
			int[][] dp = new int[len1 + 1][len2 + 1];
		 
			for (int i = 0; i <= len1; i++) {
				dp[i][0] = i;
			}
		 
			for (int j = 0; j <= len2; j++) {
				dp[0][j] = j;
			}
		 
			//iterate though, and check last char
			for (int i = 0; i < len1; i++) {
				char c1 = word1.charAt(i);
				for (int j = 0; j < len2; j++) {
					char c2 = word2.charAt(j);
		 
					//if last two chars equal
					if (c1 == c2) {
						//update dp value for +1 length
						dp[i + 1][j + 1] = dp[i][j];
					} else {
						int replace = dp[i][j] + 1;
						int insert = dp[i][j + 1] + 1;
						int delete = dp[i + 1][j] + 1;
		 
						int min = replace > insert ? insert : replace;
						min = delete > min ? min : delete;
						dp[i + 1][j + 1] = min;
					}
				}
			}
		 
			return dp[len1][len2];
		}

	
	

	public static void main(String[] args) {
		
		
		WebSearchEngine bruteforce = new WebSearchEngine();
		
		
		Hashtable<String,Integer> occurrs = new Hashtable<String,Integer>();
		Scanner s = new Scanner (System.in);
		System.out.println("Enter your search: ");
		String p= s.nextLine();
		long fileNumber = 0;
		int occur=0;
		int pg=0;
		try {
			
			File dir = new File("C:\\Users\\prade\\eclipse-workspace\\SearchEngine\\src\\ConvertedTextFiles");
			
			File[] fileArray = dir.listFiles();
			for(int i=0;i<102;i++)
			{
				
				occur=bruteforce.searchWord(fileArray[i],p);
				occurrs.put(fileArray[i].getName(), occur);
				if(occur!=0) {
					pg++;
				}
							
				fileNumber++;
			}
			
			if(pg==0) {
				
				System.out.println("---------------------------------------------------");
		        System.out.println("Searching in web.....");
				bruteforce.alternativeWord(p);
				
			}
			sortValue(occurrs,pg);
		
		}
		catch (Exception e) {
			System.out.println("Exception:"+e);
		}
		finally
		{
				
		} 
		
		}
		
	}
	
