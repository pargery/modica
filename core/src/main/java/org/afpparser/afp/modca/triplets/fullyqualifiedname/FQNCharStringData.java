package org.afpparser.afp.modca.triplets.fullyqualifiedname;

/**
 * Provides functionality for the Fully Qualified Name triplets that have character string data.
 */
final class FQNCharStringData extends FullyQualifiedName {
    private final String data;
    private final FQNType type;

    FQNCharStringData(int length, String data, FQNType type) {
        super(length);
        this.data = data;
        this.type = type;
    }

    /**
     * Returns the character string data.
     *
     * @return the character string
     */
    public String getString() {
        return this.data;
    }

    @Override
    public FQNFmt getFormat() {
        return FQNFmt.character_string;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FQNCharStringData)) {
            return false;
        }
        FQNCharStringData fqn = (FQNCharStringData) o;
        return this.data.equals(fqn.data)
                && this.getFQNType() == fqn.getFQNType()
                && this.getLength() == fqn.getLength();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getLength();
        result = 31 * result + getFQNType().hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getTid().name() + ", " + getFQNType() + "=" + data;
    }

    @Override
    public FQNType getFQNType() {
        return type;
    }

    @Override
    public String valueToString() {
        return data;
    }
}
