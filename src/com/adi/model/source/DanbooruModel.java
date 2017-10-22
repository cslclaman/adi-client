/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.model.source;

/**
 * Interface para identificar uma classe pesquisável do Danbooru e suas implementações (inclui Danbooru2)
 * <br>Qualquer uma de suas implementações deve representar dados das seguintes sources:
 * <ul>
 *      <li><b>Danbooru</b> - Inclui Sankaku Complex</li>
 *      <li><b>Danbooru2</b> - Inclui Danbooru/Safebooru (donmai.us)</li>
 * </ul>
 * @author Caique
 */
public interface DanbooruModel extends Searchable {
    @Override
    public default String[] supportedSourceTypeList() {
        return new String[]{
            "Danbooru",
            "Danbooru2",
        };
    }
}
