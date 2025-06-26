package br.com.dio.model;

/**
 * Representa uma célula do tabuleiro de Sudoku.
 */
public class Space {

    private Integer value;           // Valor atual inserido pelo jogador (ou fixo)
    private final int expected;     // Valor correto (usado em modos com verificação)
    private final boolean fixed;    // Indica se essa célula é parte do template original

    /**
     * Construtor da célula.
     *
     * @param expected valor correto da célula
     * @param fixed    se é uma célula fixa do tabuleiro inicial
     */
    public Space(final int expected, final boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed) {
            this.value = expected;
        }
    }

    /**
     * Retorna o valor atual da célula.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Define um novo valor para a célula, se não for fixa.
     *
     * @param value valor a ser inserido (1 a 9 ou null para limpar)
     */
    public void setValue(final Integer value) {
        if (!fixed) {
            this.value = value;
        }
    }

    /**
     * Limpa a célula (define como null), se não for fixa.
     */
    public void clear() {
        setValue(null);
    }

    /**
     * Retorna o valor correto da célula, usado para validação.
     */
    public int getExpected() {
        return expected;
    }

    /**
     * Informa se a célula é fixa (pré-preenchida).
     */
    public boolean isFixed() {
        return fixed;
    }

    @Override
    public String toString() {
        return value == null ? "." : String.valueOf(value);
    }
}
