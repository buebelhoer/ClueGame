package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, Color color, Random random, ArrayList<Card> roomCards,  ArrayList<Card> personCards, ArrayList<Card> weaponCards) {
		super(name, color);

		rng = random;
	}

}
