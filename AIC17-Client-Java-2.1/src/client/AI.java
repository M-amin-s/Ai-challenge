package client;

import client.model.*;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import java.util.Random;

/**
 * AI class.
 * You should fill body of the method {@link #doTurn}.
 * Do not change name or modifiers of the methods or fields
 * and do not add constructor for this class.
 * You can add as many methods or fields as you want!
 * Use world parameter to access and modify game's
 * world!
 * See World interface for more details.
 */
public class AI {

    public void doTurn(World game) {
        // fill this method, we've presented a stupid AI for example!
        Random rand = new Random();
        BeetleType defender,attacker;
        Cell[][] cells = game.getMap().getCells();
        defender = findDefender(cells);
        if (defender == BeetleType.HIGH)
            attacker = BeetleType.LOW;
        else
            attacker = BeetleType.HIGH;
        if (game.getCurrentTurn() == 0) {
            AttackerStrategy(game, attacker);
            DefenderStrategy(game, defender);
        } else {
            // no strategy change
        }

    }

    public BeetleType findDefender(Cell[][] cells){
        int hasWingLow=0,hasWingHigh=0;
        for (int i=0;i<cells.length;i++){
            for (int j=0;j<cells[i].length;j++) {
                if (cells[i][j].getBeetleEntity() != null &&((Beetle)cells[i][j].getBeetleEntity()).has_winge() &&  (((Beetle)cells[i][j].getBeetleEntity()).getCellState() == CellState.Ally)){
                    if (((Beetle) cells[i][j].getBeetleEntity()).getBeetleType() == BeetleType.LOW){
                        hasWingLow++;
                    }
                    else
                        hasWingHigh++;
                }
            }
        }
        if (hasWingHigh >= hasWingLow)
            return BeetleType.HIGH;
        else
            return BeetleType.LOW;
    }

    public void AttackerStrategy(World game, BeetleType attacker) {
        for (int i=0;i<3;i++){
            for (int j=0;j<2;j++){
                for (int k=0;k<3;k++){
                    if ((i==2 && k==0) || (i==2 && k==2 && j==0))
                        game.changeStrategy(attacker, CellState.values()[i],
                                CellState.values()[j], CellState.values()[k], Move.turnRight);
                    else
                        game.changeStrategy(attacker, CellState.values()[i],
                                CellState.values()[j], CellState.values()[k], Move.stepForward);
                }
            }
        }
    }

    public void DefenderStrategy(World game, BeetleType defender){
        for (int i=0;i<2;i++){
            game.changeStrategy(defender, CellState.Ally,
                    CellState.values()[i], CellState.Ally, Move.stepForward);
            game.changeStrategy(defender, CellState.Ally,
                    CellState.values()[i], CellState.Enemy, Move.turnRight);
            game.changeStrategy(defender, CellState.Ally,
                    CellState.values()[i], CellState.Blank, Move.turnLeft);
            game.changeStrategy(defender, CellState.Enemy,
                    CellState.values()[i], CellState.Ally, Move.turnRight);
            game.changeStrategy(defender, CellState.Enemy,
                    CellState.values()[i], CellState.Enemy, Move.turnLeft);
            game.changeStrategy(defender, CellState.Enemy,
                    CellState.values()[i], CellState.Blank, Move.turnLeft);
            game.changeStrategy(defender, CellState.Blank,
                    CellState.values()[i], CellState.Ally, Move.stepForward);
            game.changeStrategy(defender, CellState.Blank,
                    CellState.values()[i], CellState.Enemy, Move.turnRight);
            game.changeStrategy(defender, CellState.Blank,
                    CellState.values()[i], CellState.Blank, Move.stepForward);
        }
    }

}

