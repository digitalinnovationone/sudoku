package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.COMPLETE;
import static br.com.dio.model.GameStatusEnum.INCOMPLETE;
import static br.com.dio.model.GameStatusEnum.NON_STARTED;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;

public class Board {

    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus(){
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){
        if(getStatus() == NON_STARTED){
            return false;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && !s.getActual().equals(s.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value){
        Space space = spaces.get(col).get(row);
        
        if (space.isFixed()){
            return false;
        }

        List<Integer> frameNumbers = getFrameNumbers(col, row);

        for (Integer integer : frameNumbers) {
            System.out.println(integer);
        }

        if(frameNumbers.stream().anyMatch(s -> s.equals(value))) {
            System.out.println("Voce nao pode colocar numeros repetidos dentro do mesmo quadro!");
            return false; 
        }

        space.setActual(value);
        return true;
    }

    /**
     * <p>
     * Devido ao fato dos requisitos deixarem claro que há uma opção exclusiva para
     * remover um numero de uma posicao, eu fiz a deducao de que:<br><br>
     * 
     * <strong>1.</strong> Para seguir os requisitos a risca, eu teria que retornar um erro apenas quando
     * os numeros dentro de um mesmo quadro fossem repetidos;<br><br>
     * <strong>2.</strong> Caso o numero apenas esteja em sua posicao incorreta, ficara a cargo do jogador descobrir!;<br><br>
     * 
     * Estas minha visao se deu principalmente para manter a integridade dos requisitos, pois,
     * se eu retorno um erro sempre que um numero esta em sua posicao incorreta, logo, eu nao preciso
     * disponibilizar uma opcao de apagar o numero do meu tabuleiro, afinal o próprio jogo nao deixaria
     * voce colocar um número!
     * </p?>
     */
    public List<Integer> getFrameNumbers(int col, int row) {
        List<Integer> frameNumbers = new ArrayList<>();
        
        int minColValue, minRowValue;

        minColValue = getMinValue(col);
        minRowValue = getMinValue(row);
        
        for(int i = minColValue; i < (minColValue+2); i++){

            for (int j = minRowValue; j < (minRowValue+2); j++) {

                Integer value = spaces.get(i).get(j).getActual();

                if(value != null)
                    frameNumbers.add(spaces.get(i).get(j).getActual());
            }
        }

        return frameNumbers;
    }

    private int getMinValue(int input) {
        int minValue;
        if(input >=0 && input < 3)
            minValue = 0;
        else if(input >=3 && input < 6)
            minValue = 3;
        else
            minValue = 6;
        return minValue;
    }

    public boolean clearValue(final int col, final int row){
        Space space = spaces.get(col).get(row);
        if (space.isFixed()){
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

}
