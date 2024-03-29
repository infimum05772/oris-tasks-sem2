plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "2.7.17"

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

    implementation("org.springframework.security:spring-security-taglibs:${properties["springSecurityVersion"]}")

    annotationProcessor("org.hibernate:hibernate-jpamodelgen:${properties["hibernateVersion"]}")

    implementation("org.postgresql:postgresql:${properties["postgresqlVersion"]}")
    implementation("com.google.code.gson:gson:${properties["gsonVersion"]}")

    annotationProcessor("org.projectlombok:lombok:${properties["lombokVersion"]}")
    compileOnly("org.projectlombok:lombok:${properties["lombokVersion"]}")

    implementation("org.apache.tomcat:tomcat-jsp-api:${properties["tomcatJspApiVersion"]}")
    implementation("javax.servlet.jsp:jsp-api:${properties["jspApiVersion"]}")

//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}

//tasks.test {
//    useJUnitPlatform()
//}