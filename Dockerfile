# --- 1. ビルド用ステージ ---
    FROM eclipse-temurin:21-jdk-jammy AS build

    WORKDIR /workspace
    
    # 1. Gradle Wrapper とプロジェクト設定
    COPY build.gradle settings.gradle gradlew ./
    COPY gradle gradle
    
    # 2. 依存解決
    RUN ./gradlew build -x test || true
    
    # 3. アプリケーションのソースコード
    COPY src src
    
    # 4. ビルド（再度、依存も含めて完全に）
    RUN ./gradlew bootJar -x test
    