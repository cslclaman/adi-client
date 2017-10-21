Sistema de categorização de imagens de animes baixadas de imageboards como Danbooru.

Requisitos (ordem de prioridade):

1. O sistema deve mapear pastas definidas pelo usuário e, ao encontrar novas imagens nessas pastas, cadastrá-las (ou, se já existirem, atualizá-las) no banco de dados.
	1.1. Isso inclui a associação de tags (chamadas AdiTags) usando dados de APIs como a do Danbooru.
Implementação: Serviço local (exemplos: Dropbox, Onedrive).

2. O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos).
Implementação: Módulo de API que retorna JSON/XML (exemplos: Facebook, Meetup).

3. O sistema deve permitir a exibição de imagens/vídeos e busca interativa de tags, independente dos outros sistemas (não precisa acessar a base de dados diretamente, pode usar a API).
Implementação: Sistema WEB (exemplos: Danbooru, Shuushuu).