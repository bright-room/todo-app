package net.brightroom.todo.domain.policy.serializer

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTime
import net.brightroom.todo.domain.model.lifecycle.complete.CompletedTimeFactory

object CompletedTimeSerializer : KSerializer<CompletedTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.brightroom.todo.domain.model.lifecycle.complete.CompletedTime", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(
        encoder: Encoder,
        value: CompletedTime,
    ) {
        if (value.is完了日時が設定されている()) {
            encoder.encodeString(value().toString())
        } else {
            encoder.encodeNull()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): CompletedTime {
        val str =
            decoder
                .decodeNullableSerializableValue(String.serializer())
                ?: return CompletedTimeFactory.create(null)

        if (str.isBlank()) return CompletedTimeFactory.create(null)

        return CompletedTimeFactory.create(LocalDateTime.parse(str))
    }
}
