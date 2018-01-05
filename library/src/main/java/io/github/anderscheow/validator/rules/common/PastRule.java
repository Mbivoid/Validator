package io.github.anderscheow.validator.rules.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import io.github.anderscheow.validator.rules.BaseRule;

public class PastRule extends BaseRule {

    private DateFormat dateFormat;

    public PastRule(@Nullable DateFormat dateFormat) {
        super("Does not match past rule");
        this.dateFormat = dateFormat;
    }

    public PastRule(@Nullable DateFormat dateFormat, @StringRes int errorRes) {
        super(errorRes);
        this.dateFormat = dateFormat;
    }

    public PastRule(@Nullable DateFormat dateFormat, @NonNull String errorMessage) {
        super(errorMessage);
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean validate(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        Date parsedDate = null;

        if (value instanceof String) {
            if (dateFormat == null) {
                throw new NullPointerException("DateFormat cannot be null");
            }

            try {
                parsedDate = dateFormat.parse((String) value);
            } catch (ParseException ignored) {
            }
        } else if (value instanceof Date) {
            parsedDate = (Date) value;
        } else {
            throw new ClassCastException("Required String or Date value");
        }

        return parsedDate != null && parsedDate.before(new Date());
    }
}
