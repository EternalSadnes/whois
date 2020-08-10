package com.introlabsystems.whois.model.assessment;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment_domain")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class AssessmentDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @NotEmpty
    @Column(name = "domain", unique = true)
    private String domain;

    @Column(name = "filled_by_operator")
    private boolean filledByOperator;

    @Column(name = "priority")
    private boolean priority;

    @Column(name = "priority_language")
    private String priorityLanguage;

    @Column(name = "other_language")
    private String otherLanguage;

    @Column(name = "no_language_identified")
    private Boolean noLanguageIdentified;

    @Column(name = "multi_language")
    private Boolean multiLanguage;

    @Column(name = "priority_country")
    private String priorityCountry;

    @Column(name = "other_countries")
    private String otherCountries;

    @Column(name = "no_country_identified")
    private Boolean noCountryIdentified;

    @Column(name = "multi_national")
    private Boolean multiNational;

    @Column(name = "commercial_web_site")
    private Boolean commercialWebSite;

    @Column(name = "unsure_type")
    private Boolean unsureType;

    @Column(name = "blacklisted")
    private boolean blacklisted;

    @Column(name = "country_of_contact")
    private String countryOfContact;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "contact_form_link")
    private String contactFormLink;

    @Column(name = "no_details_found")
    private Boolean noDetailsFound;

    @Column(name = "facebook_link")
    private String facebookLink;

    @Column(name = "twitter_link")
    private String twitterLink;

    @Column(name = "instagram_link")
    private String instagramLink;

    @Column(name = "linkedin_link")
    private String linkedinLink;

    @Column(name = "comment")
    private String comment;
}
