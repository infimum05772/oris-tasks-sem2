import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("war")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass = "${properties["mainAppClass"]}"
}

group = "ru.kpfu.itis.arifulina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webmvc:${properties["springVersion"]}")
    implementation("org.springframework:spring-jdbc:${properties["springVersion"]}")
    implementation("org.springframework:spring-orm:${properties["springVersion"]}")
    implementation("org.springframework:spring-context-support:${properties["springVersion"]}")

    implementation("org.springframework.security:spring-security-core:${properties["springSecurityVersion"]}")
    implementation("org.springframework.security:spring-security-web:${properties["springSecurityVersion"]}")
    implementation("org.springframework.security:spring-security-config:${properties["springSecurityVersion"]}")
    implementation("org.springframework.security:spring-security-taglibs:${properties["springSecurityVersion"]}")

    implementation("org.springframework.data:spring-data-jpa:${properties["springDataJpaVersion"]}")

    implementation("org.hibernate:hibernate-core:${properties["hibernateVersion"]}")
    implementation("org.hibernate:hibernate-entitymanager:${properties["hibernateVersion"]}")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:${properties["hibernateVersion"]}")
//    implementation("org.hibernate:hibernate-validator:${properties["hibernateVersion"]}")

    implementation("org.postgresql:postgresql:${properties["postgresqlVersion"]}")

    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:${properties["tomcatVersion"]}")
    implementation("com.google.code.gson:gson:${properties["gsonVersion"]}")
    implementation("com.mchange:c3p0:${properties["c3p0Version"]}")

    annotationProcessor("org.projectlombok:lombok:${properties["lombokVersion"]}")
    compileOnly("org.projectlombok:lombok:${properties["lombokVersion"]}")

    implementation("org.freemarker:freemarker:${properties["freemarkerVersion"]}")

//    testImplementation(platform("org.junit:junit-bom:5.9.1"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("tasks_sem2.jar")
    mergeServiceFiles()
}

//tasks.test {
//    useJUnitPlatform()
//}