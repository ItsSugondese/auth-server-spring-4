package com.lazy.authserver.utils.mail;

import com.lazy.authserver.pojo.AttachmentDetailsPojo;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Santosh Paudel
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSenderRequest {
    private String subject;
    private List<String> toEmail;
    private List<String> ccEmail;
    private String content;
    private Map<String, Object> model;
    private List<AttachmentDetailsPojo> attachmentsLocation;
    private List<String> attachmentsUrl;
    private String templateName;
}
