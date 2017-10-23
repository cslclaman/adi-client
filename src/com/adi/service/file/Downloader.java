/*
 * ADI 6 Project
 * Copyright C.S.L. ClaMAN 2017
 * This code is provided "as is". Modify this thing under your responsability.
 */
package com.adi.service.file;

import com.adi.instance.Configuration;
import com.adi.model.source.danbooru.DanbooruPost;
import com.adi.service.file.file.Archive;
import com.adi.view.models.StatusMonitor;
import com.adi.view.models.StatusMonitorScale;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Classe que tem a função de obter um arquivo da internet.
 * <br>Possui métodos para efetuar download e monitorá-los.
 * @author Caique
 */
public abstract class Downloader {
    
    /**
     * Faz download de um arquivo a partir da URL fornecida.
     * <br>O nome do arquivo será o mesmo que estiver na URL, truncado caso necessário. O arquivo será salvo na pasta TEMP.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @return Arquivo baixado com mesmo nome da URL - procure por "USUARIO/adi/temp/image.jpg".
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl) throws IOException {
        return download(fileUrl, getFileNameFromUrl(fileUrl), -1, null);
    }
    
    /**
     * Faz download de um arquivo a partir da URL fornecida e do caminho completo de destino.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @param destfile Caminho e nome do arquivo de destino. Ex.: "C:\Pasta\Sub pasta\imagem_baixada.jpg"
     * @return Arquivo baixado.
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl, String destfile) throws IOException {
        return download(fileUrl, destfile, -1, null);
    }
    
    /**
     * Faz download de um arquivo a partir da URL fornecida e do caminho completo de destino. Exibe status no {@link StatusMonitor} fornecido.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @param destfile Caminho e nome do arquivo de destino. Ex.: "C:\Pasta\Sub pasta\imagem_baixada.jpg"
     * @param monit Implementação de um monitor de Status.
     * @return Arquivo baixado.
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl, String destfile, StatusMonitor monit) throws IOException {
        return download(fileUrl, destfile, -1, monit);
    }
    
    /**
     * Faz download de um arquivo a partir da URL fornecida. Exibe status no {@link StatusMonitor} fornecido.
     * <br>O nome do arquivo será o mesmo que estiver na URL, truncado caso necessário. O arquivo será salvo na pasta TEMP.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @param monit Implementação de um monitor de Status.
     * @return Arquivo baixado com mesmo nome da URL - procure por "USUARIO/adi/temp/image.jpg".
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl, StatusMonitor monit) throws IOException {
        return download(fileUrl, getFileNameFromUrl(fileUrl), -1, monit);
    }
    
    /**
     * Faz download de um arquivo a partir da URL fornecida.. Exibe status no {@link StatusMonitor} fornecido, em porcentagem.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @param fileSize Tamanho (em bytes) total do arquivo a ser baixado. Use 0 ou um número negativo caso não tenha essa informação.
     * @param monit Implementação de um monitor de Status.
     * @return Arquivo baixado com mesmo nome da URL - procure por "USUARIO/adi/temp/image.jpg".
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl, long fileSize, StatusMonitor monit) throws IOException {
        return download(fileUrl, getFileNameFromUrl(fileUrl), fileSize, monit);
    }
    
    /**
     * Faz download de um arquivo a partir da URL fornecida e do caminho completo de destino. Exibe status no {@link StatusMonitor} fornecido, em porcentagem.
     * @param fileUrl URL de origem do arquivo. Ex.: "http://images.com/files/image.jpg"
     * @param destfile Caminho e nome do arquivo de destino. Ex.: "C:\Pasta\Sub pasta\imagem_baixada.jpg"
     * @param fileSize Tamanho (em bytes) total do arquivo a ser baixado. Use 0 ou um número negativo caso não tenha essa informação.
     * @param monit Implementação de um monitor de Status.
     * @return Arquivo baixado.
     * @throws IOException Caso a URL seja mal formulada, ou se houver erro na leitura do arquivo.
     */
    public static Archive download(String fileUrl, String destfile, long fileSize, StatusMonitor monit) throws IOException {
        if (monit != null){
            monit.initStatus();
            monit.setIndeterminate(false);
            if (fileSize <= 0){
                monit.setScale(StatusMonitorScale.SCALE_ABSOLUTE);
            } else {
                monit.setScale(StatusMonitorScale.SCALE_PERCENT);
                monit.setTotal(fileSize);
            }
        }
        File out = new File(destfile);
        if (!out.exists()){
            out.createNewFile();
        }
        URL url = new URL(fileUrl);
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(out);
        
        long actual = 0;
        int bytes;

        while ((bytes = is.read()) != -1) {
            fos.write(bytes);
            if (monit != null){
                monit.setActualPosition(actual++, destfile);
            }
        }
        
        if (monit != null){
            monit.finalizeStatus();
        }
        is.close();
        fos.close();
        
        return new Archive(out.getPath());
    }
    
    private static String getFileNameFromUrl(String fileUrl){
        String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String path = Configuration.TEMP_PATH;
        //Trunca nome caso seja maior que 220 caracteres. Fica tipo "nomemuitol+.ext"
        if (path.length() + name.length() > 220){
            name = name.substring(0, 214 - path.length()) + "+." + name.substring(name.lastIndexOf(".") + 1);
        }
        return path + name;
    }
    
    /**
     * Verifica, a partir de um {@link DanbooruPost} e de um {@link Archive}, se é necessário baixar novamente um arquivo.
     * <br>Usa a MD5 como fator de verificação, mas não baixa caso o Post seja ZIP e o Archive seja WEBM.
     * <br>A função desse método é verificar se o arquivo está corrompido ou se é uma versão alternativa (sample).
     * @param arc Arquivo a verificar.
     * @param post Post a verificar.
     * @return TRUE se deve ser baixado novamente, FALSE se não precisa/deve.
     */
    public static boolean needDownload (Archive arc, DanbooruPost post){
        if (post == null){
            post = new DanbooruPost();
        }
        boolean postHasMd5 = post.getMd5() != null && !post.getMd5().isEmpty();
        boolean postHasFileExt = post.getFileExt() != null && !post.getFileExt().isEmpty();
        boolean archiveIsWebm = arc.getExtension().equals("webm");
        boolean archiveQueryEqMd5 = arc.getQueryText().equals(arc.getMd5());
        if (postHasMd5){
            if (arc.getMd5().equals(post.getMd5())){
                return false;
            } else {
                return !(postHasFileExt && post.getFileExt().equals("zip") && archiveIsWebm);
            }
        } else {
            return !archiveQueryEqMd5;
        }
    }
}
