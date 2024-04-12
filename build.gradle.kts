import java.util.Properties

plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "2.7.17"
    id("org.liquibase.gradle") version "2.2.0"

}

apply(plugin = "io.spring.dependency-management")

application {
    mainClass = "${properties["mainAppClass"]}"
}

group = "ru.kpfu.itis.arifulina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springframework.security:spring-security-taglibs:${properties["springSecurityVersion"]}")

    annotationProcessor("org.hibernate:hibernate-jpamodelgen:${properties["hibernateVersion"]}")

    implementation("org.postgresql:postgresql:${properties["postgresqlVersion"]}")
    implementation("org.liquibase:liquibase-core:4.20.0")
    liquibaseRuntime("org.liquibase:liquibase-core:4.20.0")
    liquibaseRuntime("org.postgresql:postgresql:${properties["postgresqlVersion"]}")
    liquibaseRuntime("info.picocli:picocli:4.6.3")

    implementation("com.google.code.gson:gson:${properties["gsonVersion"]}")

    annotationProcessor("org.projectlombok:lombok:${properties["lombokVersion"]}")
    compileOnly("org.projectlombok:lombok:${properties["lombokVersion"]}")

    implementation("org.apache.tomcat:tomcat-jsp-api:${properties["tomcatJspApiVersion"]}")
    implementation("javax.servlet.jsp:jsp-api:${properties["jspApiVersion"]}")

    implementation("javax.mail:javax.mail-api:${properties["javaxMailVersion"]}")

//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}

//tasks.test {
//    useJUnitPlatform()
//}

var props = Properties()
props.load(file("src/main/resources/liquibase.properties").inputStream())

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changeLogFile" to props.get("change-log-file"),
            "url" to props.get("url"),
            "username" to props.get("username"),
            "password" to props.get("password"),
            "driver" to props.get("driver-class-name")
        )
    }
}
