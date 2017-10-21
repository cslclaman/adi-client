<html>
<p>Sistema de categorização de imagens de animes baixadas de imageboards como Danbooru.</p>

<p><b>Requisitos (ordem de prioridade):</b></p>
<ol> 
<li>
	<p>O sistema deve mapear pastas definidas pelo usuário e, ao encontrar novas imagens nessas pastas, cadastrá-las (ou, se já existirem, atualizá-las) no banco de dados.</p>
	<p><ol><li>1.1. Isso inclui a associação de tags (chamadas AdiTags) usando dados de APIs como a do Danbooru.</li></ol></p>
	<p><i>Implementação: Serviço local (exemplos: Dropbox, Onedrive).</i></p>
</li>
<li>
	<p>O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos).</p>
	<p><i>Implementação: Módulo de API que retorna JSON/XML (exemplos: Facebook, Meetup).</i></p>
</li>

<li>
	<p>O sistema deve permitir a exibição de imagens/vídeos e busca interativa de tags, independente dos outros sistemas (não precisa acessar a base de dados diretamente, pode usar a API).</p>
	<p><i>Implementação: Sistema WEB (exemplos: Danbooru, Shuushuu).</i></p>
</li>
</html>