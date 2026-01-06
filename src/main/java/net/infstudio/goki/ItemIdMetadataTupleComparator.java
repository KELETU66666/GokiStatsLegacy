package net.infstudio.goki;

import java.util.Comparator;

public class ItemIdMetadataTupleComparator implements Comparator<ItemIdMetadataTuple>
{
    @Override
    public int compare(final ItemIdMetadataTuple o1, final ItemIdMetadataTuple o2) {
        if (o1.id == o2.id && o1.id == o2.id) {
            return 1;
        }
        return 0;
    }
}