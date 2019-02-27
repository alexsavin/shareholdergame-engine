package com.shareholdergame.engine.account.config;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.Positive;

@ConfigurationProperties("shareholdergame.engine.account")
public class AccountServiceConfiguration {

    @Positive
    private int verificationCodeLength = 24;

    @Positive
    private int verificationExpirationDays = 3;

    public int getVerificationCodeLength() {
        return verificationCodeLength;
    }

    public int getVerificationExpirationDays() {
        return verificationExpirationDays;
    }
}
