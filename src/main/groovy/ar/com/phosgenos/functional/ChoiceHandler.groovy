package ar.com.phosgenos.functional

class ChoiceHandler<A, B, R> {

    final Either<A, B> choice
    private Closure<R> expectedClosure
    private Closure<R> alternativeClosure

    ChoiceHandler(Either<A, B> choice) {
        this.choice = choice

    }

    ChoiceHandler<A, B, R> onExpected(Closure<R> expectedClosure) {
        this.expectedClosure = expectedClosure
        this
    }

    ChoiceHandler<A, B, R> onAlternative(Closure<R> alternativeClosure) {
        this.alternativeClosure = alternativeClosure
        this
    }

    R fold() {
        Objects.requireNonNull(expectedClosure, 'Must provide an expected closure')
        Objects.requireNonNull(alternativeClosure, 'Must provide an alternative closure')

        choice.hasExpected() ? expectedClosure.call(choice.expected()) : alternativeClosure.call(choice.alternative())
    }

    static <A, B, R> ChoiceHandler<A, B, R> on(Either<A, B> _choice) {
        Objects.requireNonNull(_choice)
        new ChoiceHandler<A, B, R>(_choice)
    }
}
