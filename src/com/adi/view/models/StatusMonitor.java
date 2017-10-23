/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.view.models;

/**
 * Interface que determina um componente monitor de status (exemplo: barra de progresso).
 * <br>Esse componente deverá ser atualizável por diferentes threads e exibir o status corrente.
 * @author Caique
 */
public interface StatusMonitor {
    
    /**
     * Inicia/reseta o status do componente.
     * Por padrão, deve definir como indeterminado, escala padrão e com total igual a zero.
     */
    public void initStatus();
    
    /**
     * Define se o valor é escalável ou não.
     * @param indeterm Se o valor não pode ser medido, use TRUE.
     */
    public void setIndeterminate(boolean indeterm);
    
    /**
     * Altera a escala padrão de exibição de um status.
     * @param scale {@link StatusMonitorScale} a ser usado.
     */
    public void setScale(StatusMonitorScale scale);
    
    /**
     * Define o total de elementos/último elemento/tamanho do elemento.
     * <br>Este total define o valor máximo que o status pode chegar, mas não necessariamente o limite do componente que o exibe.
     * Exemplo: Uma lista com 5000 itens não vai definir o valor máximo de uma barra de progresso como 5000 se a escala for Porcentagem - mas o 5000 tornará-se o equivalente a 100%.
     * @param total O valor máximo do status. (ex.: 5000 para uma lista com 5000 itens)
     */
    public void setTotal(long total);
    
    /**
     * Define a posição ou elemento atual do status.
     * <br>Entenda que esse método será chamado várias vezes em um progresso, então cuidado com a forma de implementá-lo.
     * Note que esse valor não é dependente da escala. Ou seja, se quer falar de 50% de uma lista de 5000 itens verificada, não escreva 50, escreva o índice 2500.
     * @param position Inteiro da posição (ex.: índice 425 de uma lista de 5000 itens).
     */
    public void setActualPosition(long position);
    
    /**
     * Define uma mensagem a ser exibida.
     * <br>Implemente esse método se houver uma interface que exiba a mensagem sem conflitar com a alteração de posição.
     * Se não for implementado, esse método simplesmente não faz coisa alguma.
     * @param msg 
     */
    public default void setMessage(String msg){
    }
    
    /**
     * Altera a posição atual e exibe uma mensagem. Útil para listas em que há um nome para cada elemento, por exemplo.
     * <br>Esse método é apenas um atalho para o {@link #setActualPosition(int)} juntamente com {@link #setMessage(java.lang.String)}.
     * @param position
     * @param msg 
     */
    public default void setActualPosition(long position, String msg){
        setActualPosition(position);
        setMessage(msg);
    }
    
    /**
     * Finaliza a exibição do status.
     * <br>Em geral, deve significar que o status atingiu seu final (natural ou forçadamente). Pode exibir uma mensagem, abrir uma janela, etc.
     */
    public void finalizeStatus();
    
    /**
     * Finaliza a exibição do status e exibe uma mensagem.
     * <br>Atalho para o método {@link #finalizeStatus()} juntamente com {@link #setMessage(java.lang.String)}.
     * @param msg Mensagem a ser exibida.
     */
    public void finalizeStatus(String msg);
    
}
