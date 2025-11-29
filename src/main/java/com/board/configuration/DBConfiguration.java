package com.board.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration // spring 설정을 담당하는 클레스라는 표시. 이 클래스 안의 @Bean 붙은 메서드들이 Spring이 관리하는 Bean으로 자동등록.
@PropertySource("classpath:/application.properties") // 해당 클래스에서 참조할 properties 파일 위치 지정
public class DBConfiguration {

    @Autowired // ApplicationContext == 'IoC컨테이너의 권한'을 이 클레스에 적용.(spring에서 가장 높은 권한)
    private ApplicationContext applicationContext; // 권한: Bean검색 및 조작, 환경, 리소스 접근(Properties, xml)

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    // 외부 설정 자동 매핑: 메서드에서 반환하는 객체에 해당 주소에 있는 값들을 자동으로 채워 넣는다.
    public HikariConfig hikariConfig() { // 1. HikariConfig() 객체 생성
        return new HikariConfig();         // 2. .Properties파일의 "spring.datasource.hikari"로 시작하는 모든 데이터를 HikariConfig객체에 담아서 반환.
    }

    @Bean
    public DataSource dataSource() { // DB 연결을 실제로 관리하는 핵심 객체(데이터소스)를 스프링에게 제공하는 역할
        return new HikariDataSource(hikariConfig()); // 3. HikariConfig()객체의 데이터를 받아서 HikariDataSource()객체 생성 후 spring에게 Bean으로 등록.
    }

    @Bean // Mybatis 객체 sqlSessionFactory 생성 메서드
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean(); // SqlSessionFactoryBean == MyBatis 설정용 spring클래스 = 컨테이너,빌더 역할
        factoryBean.setDataSource(dataSource()); // 4. factoryBean객체에 dataSource()='DB연결정보' 를 할당.
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*Mapper.xml")); // 5. factoryBean 객체에게 /mappers 폴더 아래의 모든 하위폴더**/ 안에 있는 *Mapper.xml로 끝나는 모든 xml파일을 Mapper로 등록.
        factoryBean.setTypeAliasesPackage("com.board.*"); // 6. XML Mapper에서 Type에 객체 전체 이름을 적는 대신, 주소안의 내용은 생략할 수 있게 설정.
        factoryBean.setConfiguration(mybatisConfig()); // 7. factoryBean에 Mybatis의 작동 방식을 설정한 mybatisConfig() 객체 주입.
        return factoryBean.getObject(); // 8. factoryBean의 모든 설정 정보를 사옹해서 MyBatis가 실제로 사용할 수 있는 최종 결과물인 SqlSessionFactory 객체를 새로 만들어서 반환
    }

    @Bean // 7번에 주입되는 mybatis 작동 방식 설정객체
    @ConfigurationProperties(prefix = "mybatis.configuration") // 외부 설정 자동 매핑: 메서드에서 반환하는 객체에 해당 주소에 있는 값들을 자동으로 채워 넣는다.
    public org.apache.ibatis.session.Configuration mybatisConfig() { // == 기본 mybatis 작동 방식 설정객체
        return new org.apache.ibatis.session.Configuration(); // == 객체를 반환해서 Properties의 MyBatis관련 설정을 적용한다.
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory()); // 9. sqlSessionFactory객체의 정보를 받아서 실제 SQL쿼리를 전달.
    }
}
