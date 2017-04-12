package ar.com.phosgenos.functional;

import java.util.function.Function;

public class Either<A, B> {

    private final A expected;
    private final B alternative;

    private Either(A a, B b) {
        super();

        expected = a;
        alternative = b;

        // Defensive assumptions
        if (expected != null && alternative != null) {
            throw new IllegalStateException("Both values cannot be null");
        } else if (expected == null && alternative == null) {
            throw new IllegalStateException("Both values cannot be set");
        }
    }

    public boolean hasExpected() {
        return expected != null;
    }

    public boolean hasAlternative() {
        return alternative != null;
    }

    public A expected() {
        return expected;
    }

    public B alternative() {
        return alternative;
    }

    public static <A, B> Either<A, B> expected(A a) {
        return new Either<A, B>(a, null);
    }

    public static <A, B> Either<A, B> alternative(B b) {
        return new Either<A, B>(null, b);
    }

    public <C> C fold(Function<A, C> functionOnExpected, Function<B, C> functionOnAlternative) {
        if (hasExpected()) {
            return functionOnExpected.apply(expected());
        } else {
            return functionOnAlternative.apply(alternative());
        }
    }

}
