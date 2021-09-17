package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Mail {

    public final String mailTo;
    public final String subject;
    public final String message;
    public final String toCc;
}
