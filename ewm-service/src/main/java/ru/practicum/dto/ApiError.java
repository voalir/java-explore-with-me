package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сведения об ошибке
 */

public final class ApiError {

    private final List<String> errors;
    private final String message;
    private final String reason;
    private final Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private final LocalDateTime timestamp;

    public ApiError(List<String> errors, String message, String reason, Status status, LocalDateTime timestamp) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Код статуса HTTP-ответа
     */
    public enum Status {
        STATUS_100_CONTINUE("100 CONTINUE"),

        STATUS_101_SWITCHING_PROTOCOLS("101 SWITCHING_PROTOCOLS"),

        STATUS_102_PROCESSING("102 PROCESSING"),

        STATUS_103_CHECKPOINT("103 CHECKPOINT"),

        STATUS_200_OK("200 OK"),

        STATUS_201_CREATED("201 CREATED"),

        STATUS_202_ACCEPTED("202 ACCEPTED"),

        STATUS_203_NON_AUTHORITATIVE_INFORMATION("203 NON_AUTHORITATIVE_INFORMATION"),

        STATUS_204_NO_CONTENT("204 NO_CONTENT"),

        STATUS_205_RESET_CONTENT("205 RESET_CONTENT"),

        STATUS_206_PARTIAL_CONTENT("206 PARTIAL_CONTENT"),

        STATUS_207_MULTI_STATUS("207 MULTI_STATUS"),

        STATUS_208_ALREADY_REPORTED("208 ALREADY_REPORTED"),

        STATUS_226_IM_USED("226 IM_USED"),

        STATUS_300_MULTIPLE_CHOICES("300 MULTIPLE_CHOICES"),

        STATUS_301_MOVED_PERMANENTLY("301 MOVED_PERMANENTLY"),

        STATUS_302_FOUND("302 FOUND"),

        STATUS_302_MOVED_TEMPORARILY("302 MOVED_TEMPORARILY"),

        STATUS_303_SEE_OTHER("303 SEE_OTHER"),

        STATUS_304_NOT_MODIFIED("304 NOT_MODIFIED"),

        STATUS_305_USE_PROXY("305 USE_PROXY"),

        STATUS_307_TEMPORARY_REDIRECT("307 TEMPORARY_REDIRECT"),

        STATUS_308_PERMANENT_REDIRECT("308 PERMANENT_REDIRECT"),

        STATUS_400_BAD_REQUEST("400 BAD_REQUEST"),

        STATUS_401_UNAUTHORIZED("401 UNAUTHORIZED"),

        STATUS_402_PAYMENT_REQUIRED("402 PAYMENT_REQUIRED"),

        STATUS_403_FORBIDDEN("403 FORBIDDEN"),

        STATUS_404_NOT_FOUND("404 NOT_FOUND"),

        STATUS_405_METHOD_NOT_ALLOWED("405 METHOD_NOT_ALLOWED"),

        STATUS_406_NOT_ACCEPTABLE("406 NOT_ACCEPTABLE"),

        STATUS_407_PROXY_AUTHENTICATION_REQUIRED("407 PROXY_AUTHENTICATION_REQUIRED"),

        STATUS_408_REQUEST_TIMEOUT("408 REQUEST_TIMEOUT"),

        STATUS_409_CONFLICT("409 CONFLICT"),

        STATUS_410_GONE("410 GONE"),

        STATUS_411_LENGTH_REQUIRED("411 LENGTH_REQUIRED"),

        STATUS_412_PRECONDITION_FAILED("412 PRECONDITION_FAILED"),

        STATUS_413_PAYLOAD_TOO_LARGE("413 PAYLOAD_TOO_LARGE"),

        STATUS_413_REQUEST_ENTITY_TOO_LARGE("413 REQUEST_ENTITY_TOO_LARGE"),

        STATUS_414_URI_TOO_LONG("414 URI_TOO_LONG"),

        STATUS_414_REQUEST_URI_TOO_LONG("414 REQUEST_URI_TOO_LONG"),

        STATUS_415_UNSUPPORTED_MEDIA_TYPE("415 UNSUPPORTED_MEDIA_TYPE"),

        STATUS_416_REQUESTED_RANGE_NOT_SATISFIABLE("416 REQUESTED_RANGE_NOT_SATISFIABLE"),

        STATUS_417_EXPECTATION_FAILED("417 EXPECTATION_FAILED"),

        STATUS_418_I_AM_A_TEAPOT("418 I_AM_A_TEAPOT"),

        STATUS_419_INSUFFICIENT_SPACE_ON_RESOURCE("419 INSUFFICIENT_SPACE_ON_RESOURCE"),

        STATUS_420_METHOD_FAILURE("420 METHOD_FAILURE"),

        STATUS_421_DESTINATION_LOCKED("421 DESTINATION_LOCKED"),

        STATUS_422_UNPROCESSABLE_ENTITY("422 UNPROCESSABLE_ENTITY"),

        STATUS_423_LOCKED("423 LOCKED"),

        STATUS_424_FAILED_DEPENDENCY("424 FAILED_DEPENDENCY"),

        STATUS_425_TOO_EARLY("425 TOO_EARLY"),

        STATUS_426_UPGRADE_REQUIRED("426 UPGRADE_REQUIRED"),

        STATUS_428_PRECONDITION_REQUIRED("428 PRECONDITION_REQUIRED"),

        STATUS_429_TOO_MANY_REQUESTS("429 TOO_MANY_REQUESTS"),

        STATUS_431_REQUEST_HEADER_FIELDS_TOO_LARGE("431 REQUEST_HEADER_FIELDS_TOO_LARGE"),

        STATUS_451_UNAVAILABLE_FOR_LEGAL_REASONS("451 UNAVAILABLE_FOR_LEGAL_REASONS"),

        STATUS_500_INTERNAL_SERVER_ERROR("500 INTERNAL_SERVER_ERROR"),

        STATUS_501_NOT_IMPLEMENTED("501 NOT_IMPLEMENTED"),

        STATUS_502_BAD_GATEWAY("502 BAD_GATEWAY"),

        STATUS_503_SERVICE_UNAVAILABLE("503 SERVICE_UNAVAILABLE"),

        STATUS_504_GATEWAY_TIMEOUT("504 GATEWAY_TIMEOUT"),

        STATUS_505_HTTP_VERSION_NOT_SUPPORTED("505 HTTP_VERSION_NOT_SUPPORTED"),

        STATUS_506_VARIANT_ALSO_NEGOTIATES("506 VARIANT_ALSO_NEGOTIATES"),

        STATUS_507_INSUFFICIENT_STORAGE("507 INSUFFICIENT_STORAGE"),

        STATUS_508_LOOP_DETECTED("508 LOOP_DETECTED"),

        STATUS_509_BANDWIDTH_LIMIT_EXCEEDED("509 BANDWIDTH_LIMIT_EXCEEDED"),

        STATUS_510_NOT_EXTENDED("510 NOT_EXTENDED"),

        STATUS_511_NETWORK_AUTHENTICATION_REQUIRED("511 NETWORK_AUTHENTICATION_REQUIRED");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @JsonCreator
        public static Status fromValue(String text) {
            for (Status b : Status.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }
}
