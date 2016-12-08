package ru.roox.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PARTNER_MAPPING")
public class PartnerMapping {

    private static final String IMG_URL_REGEXP = "((?:https?\\:\\/\\/)(?:[a-zA-Z]{1}(?:[\\w\\-]+\\.)+(?:[\\w]{2,5}))(?:\\:[\\d]{1,5})?\\/(?:[^\\s\\/]+\\/)*(?:[^\\s]+\\.(?:jpe?g|gif|png))(?:\\?\\w+=\\w+(?:&\\w+=\\w+)*)?)";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "mapping_id")
    private long id;
    @NotNull
    @Min(1)
    @Column(name = "partner_id")
    private long partnerId;
    @NotNull
    @Min(1)
    @Column(name = "customer_account_id")
    private long customerAccountId;
    @NotNull
    @Column(name = "customer_last_name")
    private String customerLastName;
    @NotNull
    @Column(name = "customer_first_name")
    private String customerFirstName;
    @NotNull
    @Column(name = "customer_middle_name")
    private String customerMiddleName;
    @NotNull
    @Pattern(regexp = IMG_URL_REGEXP)
    @Column(name = "customer_avatar_url")
    private String customerAvatarUrl;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public PartnerMapping() {
    }

    public PartnerMapping(long partnerId, long customerAccountId, String customerLastName, String customerFirstName, String customerMiddleName, String customerAvatarUrl) {
        this.partnerId = partnerId;
        this.customerAccountId = customerAccountId;
        this.customerLastName = customerLastName;
        this.customerFirstName = customerFirstName;
        this.customerMiddleName = customerMiddleName;
        this.customerAvatarUrl = customerAvatarUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public long getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(long customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerMiddleName() {
        return customerMiddleName;
    }

    public void setCustomerMiddleName(String customerMiddleName) {
        this.customerMiddleName = customerMiddleName;
    }

    public String getCustomerAvatarUrl() {
        return customerAvatarUrl;
    }

    public void setCustomerAvatarUrl(String customerAvatarUrl) {
        this.customerAvatarUrl = customerAvatarUrl;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
