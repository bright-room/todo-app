# Frontend Dockerfile for Bright Room Todo Web App
FROM openjdk:21 as builder

# Node.js のインストール (Compose Multiplatform Web のビルドに必要)
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_22.x | bash - && \
    apt-get install -y nodejs

# 作業ディレクトリの設定
WORKDIR /app

# Gradle Wrapper とビルドファイルをコピー
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle/libs.versions.toml ./gradle/

# 共有モジュールをコピー
COPY shared ./shared

# フロントエンドソースをコピー
COPY frontend ./frontend

# 実行権限を付与
RUN chmod +x ./gradlew

# フロントエンドアプリケーションをビルド
RUN ./gradlew :frontend:app:wasmJsBrowserDistribution

# Nginx を使用してアプリケーションを配信
FROM nginx:alpine

# ビルドされたファイルをnginxの公開ディレクトリにコピー
COPY --from=builder /app/frontend/app/build/dist/ /usr/share/nginx/html/

# カスタムnginx設定をコピー
COPY docker/frontend/nginx.conf /etc/nginx/nginx.conf

# ポート80を公開
EXPOSE 80

# nginxを起動
CMD ["nginx", "-g", "daemon off;"]