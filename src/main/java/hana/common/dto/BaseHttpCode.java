package hana.common.dto;

import hana.common.annotation.TypeInfo;

@TypeInfo(name = "BaseHttpCode", description = "HttpCode 인터페이스")
public interface BaseHttpCode {
    BaseHttpReason getHttpReason();
}
