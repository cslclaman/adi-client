# ADI - Anime Downloaded Images
(sério, que nome inútil ¬¬')

## O que é:
Sistema de categorização de imagens de animes baixadas de imageboards como Danbooru.

## Requisitos (ordem de prioridade):
1. O sistema deve mapear pastas definidas pelo usuário e, ao encontrar novas imagens nessas pastas, cadastrá-las (ou, se já existirem, atualizá-las) no banco de dados.
	1. Isso inclui a associação de tags (chamadas AdiTags) usando dados de APIs como a do Danbooru.
	_Implementação: Serviço local (exemplos: Dropbox, Onedrive)._
1. O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos).</p>
	_Implementação: Módulo de API que retorna JSON/XML (exemplos: Facebook, Meetup)._
1. O sistema deve permitir a exibição de imagens/vídeos e busca interativa de tags, independente dos outros sistemas (não precisa acessar a base de dados diretamente, pode usar a API).</p>
	_Implementação: Sistema WEB (exemplos: Danbooru, Shuushuu)._
	
## Dependências:
* [GSON 2.8.2](https://github.com/google/gson)
* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Primefaces (em breve, provavelmente)](https://www.primefaces.org/)