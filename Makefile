COMPOSE=docker compose

up:
	$(COMPOSE) up -d

down:
	$(COMPOSE) down -v
