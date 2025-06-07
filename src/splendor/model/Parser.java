package splendor.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Parser {
	
	public static HashMap<Integer, List<Card>> getDeveloppementCard(Path path, Charset charset) {
		var res = new  HashMap<Integer, List<Card>>();
		
		try(var reader = Files.newBufferedReader(path, charset)) {
			String line;
			int currentLevel = -1, currentPrestige = 0, currentId = 0;
	        Stones currentStone = null;

			reader.readLine();
	        reader.readLine();
	        
			while((line = reader.readLine()) != null) {
				if (line.isBlank()) continue;
				
				String[] parts = line.split(",", -1);
				
					if(!parts[0].isBlank()) currentLevel = Integer.parseInt(parts[0]);
					if(!parts[1].isBlank()) currentStone = parseStone(parts[1]);
					currentPrestige = (parts[2].isBlank() ? 0 : Integer.parseInt(parts[2]));
					System.out.println(currentPrestige);
					var price = parsePrice(parts[3]);
					var card = new Card(currentId, currentStone, price, currentPrestige);
					currentId++;
					
					res.computeIfAbsent(currentLevel, level -> new ArrayList<>()).add(card);
			}
		} catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return res;
	}
	
	
	public static List<Noble> getNobles(Path path, Charset charset) {
		var res = new ArrayList<Noble>();
		
		try(var reader = Files.newBufferedReader(path, charset)) {
			String line;
			
			String name = null;
			int currentPrestige = 0, currentId = 0;

			reader.readLine();
	        reader.readLine();
	        
			while((line = reader.readLine()) != null) {
				if (line.isBlank()) continue;
				
				String[] parts = line.split(",", -1);
					if(!parts[0].isBlank()) name = parts[0];
 					if(!parts[1].isBlank()) currentPrestige = Integer.parseInt(parts[1]);
					
					var price = parsePrice(parts[2]);
					var noble = new Noble(currentId, name, price, currentPrestige);
					currentId++;
					
					res.add(noble);
			}
		} catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return res;
	}
	
	
	private static Price parsePrice(String p) {
		Objects.requireNonNull(p);
		String[] parts = p.split("\\+");
		
		int r = 0, s = 0, d = 0, e = 0, o = 0;
		
		for(String tmp: parts) {
			var quantity = Character.getNumericValue(tmp.charAt(0));
			var stone = parseStone(String.valueOf(tmp.charAt(1)));
			
			switch (stone) {
            case RUBY -> r += quantity;
            case SAPHIR -> s += quantity;
            case DIAMOND -> d += quantity;
            case EMERALD -> e += quantity;
            case ONYX -> o += quantity;
			default -> throw new IllegalArgumentException("Unexpected value: " + stone);
        }
			
		}
		return new Price(r, s, d, e, o);
		
	}
	
	private static Stones parseStone(String color) {
	    return switch (color.toLowerCase()) {
	        case "black", "k" -> Stones.ONYX;
	        case "blue", "u" -> Stones.SAPHIR;
	        case "green", "g" -> Stones.EMERALD;
	        case "red", "r"   -> Stones.RUBY;
	        case "white", "w" -> Stones.DIAMOND;
	        default -> throw new IllegalArgumentException("Couleur inconnue : " + color);
	    };
	}
}
