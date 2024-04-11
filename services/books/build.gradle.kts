plugins {
    id("java")
}

group = "com.parmeet.springboot"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.9.0")
}

tasks.test {
    useJUnitPlatform()
}