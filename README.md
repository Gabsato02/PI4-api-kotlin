# PI4_2021-api
Repositório para os serviços REST do Projeto Integrador do 4° semestre de TSI pelo SENAC-SP.


Criando projeto:
-> File -> New Project -> Project template (REST service para APIs) -> Application server (Tomcat 9.0.52) -> Language (Kotlin) -> Group (br.grupo)

Conectando o banco de dados:
-> Database -> New -> Data Source -> MySQL -> User & Password

Removendo o explode da URL:
-> Tomcat -> Edit Configurations -> Deployment -> Application context

Criação de classe:
-> Botão direito no pacote do projeto -> New -> Kotlin Class/File

Conexão de banco:
-> Criar classe de conexão
-> Criar objeto DAO (Data Access Object) (-> New -> Kotlin Class/File -> Object)
-> Criar classe de dados (Model) (-> New -> Kotlin Class/File -> Data Class)
-> Criar classe de serviço

Atualizar o driver:
-> pom.xml
Colar:
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.26</version>
        </dependency>
-> Botão direito no projeto -> Maven -> Reload project
