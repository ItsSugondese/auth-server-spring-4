package com.lazy.authserver.entity.mail;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "mail_properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailPropertiesModel {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_properties_gen")
//    @SequenceGenerator(name = "mail_properties_gen", sequenceName = "mail_properties_seq", initialValue = 1, allocationSize = 1)
    private Long companyId;
    private int port;
    private String host;
    private String username;
    private String password;
    private String uri;
//    private Long id;
}
