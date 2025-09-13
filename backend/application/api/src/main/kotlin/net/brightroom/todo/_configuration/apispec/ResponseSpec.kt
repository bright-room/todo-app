package net.brightroom.todo._configuration.apispec

object ResponseSpec {
    object OK {
        const val DESCRIPTION = "OK"
    }

    object Created {
        const val DESCRIPTION = "Created"
    }

    object BadRequest {
        const val DESCRIPTION = "不正なリクエスト"
    }

    object NotFound {
        const val DESCRIPTION = "指定されたリソースが存在しない"
    }

    object InternalServerError {
        const val DESCRIPTION = "サーバー起因によるエラー"
    }
}
