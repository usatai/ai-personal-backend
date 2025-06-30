# --- 1. ビルド用ステージ ---
    FROM eclipse-temurin:21-jdk-jammy AS build

    WORKDIR /workspace
    
    # 1. Gradle Wrapper とプロジェクト設定
    COPY build.gradle settings.gradle gradlew ./
    COPY gradle gradle
    
    # 2. 依存解決
    RUN ./gradlew build -x test
    
    # 3. アプリケーションのソースコード
    COPY src src
    
    # 4. ビルド（再度、依存も含めて完全に）
    RUN ./gradlew bootJar -x test

# --- 2. 実行用ステージ ---
    FROM eclipse-temurin:21-jre-jammy

    WORKDIR /app

    COPY --from=build /workspace/build/libs/*.jar app.jar

    ENTRYPOINT ["java", "-jar", "/app/app.jar"]
    