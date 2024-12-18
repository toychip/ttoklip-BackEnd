package com.domain.member.application.response;

public record MemberStreetResponse(
        String street
) {
    public static MemberStreetResponse of(String street) {
        String trimmedStreet = street.trim();

        return new MemberStreetResponse(trimmedStreet);
    }
}
