package com.example.shax_app.Rooms.RoomLogic.GameEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class BoardUtils{
	
	public static final int[][] neighborsList = {
	    {1, 9},          // 0  top-left (outer)
	    {0, 2, 4},       // 1  top-mid (outer)
	    {1, 14},         // 2  top-right (outer)
	    {4, 10},         // 3  top-left (middle)
	    {1, 3, 5, 7},    // 4  top-mid (middle)
	    {4, 13},         // 5  top-right (middle)
	    {7, 11},         // 6  top-left (inner)
	    {4, 6, 8},       // 7  top-mid (inner)
	    {7, 12},         // 8  top-right (inner)
	    {0, 10, 21},     // 9  mid-left (outer)
	    {3, 9, 11, 18},  // 10 mid-left (middle)
	    {6, 10, 15},     // 11 mid-left (inner)
	    {8, 13, 17},     // 12 mid-right (inner)
	    {5, 12, 14, 20}, // 13 mid-right (middle)
	    {2, 13, 23},     // 14 mid-right (outer)
	    {11, 16},        // 15 bottom-left (inner)
	    {15, 17, 19},    // 16 bottom-mid (inner)
	    {12, 16},        // 17 bottom-right (inner)
	    {10, 19},        // 18 bottom-left (middle)
	    {16, 18, 20, 22},// 19 bottom-mid (middle)
	    {13, 19},        // 20 bottom-right (middle)
	    {9, 22},         // 21 bottom-left (outer)
	    {19, 21, 23},    // 22 bottom-mid (outer)
	    {14, 22}         // 23 bottom-right (outer)
	};	

	public static final int[][] All_Mills = {
	    // Horizontal
	    {0, 1, 2},
	    {3, 4, 5},
	    {6, 7, 8},
	    {9, 10, 11},
	    {12, 13, 14},
	    {15, 16, 17},
	    {18, 19, 20},
	    {21, 22, 23},
	    // Vertical
	    {0, 9, 21},
	    {3, 10, 18},
	    {6, 11, 15},
	    {2, 14, 23},
	    {5, 13, 20},
	    {8, 12, 17},
	    // Cross
	    {1, 4, 7},
	    {16, 19, 22}
    };

	private static final HashMap<String, int[]> neighbors = new HashMap<>();
	private static final HashMap<Integer, ArrayList<int[]>> millsAtSpot = new HashMap<>();

	 static {                         // runs once when the class loads
		for (int i = 0; i < neighborsList.length; i++) {
		    neighbors.put(String.valueOf(i), neighborsList[i]);
		
		}
	    }


	static {
		// loop through each in all mills and put in appropriate spot 
		for (int[] mill : All_Mills){
			for (int num : mill){
				//check if key exists in hashmap
				if(millsAtSpot.containsKey(num)){
					ArrayList<int[]> mills = millsAtSpot.get(num);
					mills.add(mill);
					millsAtSpot.replace(num, mills);
				} else {
					ArrayList<int[]> mills = new ArrayList<>();
					mills.add(mill);
					millsAtSpot.put(num, mills);
				}

			}
		}
	}

	


		
	public static boolean isNeighbor(String from, String to){
		int[] neighbors = BoardUtils.neighbors.get(from);
		int toNum;
		try{
			toNum = Integer.parseInt(to);

		}catch (NumberFormatException e){
			return false;
		}


		for (int neighbor : neighbors){
			if (neighbor == toNum){
				return true;
			}
		}

		return false;
	}

	public static int[] getNeighbors(String spotNumber){
		return BoardUtils.neighbors.get(spotNumber);
	}	

	public static Boolean madeMill(Move move, HashMap<String, Spot> board){
		if (move instanceof PlacePiece placePiece){
				Integer spotNumInt = Integer.parseInt(placePiece.getSpotNumber());
				ArrayList<int[]> potentialMills = millsAtSpot.get(spotNumInt);
			
				for (int[] mill : potentialMills){
					for (int spot : mill){

						if (spot != spotNumInt){
							if (placePiece.getPlayer() != board.get(String.valueOf(spot)).getOccupiedBy()){
								break;
							}
							
						}

						if(spot == mill[mill.length - 1]){
							return true;
						} 



					}

					
				}
		} else if (move instanceof MoveSpot moveSpot){
			
			
				Integer spotNumInt = Integer.parseInt(moveSpot.getTo());
				ArrayList<int[]> potentialMills = millsAtSpot.get(spotNumInt);
			
				for (int[] mill : potentialMills){
					for (int spot : mill){

					if (spot != spotNumInt){
						if (moveSpot.getPlayer() != board.get(String.valueOf(spot)).getOccupiedBy()){
							break;
						}
						
					}

					if(spot == mill[mill.length - 1]){
						
						return true;
						} 



					}

					
					}
 
		}

		return false;
	}

}




