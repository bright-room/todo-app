FROM openjdk:21-jdk-slim-buster as builder

# 作業ディレクトリの設定
WORKDIR /app

# Gradle Wrapper とビルドファイルをコピー
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/libs.versions.toml ./gradle/

# 共有モジュールをコピー
COPY shared ./shared

# バックエンドソースをコピー
COPY backend ./backend

# 実行権限を付与
RUN chmod +x ./gradlew

# アプリケーションをビルド
RUN ./gradlew :backend:api:build

# 実行用イメージ
FROM openjdk:21-jdk-slim-buster

# 作業ディレクトリの設定
WORKDIR /app

# ビルドされたJARファイルをコピー
COPY --from=builder /app/backend/api/build/libs/api-all.jar app.jar

# ポート8080を公開
EXPOSE 8080

# ヘルスチェック
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/health || exit 1

# アプリケーションを実行
CMD ["java", "-jar", "app.jar"]
