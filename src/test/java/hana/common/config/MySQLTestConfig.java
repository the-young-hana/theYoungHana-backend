package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TypeInfo(name = "MySQLTestConfig", description = "MySQL 테스트 설정 클래스")
@Configuration
@Testcontainers
public class MySQLTestConfig implements BeforeAllCallback {
    @Container
    private static final GenericContainer<?> mySQLContainer =
            new GenericContainer<>(DockerImageName.parse("mysql:8.0.35"))
                    .withEnv("MYSQL_DATABASE", "hana")
                    .withEnv("MYSQL_ROOT_USER", "user")
                    .withEnv("MYSQL_ROOT_PASSWORD", "password")
                    .withEnv("MYSQL_USER", "user")
                    .withEnv("MYSQL_PASSWORD", "password")
                    .withExposedPorts(3306);

    @MethodInfo(name = "beforeAll", description = "테스트 실행 전 MySQL 컨테이너를 실행합니다.")
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!mySQLContainer.isRunning()) {
            mySQLContainer.start();
        }

        configureDataSourceProperties();
        createTestTable();
    }

    @MethodInfo(name = "configureDataSourceProperties", description = "데이터 소스를 설정합니다.")
    private void configureDataSourceProperties() {
        System.setProperty(
                "spring.datasource.url",
                "jdbc:mysql://"
                        + mySQLContainer.getHost()
                        + ":"
                        + mySQLContainer.getMappedPort(3306)
                        + "/hana");
        System.setProperty("spring.datasource.username", "user");
        System.setProperty("spring.datasource.password", "password");
    }

    @MethodInfo(name = "createTestTable", description = "테스트 테이블을 생성합니다.")
    private void createTestTable() {
        List<String> sqlStatements = readSqlFile("schema.sql");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(buildDataSource());
        for (String sqlStatement : sqlStatements) {
            jdbcTemplate.execute(sqlStatement);
        }
    }

    @MethodInfo(name = "buildDataSource", description = "데이터 소스를 생성합니다.")
    private DataSource buildDataSource() {
        org.springframework.jdbc.datasource.DriverManagerDataSource dataSource =
                new org.springframework.jdbc.datasource.DriverManagerDataSource();
        dataSource.setUrl(System.getProperty("spring.datasource.url"));
        dataSource.setUsername(System.getProperty("spring.datasource.username"));
        dataSource.setPassword(System.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @MethodInfo(name = "readSqlFile", description = "SQL 파일을 읽어 이를 여러 개의 SQL 문으로 분리합니다.")
    private List<String> readSqlFile(String sqlFileName) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(sqlFileName);
            InputStream inputStream = classPathResource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> sqlStatements = new ArrayList<>();
            StringBuilder currentStatement = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().endsWith(";")) {
                    currentStatement.append(line).append("\n");
                    sqlStatements.add(currentStatement.toString().trim());
                    currentStatement.setLength(0);
                } else {
                    currentStatement.append(line).append("\n");
                }
            }

            return sqlStatements;
        } catch (IOException ioException) {
            throw new RuntimeException("SQL 파일을 가져오는 데에 실패하였습니다. : " + sqlFileName, ioException);
        }
    }
}
