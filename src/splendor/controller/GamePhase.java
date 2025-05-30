package splendor.controller;



public sealed interface GamePhase permits Base, Complet{
	boolean nobles();
	boolean reseravtion();
	
}
