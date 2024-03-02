package com.example.tasklist3.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class LocalDateTimeCoercing implements Coercing<LocalDateTime, String> {

    @Override
    public @Nullable String serialize(
            final @NotNull Object dataFetcherResult,
            final @NotNull GraphQLContext graphQLContext,
            final @NotNull Locale locale) throws CoercingSerializeException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSXXX", Locale.ENGLISH);
        return formatter.format(
                Date.from(
                        ((LocalDateTime) dataFetcherResult)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                )
        );
    }

    @Override
    public @Nullable LocalDateTime parseValue(
            final @NotNull Object input,
            final @NotNull GraphQLContext graphQLContext,
            final @NotNull Locale locale) throws CoercingParseValueException {
        return LocalDateTime.parse((String) input);
    }

    @Override
    public @Nullable LocalDateTime parseLiteral(
            final @NotNull Value<?> input,
            final @NotNull CoercedVariables variables,
            final @NotNull GraphQLContext graphQLContext,
            final @NotNull Locale locale) throws CoercingParseLiteralException {
        return LocalDateTime.parse(((StringValue) input).getValue());
    }

}
