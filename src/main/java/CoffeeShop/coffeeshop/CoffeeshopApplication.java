package CoffeeShop.coffeeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
@SpringBootApplication
public class CoffeeshopApplication {

	public static void main(String[] args) {
		System.out.println(reverseString("potato"));
		SpringApplication.run(CoffeeshopApplication.class, args);
	}

	//get unique char from string
	char uniqueChar(String s){
		if(s.isEmpty()) return '\u0000'; 
		else if(s.length()==1) return s.charAt(0);
		Map<Character,Integer> freq = new HashMap<>();
		for(char c  : s.toCharArray() ){
			freq.put(c,freq.getOrDefault(c, 0) + 1 );
		}
		for(Entry<Character,Integer> entry : freq.entrySet()){
			if(entry.getValue() == 1) return entry.getKey();
		}
		//return empty char
		return '\u0000';

	}
	//reverse string
	public static String reverseString(String s){
		StringBuilder builder = new StringBuilder(s);
		return builder.reverse().toString();
	}
	//is string unique
	//is anagram
	

}
