package com.shareholdergame.engine.facade.controller;

import java.security.Principal;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;

import com.shareholdergame.engine.account.model.AccountWithPassword;
import com.shareholdergame.engine.common.support.ResponseWrapper;
import com.shareholdergame.engine.facade.client.AccountClient;
import com.shareholdergame.engine.facade.converter.Converters;
import com.shareholdergame.engine.facade.dto.AccountDetails;
import com.shareholdergame.engine.facade.dto.SignUp;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller("/account")
@Validated
@Secured(SecurityRule.IS_AUTHENTICATED)
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Account")
public class AccountController {

    @Inject
    private AccountClient accountClient;

    /**
     * Check user existence.
     * @param userNameOrEmail user name or email
     * @return true if user exists or false if no.
     */
    @Get("/exist/{userNameOrEmail}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public ResponseWrapper<Boolean> checkUserExistence(@NotBlank String userNameOrEmail) {
        boolean existence = accountClient.checkUserExistence(userNameOrEmail);
        return ResponseWrapper.ok(existence);
    }

    /**
     * Sign user up.
     * @param signUp contains user name, email and password
     * @return empty response if ok.
     */
    @Put("/signup")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public ResponseWrapper<?> signup(@Body SignUp signUp) {
        return ResponseWrapper.ok();
    }

    /**
     * Verify user account.
     * @param verificationCode verification code.
     * @return empty response if ok.
     */
    @Get("/verify/{verificationCode}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public ResponseWrapper<?> verify(@NotBlank String verificationCode) {
        return ResponseWrapper.ok();
    }

    /**
     * Resets user password.
     * @param email user email.
     * @return empty response if ok.
     */
    @Post(value = "/resetpassword", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    @Secured(SecurityRule.IS_ANONYMOUS)
    public ResponseWrapper<?> resetPassword(@QueryValue String email) {
        return ResponseWrapper.ok();
    }

    /**
     * Returns user's account.
     * User must be authenticated to invoke this call.
     * @param principal user principal.
     * @return account details.
     */
    @Get
    public ResponseWrapper<AccountDetails> getAccount(Principal principal) {
        AccountWithPassword accountWithPassword = accountClient.findUserByNameOrEmail(principal.getName());
        if (null == accountWithPassword) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, principal.getName());
        }

        return ResponseWrapper.ok(Converters.convert(accountWithPassword.getAccount()));
    }

    /**
     * Starts account removing process.
     * User must be authenticated to invoke this call.
     * @param principal user principal.
     * @return empty response if ok.
     */
    @Delete("/remove")
    public ResponseWrapper<?> removeAccount(Principal principal) {
        return ResponseWrapper.ok();
    }

    /**
     * Starts account restoring process.
     * User must be authenticated to invoke this call.
     * @param principal user principal.
     * @return empty response if ok.
     */
    @Post("/restore")
    public ResponseWrapper<?> restoreAccount(Principal principal) {
        return ResponseWrapper.ok();
    }

    /**
     * Updates user's email.
     * User must be authenticated to invoke this call.
     * @param email new email.
     * @param principal user principal.
     * @return empty response if ok.
     */
    @Post(value = "/edit/email", consumes = {MediaType.APPLICATION_FORM_URLENCODED})
    public ResponseWrapper<?> updateEmail(@QueryValue String email, Principal principal) {
        return ResponseWrapper.ok();
    }
}
