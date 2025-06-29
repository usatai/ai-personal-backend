# Dockerfile

# --- 1. ビルド用ステージ ---
# Java 21のJDKが含まれたイメージをベースにする
FROM eclipse-temurin:21-jdk-jammy AS build

# 作業ディレクトリを設定
WORKDIR /workspace

# Gradle Wrapperのファイルとbuild.gradleをコピー
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# 依存関係をダウンロード（ソースコードをコピーする前に行うことで、キャッシュが効きやすくなる）
RUN ./gradlew dependencies

# アプリケーションのソースコードをコピー
COPY src src

# Gradleを使ってアプリケーションをビルド（テストはスキップ）
RUN ./gradlew build -x test

# --- 2. 実行用ステージ ---
# より軽量なJRE（Java実行環境）のみのイメージをベースにする
FROM eclipse-temurin:21-jre-jammy

# 作業ディレクトリを設定
WORKDIR /app

# ビルド用ステージから、ビルドされたJARファイルのみをコピー
COPY --from=build /workspace/build/libs/*.jar app.jar

# コンテナが起動したときに実行するコマンド
ENTRYPOINT ["java","-jar","/app/app.jar"]