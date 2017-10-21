Sistema de categoriza��o de imagens de animes baixadas de imageboards como Danbooru.

Requisitos (ordem de prioridade):

1. O sistema deve mapear pastas definidas pelo usu�rio e, ao encontrar novas imagens nessas pastas, cadastr�-las (ou, se j� existirem, atualiz�-las) no banco de dados.
	1.1. Isso inclui a associa��o de tags (chamadas AdiTags) usando dados de APIs como a do Danbooru.
Implementa��o: Servi�o local (exemplos: Dropbox, Onedrive).

2. O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos).
Implementa��o: M�dulo de API que retorna JSON/XML (exemplos: Facebook, Meetup).

3. O sistema deve permitir a exibi��o de imagens/v�deos e busca interativa de tags, independente dos outros sistemas (n�o precisa acessar a base de dados diretamente, pode usar a API).
Implementa��o: Sistema WEB (exemplos: Danbooru, Shuushuu).