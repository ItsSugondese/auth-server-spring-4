package com.lazy.authserver.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttachmentDetailsPojo {
    private String attachmentUrl;
    private String attachmentOriginalName;
}
