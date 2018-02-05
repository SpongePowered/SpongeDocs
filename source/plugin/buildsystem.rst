==================
Construir Sistemas
==================

Constrói sistemas como Gradle_ ou Maven_ podes gerir os processos de construção de todos os teus projetos. Sendo uma ferramenta
independente do teu IDE, podes usá-los para gerir a tua dependência em SpongeAPI ou em outros plugins e possibilitares a outras pessoas
uma forma mais fácil de construir o teu plugin a partir da fonte.

.. nota::
    SpongeAPI **não** necessita de usar um sistema de construção para criar plugins, sendo que o uso de um deles é por nós fortemente
    recomendado.Tirando a curta eplicação em `Criando um plugin sem um sistema de construção`_, as seguintes partes, irão assumir que tu
    estás a usal um sistema de construção, que poderá gerir as dependências por ti.

Geralmente, podes usar qualquer tipo de sistema de construção que suporte *Dependências Maven *, que normalmente é um padrão suportado
pela maioria dos sistemas de construção para projetos Java. As  seguintes irão se  focar em Gradle_ e Maven_, que são os dois sistemas
de construção mais comuns. Se  não tens a certeza sobre qual usar, recomendamos o uso do Gradle_, pois também é usado
para os projetos Sponge e fornece também a melhor integração para plugins do Sponge..

.. _gradle-configuração:

Gradle
======
Gradle_ usa scripts baseados em Groovy_ para a configuração de projetos. Um projeto de Gradle_ normalmente consiste num arquivo
``build.gradle`` que se encontra localizado no diretório raiz do teu projeto, which que informa o Gradle_  de como construir o projeto.

.. dica::
    Consulta o "Guia do Utilizador Gradle" para o instalares e para verificares a introdução geral dos conceitos usados no Gradle_. Se
    só estiveres interessado em usar o Gradle_ para um simples projeto Java, então o `Quickstart de Gradle Java ' será um bom local para
    começares.

Configura o teu espaço de trabalho como explicado em :doc:`espaço de trabalho/índice` depois segue :doc:`project/gradle`.

Maven
=====
Maven_ usa uma configuração baseada em XML chamada `Modelo de Objeto de Projeto`_ (ou *POM*) para configurar projetos. Um projeto Maven_ contém
geralmente um arquivo ``pom.xml`` no diretório raiz do projeto que diz a Maven_ de como construir o projeto.

.. dica::
    Consulta o `Centro de Utilizadores Maven`_ para o instalares e para verificares a introdução geral dos conceitos usados no Maven_.
    Se só estiveres interessado em usar o Maven_ para um simples projeto Java, então o`Maven em 5 Minutos`_ será um bom local para
    começares.

Configura o teu espaço de trabalho como explicado em :doc:`espaço de trabalho/índice` depois segue :doc:`project/maven`.


Criar um plugin sem o uso de um sistema de construção
=====================================================

Também é possível criares plugins de Sponge sem o uso de um sistema de construção, usando apenas as ferramentas incluídas no teu
IDE.

.. atenção::
    **Não recomendamos o uso de SpongeAPI sem um sistema de construção. ** A longo prazo, o uso de um sistema de construção irá
    simplificar o processo de desenvolvimento para ti e para as outras pessoas que desejem contribuir com o seu projeto. Este método de
    desenvolvimento de plugins não recebe testes ativos pela equipa do Sponge.

Para desenvolver plugins sem um sistema de compilação, precisas de descarregar manualmente a dependência SpongeAPI da 
`Página de Download SpongeAPI`_. Para o desenvolveres sem um sistema de construção, nós fornecemos-te o artefato ``sombreado`` que
agrupa todas as dependências que normalmente seriam descarregadas automaticamente pelo sistema de construção.

Depois de teres descarregado o artefato ``sombreado`` e de o adicionar a um projeto no  teu IDE, podes começar a desenvolver o teu
plugin. Continua em :doc:`identificador de plugin` para escolheres um identificador para o seu projeto, depois continua em :doc:`plugin
-classe`.

.. _Gradle: https://gradle.org/
.. _Maven: https://maven.apache.org/
.. _Groovy: http://www.groovy-lang.org/
.. _`Gradle User Guide`: https://docs.gradle.org/current/userguide/userguide.html
.. _`Gradle Java Quickstart`: https://docs.gradle.org/current/userguide/tutorial_java_projects.html
.. _`Project Object Model`: https://maven.apache.org/guides/introduction/introduction-to-the-pom.html
.. _`Maven Users Centre`: https://maven.apache.org/users/index.html
.. _`Maven in 5 Minutes`: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
.. _`Maven Getting Started Guide`: https://maven.apache.org/guides/getting-started/index.html
.. _`SpongeAPI Download Page`: https://www.spongepowered.org/downloads/spongeapi/
