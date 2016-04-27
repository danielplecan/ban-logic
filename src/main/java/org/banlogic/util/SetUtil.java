package org.banlogic.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class SetUtil {

    private SetUtil() {
    }

    public static <T> Set<Set<T>> generatePowerSet(Collection<T> originalSet) {
        Set<Set<T>> sets = new LinkedHashSet<>();

        if (originalSet.isEmpty()) {
            sets.add(new LinkedHashSet<>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Set<T> rest = new LinkedHashSet<>(list.subList(1, list.size()));

        generatePowerSet(rest)
                .stream()
                .forEach((set) -> {
                    Set<T> newSet = new LinkedHashSet<>();
                    newSet.add(head);
                    newSet.addAll(set);
                    sets.add(newSet);
                    sets.add(set);
                });

        return sets;
    }
}
