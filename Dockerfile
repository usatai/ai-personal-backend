# --- 1. ビルド用ステージ ---
    FROM eclipse-temurin:21-jdk-jammy AS build

    WORKDIR /workspace
    
    # 依存解決用キャッシュのため、gradle関連を先にコピー
    COPY gradlew build.gradle settings.gradle ./
    COPY gradle/ gradle/
    
    # 先に依存関係だけ解決（キャッシュ効かせる用）
    RUN ./gradlew dependencies --no-daemon
    
    # アプリケーション全体をコピーしてビルド
    COPY . .
    
    # bootJar を実行して jar 作成
    RUN ./gradlew bootJar -x test --no-daemon
    
    # --- 2. 実行用ステージ ---
    FROM eclipse-temurin:21-jre-jammy
    
    WORKDIR /app
    
    # build で生成された jar をコピー
    COPY --from=build /workspace/build/libs/*.jar app.jar
    
    ENTRYPOINT ["java", "-jar", "/app/app.jar"]
    