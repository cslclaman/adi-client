<html>
<p>Sistema de categoriza��o de imagens de animes baixadas de imageboards como Danbooru.</p>

<p><b>Requisitos (ordem de prioridade):</b></p>
<ol> 
<li>
	<p>O sistema deve mapear pastas definidas pelo usu�rio e, ao encontrar novas imagens nessas pastas, cadastr�-las (ou, se j� existirem, atualiz�-las) no banco de dados.</p>
	<p><ol><li>1.1. Isso inclui a associa��o de tags (chamadas AdiTags) usando dados de APIs como a do Danbooru.</li></ol></p>
	<p><i>Implementa��o: Servi�o local (exemplos: Dropbox, Onedrive).</i></p>
</li>
<li>
	<p>O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos).</p>
	<p><i>Implementa��o: M�dulo de API que retorna JSON/XML (exemplos: Facebook, Meetup).</i></p>
</li>

<li>
	<p>O sistema deve permitir a exibi��o de imagens/v�deos e busca interativa de tags, independente dos outros sistemas (n�o precisa acessar a base de dados diretamente, pode usar a API).</p>
	<p><i>Implementa��o: Sistema WEB (exemplos: Danbooru, Shuushuu).</i></p>
</li>
</html>