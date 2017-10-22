/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source;

import com.adi.model.source.danbooru.DanbooruPost;

/**
 * Interface para identificar uma classe de entidade pesquisável de uma origem.
 * <br>A classe que a implementa pode representar dados trazidos por uma API.
 * Em geral, não deve ser dependente de um ou outro tipo de retorno (XML/JSON),
 * também não precisa representar todos os atributos trazidos pela API, apenas os necessários para pesquisa do ADI.
 * @author Caique
 */
public interface Searchable {
    
    /**
     * Identifica classes que estejam relacionadas a um Post (entidade principal em imageboards danbooru-like).
     * <br>O post, em geral, contém id, URL da imagem, data de envio e tags.
     */
    public static final String TYPE_POST = "post";
    
    /**
     * Identifica classes que estejam relacionadas a uma tag (identificadores de pesquisa em posts).
     * <br>Uma tag é associada, normalmente, a uma descrição e um tipo (personagem, copyright, formato da imagem, etc).
     */
    public static final String TYPE_TAG = "tag";
    
    /**
     * Lista os nomes <b>dos tipos</b> das sources suportadas.
     * <br>Isso significa que os atributos da classe tem devidas anotações e conversores para armazenar dados de cada uma das sources listadas.
     * @return Array com nomes dos tipos das sources (ex.: Danbooru2, Gelbooru, Moebooru).
     */
    public String[] supportedSourceTypeList();
    
    /**
     * Retorna se uma determinada source é suportada pela classe ou não.
     * <br>Se suportada, isso significa que os atributos da classe tem devidas anotações e conversores para armazenar dados dessa determinada source.
     * <br>A implementação padrão retorna TRUE se o nome informado existir na lista retornada pelo método {@link #supportedSourceTypeList()}
     * @param source Nome da source a ser testada
     * @return TRUE se os dados da source podem ser representados por essa classe, FALSE se não.
     */
    public default boolean isSourceSupported(String source){
        for (String s : supportedSourceTypeList()){
            if (s.equals(source)) return true;
        }
        return false;
    }
    
    /**
     * Retorna o tipo de entidade representada pela classe (exemplo: Post ou Tag)
     * @return string conforme as constantes {@link #TYPE_POST} e {@link #TYPE_TAG}, por exemplo.
     */
    public String getSearchableType();
    
    /**
     * A partir do tipo de entidade e do nome da origem, cria e retorna uma instância de classe específica.
     * <table>
     *      <tr>
     *          <th colspan=3>Instâncias suportadas</th>
     *      </tr>
     *      <tr>
     *          <th>Nome da classe</th><th>Origem implement.</th><th>Tipo de entidade</th>
     *      </tr>
     *      <tr>
     *          <td> {@link DanbooruPost} </td><td>Danbooru ({@link DanbooruModel})</td><td> {@link #TYPE_POST} </td>
     *      </tr>
     * </table>
     * @param source Nome do tipo da source (ex. Danbooru2, Moebooru)
     * @param type Tipo da entidade a pesquisar, conforme constantes {@link #TYPE_POST} e {@link #TYPE_TAG}, por exemplo.
     * @return instância de um Searchable do tipo e origem especificado, ou NULL se não existir.
     */
    public static Searchable getInstance (String source, String type){
        Searchable[] sl = new Searchable[]{
            new DanbooruPost()
        };
        for (Searchable s : sl){
            if (s.isSourceSupported(source) && s.getSearchableType().equalsIgnoreCase(type)){
                return s;
            }
        }
        return null;
    }
}
