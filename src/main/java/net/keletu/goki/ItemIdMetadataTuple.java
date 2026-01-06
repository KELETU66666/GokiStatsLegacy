package net.keletu.goki;

public class ItemIdMetadataTuple {
    public int id;
    public int metadata;

    public ItemIdMetadataTuple(final int id, final int meta) {
        this.id = 0;
        this.metadata = 0;
        this.id = id;
        this.metadata = meta;
    }

    public ItemIdMetadataTuple(final String configString) {
        this.id = 0;
        this.metadata = 0;
        this.fromConfigString(configString);
    }

    public String toConfigString() {
        return this.id + ":" + this.metadata;
    }

    public void fromConfigString(final String configString) {
        final String[] configStringSplit = configString.split(":");
        try {
            this.id = Integer.parseInt(configStringSplit[0]);
            this.metadata = Integer.parseInt(configStringSplit[1]);
        } catch (final Exception ex) {
        }
    }
}