# Check All Pages

![Tela Inicial](/docs/attachments/init_screen.png)

Esse projeto tem objetivo de automaticamente criar relatórios do PageSpeed em todas as páginas de um
sitemap.xml, criando um relatório unificado em `.csv`, nesse relatório unificado é coletado apenas
as métricas do Lighthouse.

## Como usar:

- Instale o aplicativo para seu sistema operacional
- Ao abrir o aplicativo, no icone da engrenagem na tela de geração insira sua chave API do Google,
  para realizar as requisições do PageSpeed.
- Após salvar a chave API, basta encontrar o sitemap.xml do seu site, geralmente fica no caminho:
  `https://exemple.com/sitemap.xml` ou `https://exemple.com/sitemap_index.xml`
  Mas verifique se é seu caso.
    - É importante que o site map, tenha de fato a lista de urls que você deseja analisar, caso eles
      sejam xml's que apontam para outro sitemap filho, então insira a url do sitemap filho.
        - Exemplo:
          ![print](/docs/attachments/img.png)
          Nesse caso, é necessário entrar no sitemap filho para poder de fato ver as urls do seu
          site listadas.

Obs: Momentaneamente no alpha a pasta dos relatórios ficam em:
Linux `~/.local/share/MyApplication/`
Wind ``SCRE